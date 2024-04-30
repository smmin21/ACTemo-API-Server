package actemo.server.service;

import actemo.server.dto.SceneDto;
import actemo.server.entity.*;
import actemo.server.repository.MemberRepository;
import actemo.server.repository.SceneRepository;

import actemo.server.repository.ScriptRepository;
import actemo.server.repository.SeenSceneRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SceneService {
    private final ScriptRepository scriptRepository;
    private final SceneRepository sceneRepository;
    private final SeenSceneRepository seenSceneRepository;
    private final MemberRepository memberRepository;

    @Cacheable(value = "scene", key = "#emotion")
    public SceneDto getScene(String emotion, String idToken){
        try {
            // 사용자 ID를 추출
            Long userId = Long.parseLong(idToken);
//            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
//            String uid = decodedToken.getUid();
//            Long userId = Long.parseLong(uid);

            // 해당 감정에 대한 Script 찾음
            Script script = scriptRepository.findByEmotion(emotion)
                    .orElseThrow(() -> new IllegalArgumentException("옳지 않은 감정 정보입니다."));
            // 사용자가 이미 연습한 장면들을 찾음
            List<Scene> seenScenes = seenSceneRepository.findByMemberDbIdAndEmotion(userId, emotion).stream()
                    .map(SeenScene::getScene)
                    .collect(Collectors.toList());

            // 아직 연습하지 않은 장면을 찾아 반환
            Scene scene = script.getScenes().stream()
                    .filter(s -> !seenScenes.contains(s))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("해당 감정에 대한 스크립트가 모두 끝났습니다."));

            return SceneDto.builder()
                    .id(scene.getId())
                    .emotion(scene.getEmotion())
                    .name(scene.getName())
                    .situation(scene.getSituation())
                    .dialogue(scene.getDialogue())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("사용자 토큰이 유효하지 않습니다.", e);
        }
    }

    public void saveSeenScene(Long sceneId, String idToken) {
        try {
            // 사용자 ID를 추출
            Long userId = Long.parseLong(idToken);
//            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
//            String uid = decodedToken.getUid();
//            Long userId = Long.parseLong(uid);

            // 해당 대본을 찾음
            Scene scene = sceneRepository.findById(sceneId)
                    .orElseThrow(() -> new IllegalArgumentException("옳지 않은 대본 정보입니다."));
            // 해당 유저가 존재하지 않을 경우 생성 후 저장, 유저가 존재할 경우 해당 유저의 정보 가져옴
            Member member = memberRepository.findByDbId(userId).orElseGet(() ->
                    memberRepository.save(Member.builder().dbId(userId).build()));

            seenSceneRepository.save(SeenScene.builder()
                    .member(member)
                    .scene(scene)
                    .emotion(scene.getEmotion())
                    .build());
        } catch (Exception e) {
            throw new RuntimeException("사용자 토큰이 유효하지 않습니다.", e);
        }
    }

    public void saveEmotionScene(String body) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // JSON 문자열을 JsonNode로 변환
            JsonNode rootNode = objectMapper.readTree(body);

            // 각 감정에 대해
            Iterator<Map.Entry<String, JsonNode>> emotions = rootNode.fields();
            while (emotions.hasNext()) {
                Map.Entry<String, JsonNode> emotionEntry = emotions.next();
                String emotion = emotionEntry.getKey();
                JsonNode emotionNode = emotionEntry.getValue();

                // 해당 감정의 Script 객체를 찾음
                Script script = scriptRepository.findByEmotion(emotion)
                        .orElseThrow(() -> new IllegalArgumentException("옳지 않은 감정 정보입니다."));

                // 각 장면에 대해
                Iterator<Map.Entry<String, JsonNode>> scenes = emotionNode.fields();
                while (scenes.hasNext()) {
                    Map.Entry<String, JsonNode> sceneEntry = scenes.next();
                    String sceneName = sceneEntry.getKey(); // Get the scene name from the key
                    JsonNode sceneNode = sceneEntry.getValue();

                    // 대화를 "Dialogue" 객체로 만들어 List에 추가
                    List<Dialogue> dialogue = new ArrayList<>();
                    JsonNode dialogueNode = sceneNode.get("Dialogue");
                    for (JsonNode dialogueEntry : dialogueNode) {
                        String name = dialogueEntry.get("name").asText();
                        String message = dialogueEntry.get("message").asText();
                        dialogue.add(new Dialogue(name, message));
                    }

                    // 새로운 Scene 객체를 생성하고 이름과 상황을 설정
                    Scene scene = Scene.builder()
                            .emotion(emotion) // Set the emotion
                            .name(sceneName) // Set the scene name
                            .situation(sceneNode.get("Situation").asText())
                            .dialogue(dialogue)
                            .script(script)
                            .build();

                    // Scene을 저장
                    sceneRepository.save(scene);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            throw new IllegalArgumentException("스크립트 저장에 실패했습니다.");
        }
    }

}

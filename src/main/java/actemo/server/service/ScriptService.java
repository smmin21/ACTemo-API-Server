package actemo.server.service;

import actemo.server.dto.ScriptDto;
import actemo.server.entity.Dialogue;
import actemo.server.entity.Scene;
import actemo.server.entity.Script;
import actemo.server.repository.ScriptRepository;
import actemo.server.repository.SceneRepository;
import actemo.server.repository.SeenSceneRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ScriptService {
    private final ScriptRepository scriptRepository;
    private final SeenSceneRepository seenSceneRepository;
    private final SceneRepository sceneRepository;

    @PostConstruct
    public void initScripts() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // JSON 파일에서 감정 정보를 읽음
            InputStream inputStream = TypeReference.class.getResourceAsStream("/static/emotion-details.json");
            List<Script> scripts = objectMapper.readValue(inputStream, new TypeReference<List<Script>>(){});

            // 각 감정에 대해 Script 객체를 생성하고 저장
            for (Script script : scripts) {
                scriptRepository.save(script);
            }

            // JSON 파일에서 대사 정보를 읽음
            InputStream inputStream2 = TypeReference.class.getResourceAsStream("/static/emotion-script.json");
            Map<String, Map<String, Map<String, Object>>> scenes = objectMapper.readValue(inputStream2, new TypeReference<Map<String, Map<String, Map<String, Object>>>>(){});

            // 각 감정에 대해 Scene 객체를 생성하고 저장
            for (Map.Entry<String, Map<String, Map<String, Object>>> emotionEntry : scenes.entrySet()) {
                String emotion = emotionEntry.getKey();
                Script script = scriptRepository.findByEmotion(emotion)
                        .orElseThrow(() -> new IllegalArgumentException("옳지 않은 감정 정보입니다."));

                for (Map.Entry<String, Map<String, Object>> sceneEntry : emotionEntry.getValue().entrySet()) {
                    String sceneName = sceneEntry.getKey();
                    Map<String, Object> sceneDetails = sceneEntry.getValue();
                    String situation = (String) sceneDetails.get("Situation");

                    // 대사 정보를 읽어 Dialogue 객체 생성
                    List<Dialogue> dialogue = new ArrayList<>();
                    List<Map<String, String>> dialogues = (List<Map<String, String>>) sceneDetails.get("Dialogue");
                    for (Map<String, String> dialogueMap : dialogues) {
                        String name = dialogueMap.get("name");
                        String message = dialogueMap.get("message");
                        dialogue.add(new Dialogue(name, message));
                    }

                    Scene scene = Scene.builder()
                            .emotion(emotion)
                            .name(sceneName)
                            .situation(situation)
                            .dialogue(dialogue)
                            .script(script)
                            .build();

                    sceneRepository.save(scene);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("감정 정보를 읽는 도중 오류가 발생했습니다.", e);
        }
    }

    @Cacheable(value = "script", key = "#emotion")
    public ScriptDto getScript(String emotion) {
        Script script = scriptRepository.findByEmotion(emotion)
                .orElseThrow(() -> new IllegalArgumentException("옳지 않은 감정 정보입니다."));

        return ScriptDto.builder()
                .emotion(script.getEmotion())
                .emoji(script.getEmoji())
                .category(script.getCategory())
                .imagePath(script.getImagePath())
                .definition(script.getDefinition())
                .example(script.getExample())
                .build();
    }


}

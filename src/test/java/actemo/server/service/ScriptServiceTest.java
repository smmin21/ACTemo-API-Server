package actemo.server.service;

import actemo.server.repository.SceneRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class ScriptServiceTest {

    @Autowired SceneService sceneService;
    @Autowired SceneRepository sceneRepository;


    @Test
    void saveEmotionScript() {
        String body = "{\n" +
                "  \"Surprised\": {\n" +
                "    \"Scene 1\": {\n" +
                "      \"Situation\": \"A friend throws a surprise birthday party.\",\n" +
                "      \"Dialogue\": [\n" +
                "        {\"name\": \"Character 1\", \"message\": \"Hey, what's going on here? Why is everyone gathered?\"},\n" +
                "        {\"name\": \"Character 2\", \"message\": \"Surprise! Happy birthday! We wanted to make your day special.\"},\n" +
                "        {\"name\": \"Character 1\", \"message\": \"I... I'm genuinely surprised! I had no idea. Thank you so much, everyone!\"}\n" +
                "      ]\n" +
                "    },\n" +
                "    \"Scene 2\": {\n" +
                "      \"Situation\": \"Kate and Aaron, a couple, are at a restaurant celebrating their anniversary when Aaron proposes.\",\n" +
                "      \"Dialogue\": [\n" +
                "        {\"name\": \"Aaron\", \"message\": \"Kate, these past years with you have been the best of my life. Will you marry me?\"},\n" +
                "        {\"name\": \"Kate\", \"message\": \"Aaron! I... I wasn't expecting this! Is this... is this really happening?\"}\n" +
                "      ]\n" +
                "    }\n" +
                "  }\n" +
                "}";
//        sceneService.saveEmotionScene(body);
//        // 저장된 Scene이 정상적으로 저장되었는지 확인
//        assertNotNull(sceneRepository.findByName("Scene 1"));
//        assertNotNull(sceneRepository.findByName("Scene 2"));
//        // 각 신의 Dialogue 정보 출력
//        System.out.println(sceneRepository.findByName("Scene 1").get().getDialogue());

    }
}
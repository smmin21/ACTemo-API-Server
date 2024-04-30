package actemo.server.controller;

import actemo.server.dto.SceneDto;
import actemo.server.service.SceneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class SceneController {
    private final SceneService sceneService;

    @GetMapping("/scene/{emotion}")
    public ResponseEntity<SceneDto> getScene(@PathVariable("emotion") String emotion, @RequestHeader("Authorization") String idToken) {
        return ResponseEntity.ok(sceneService.getScene(emotion, idToken));
    }

    @PostMapping("/scene")
    public ResponseEntity<String> saveScene(@RequestBody String body) {
        sceneService.saveEmotionScene(body);
        return ResponseEntity.ok("success");
    }

    @PostMapping("/user-scene/{sceneId}")
    public ResponseEntity<String> saveSeenScene(@PathVariable("sceneId") Long sceneId, @RequestHeader("Authorization") String idToken) {
        sceneService.saveSeenScene(sceneId, idToken);
        return ResponseEntity.ok("success");
    }
}

package actemo.server.controller;

import actemo.server.dto.SceneDto;
import actemo.server.dto.ScriptDto;
import actemo.server.service.SceneService;
import actemo.server.service.ScriptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ScriptController {
    private final ScriptService scriptService;
    private final SceneService sceneService;

    @GetMapping("/script/{emotion}")
    public ResponseEntity<ScriptDto> getScript(@PathVariable("emotion") String emotion) {
        return ResponseEntity.ok(scriptService.getScript(emotion));
    }




}

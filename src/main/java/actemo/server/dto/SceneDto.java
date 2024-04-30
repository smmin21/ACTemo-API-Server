package actemo.server.dto;

import actemo.server.entity.Dialogue;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SceneDto {
    private Long id;
    private String emotion;
    private String name;
    private String situation;
    private List<Dialogue> dialogue;
}

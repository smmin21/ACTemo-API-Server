package actemo.server.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ScriptDto {
    private Long id;
    private String emotion;
    private String emoji;
    private String category;
    private String imagePath;
    private String definition;
    private String example;

}

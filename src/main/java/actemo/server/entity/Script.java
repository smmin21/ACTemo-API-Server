package actemo.server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Script {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String emotion;
    private String emoji;
    private String category;
    private String imagePath;
    private String definition;
    private String example;

    @OneToMany(mappedBy = "script", cascade = CascadeType.ALL)
    private List<Scene> scenes = new ArrayList<>();
}

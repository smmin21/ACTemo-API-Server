package actemo.server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Scene {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String emotion;
    private String name;
    private String situation;
    @ElementCollection
    @CollectionTable(name = "dialogue", joinColumns = @JoinColumn(name = "scene_id"))
    private List<Dialogue> dialogue;

    @ManyToOne
    @JoinColumn(name = "script_id")
    private Script script;

}

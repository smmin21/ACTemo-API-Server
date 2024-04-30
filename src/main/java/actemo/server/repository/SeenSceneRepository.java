package actemo.server.repository;

import actemo.server.entity.SeenScene;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeenSceneRepository extends JpaRepository<SeenScene, Long>{
    List<SeenScene> findByMemberDbIdAndEmotion(Long memberDbId, String emotion);
}

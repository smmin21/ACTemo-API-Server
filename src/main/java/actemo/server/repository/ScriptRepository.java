package actemo.server.repository;

import actemo.server.entity.Script;
import actemo.server.service.ScriptService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScriptRepository extends JpaRepository<Script, Long> {
    Optional<Script> findByEmotion(String emotion);
}

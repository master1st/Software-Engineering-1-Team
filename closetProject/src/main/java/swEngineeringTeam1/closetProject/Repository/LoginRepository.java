package swEngineeringTeam1.closetProject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swEngineeringTeam1.closetProject.Entity.UserEntity;

import java.util.Optional;

@Repository
public interface LoginRepository extends JpaRepository<UserEntity, Long> {
    public Optional<UserEntity> findById (String id);

}

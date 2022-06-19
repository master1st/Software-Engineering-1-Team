package swEngineeringTeam1.closetProject.Repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import swEngineeringTeam1.closetProject.Entity.CodyEntity;
import swEngineeringTeam1.closetProject.Entity.CodyId;

import javax.persistence.OrderBy;
import java.util.List;

@Repository
public interface CodyRepository extends JpaRepository<CodyEntity, CodyId> {
     List<CodyEntity> findAllByCodyIdCodyNum (Long codyNum);

     @OrderBy("CodyId.codyNum ASC")
     List<CodyEntity> findAllByCodyIdUserCode(Long userCode, Sort sort);

     CodyEntity findFirstByCodyIdCodyNum(Long codyNum);

     @Query(value = "SELECT MAX(CODYNUM) FROM cody", nativeQuery = true)
     Long findMaxCodyNum ();

     void deleteByCodyIdCodyNum (Long codyNum);

}

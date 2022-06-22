package swEngineeringTeam1.closetProject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import swEngineeringTeam1.closetProject.Entity.ClothesEntity;
import swEngineeringTeam1.closetProject.Entity.UserEntity;

import java.util.List;

@Repository
public interface ClothesRepository extends JpaRepository<ClothesEntity, Long> {
    List<ClothesEntity> findAllByUser(UserEntity entity);
    List<ClothesEntity> findAllByUserAndSeasonAndColorAndTypeAndMaterial(UserEntity entity, String season, String color, String type, String material);
    List<ClothesEntity> findAllByUserAndSeasonAndColorAndType(UserEntity entity, String season, String color, String type);
    List<ClothesEntity> findAllByUserAndSeasonAndColorAndMaterial(UserEntity entity, String season, String color, String material);
    List<ClothesEntity> findAllByUserAndSeasonAndTypeAndMaterial(UserEntity entity, String season, String type, String material);
    List<ClothesEntity> findAllByUserAndColorAndTypeAndMaterial(UserEntity entity, String color, String type, String material);
    List<ClothesEntity> findAllByUserAndSeasonAndColor(UserEntity entity, String season, String color);
    List<ClothesEntity> findAllByUserAndSeasonAndType(UserEntity entity, String season, String type);
    List<ClothesEntity> findAllByUserAndColorAndType(UserEntity entity, String color, String type);
    List<ClothesEntity> findAllByUserAndSeasonAndMaterial(UserEntity entity, String season, String material);
    List<ClothesEntity> findAllByUserAndColorAndMaterial(UserEntity entity, String color, String material);
    List<ClothesEntity> findAllByUserAndTypeAndMaterial(UserEntity entity, String type, String material);
    List<ClothesEntity> findAllByUserAndSeason(UserEntity entity, String season);
    List<ClothesEntity> findAllByUserAndColor(UserEntity entity, String color);
    List<ClothesEntity> findAllByUserAndType(UserEntity entity, String type);
    List<ClothesEntity> findAllByUserAndMaterial(UserEntity entity, String material);

    @Query(value = "SELECT MAX(clothesId) FROM clothes", nativeQuery = true)
    Long findMaxclothesNum();
}

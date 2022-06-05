package swEngineeringTeam1.closetProject.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import swEngineeringTeam1.closetProject.Dto.ClothesDtoForCody;
import lombok.*;
import swEngineeringTeam1.closetProject.Dto.ClothesDto;
import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "clothes")
@Setter

public class ClothesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clothesId;

    @ManyToOne
    @JoinColumn(name = "userCode")
    private UserEntity user;

    private String clothesImage;
    private String season;
    private String color;
    private String type;
    private String material;

    private String clothesImage;

    public ClothesEntity (UserEntity user, ClothesDtoForCody clothesDtoForCody) {
        this.user=user;
        this.clothesId= clothesDtoForCody.getClothesId();
        this.clothesImage= clothesDtoForCody.getClothesImage();
        this.season= clothesDtoForCody.getSeason();
        this.color= clothesDtoForCody.getColor();
        this.type= clothesDtoForCody.getType();
        this.material= clothesDtoForCody.getMaterial();


    public ClothesEntity(UserEntity user,ClothesDto clothesDto){
        this.user=user;
        this.clothesImage=(clothesDto.getClothesImage());
        this.season=(clothesDto.getSeason());
        this.color=(clothesDto.getColor());
        this.type=(clothesDto.getType());
        this.material=(clothesDto.getMaterial());
    }
}

package swEngineeringTeam1.closetProject.Dto;

import lombok.Getter;
import lombok.Setter;
import swEngineeringTeam1.closetProject.Entity.ClothesEntity;

@Getter
@Setter
public class ClothesReturnDto {
    private Long clothesId;
    private String clothesImage;
    private String season;
    private String color;
    private String type;
    private String material;

    public ClothesReturnDto(ClothesEntity clothesEntity){
        this.clothesId = clothesEntity.getClothesId();
        this.clothesImage = clothesEntity.getClothesImage();
        this.season=clothesEntity.getSeason();
        this.color=clothesEntity.getColor();
        this.type=clothesEntity.getType();
        this.material=clothesEntity.getMaterial();
    }
}

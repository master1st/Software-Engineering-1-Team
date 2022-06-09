package swEngineeringTeam1.closetProject.Dto;

import lombok.*;
import swEngineeringTeam1.closetProject.Entity.ClothesEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor //need
public class ClothesDtoForCody {
    private Long clothesId;
    private String clothesImage;
    private String color;
    private String material;
    private String season;
    private String type;
    private Long userCode;

    //ClothesEntity -> ClothesDto
    public ClothesDtoForCody(ClothesEntity clothesEntity) {
        this.clothesId=clothesEntity.getClothesId();
        this.clothesImage=clothesEntity.getClothesImage();
        this.color=clothesEntity.getColor();
        this.material=clothesEntity.getMaterial();
        this.season=clothesEntity.getSeason();
        this.type=clothesEntity.getType();
        this.userCode=clothesEntity.getUser().getUserCode();
    }
}

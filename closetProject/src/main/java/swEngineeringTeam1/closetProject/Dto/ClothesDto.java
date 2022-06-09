package swEngineeringTeam1.closetProject.Dto;

import lombok.Getter;
import lombok.Setter;
import swEngineeringTeam1.closetProject.Entity.ClothesEntity;
import swEngineeringTeam1.closetProject.Entity.UserEntity;

@Getter
@Setter
public class ClothesDto {
    private Long userCode;
    private String clothesImage;
    private String season;
    private String color;
    private String type;
    private String material;

    public ClothesDto(Long userCode, String clothesImage, String season, String color, String type, String material){
        this.userCode=userCode;
        this.clothesImage=clothesImage;
        this.season=season;
        this.color=color;
        this.type=type;
        this.material=material;
    }
}

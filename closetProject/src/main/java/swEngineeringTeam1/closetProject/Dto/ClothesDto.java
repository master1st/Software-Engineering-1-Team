package swEngineeringTeam1.closetProject.Dto;

import lombok.Getter;
import lombok.Setter;
import swEngineeringTeam1.closetProject.Entity.ClothesEntity;
import swEngineeringTeam1.closetProject.Entity.UserEntity;

@Getter
@Setter
public class ClothesDto {
    private Long clothesId;
    private String season;
    private String color;
    private String type;
    private String material;

    public ClothesDto(String season, String color, String type, String material){
        this.season=season;
        this.color=color;
        this.type=type;
        this.material=material;
    }
}

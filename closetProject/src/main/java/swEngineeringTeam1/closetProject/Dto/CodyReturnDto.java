package swEngineeringTeam1.closetProject.Dto;

import lombok.Builder;
import lombok.Data;
import swEngineeringTeam1.closetProject.Entity.ClothesEntity;
import swEngineeringTeam1.closetProject.Entity.CodyEntity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Data
public class CodyReturnDto {
    private String clothesImage; //string ->file
    private Long clothesId;
    private String codyImage;
    private Long codyNum;


    @Builder
    public CodyReturnDto (CodyEntity codyEntity) {
        this.codyImage=codyEntity.getCodyImage();
        this.codyNum=codyEntity.getCodyId().getCodyNum();
        this.clothesId = codyEntity.getClothesEntity().getClothesId();
        this.clothesImage = codyEntity.getClothesEntity().getClothesImage();
    }



}

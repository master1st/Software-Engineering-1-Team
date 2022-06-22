package swEngineeringTeam1.closetProject.Dto;

import lombok.*;
import swEngineeringTeam1.closetProject.Entity.CodyEntity;

@Data
public class CodyReturnDto {

    private String clothesImage; //string ->file
    private Long clothesId;
    private String codyImage; //base64로 인코딩된 정보
    private Long codyNum;

    public CodyReturnDto (CodyEntity codyEntity) {
        this.clothesImage=codyEntity.getClothesEntity().getClothesImage();
        this.codyNum=codyEntity.getCodyId().getCodyNum();
        this.clothesId = codyEntity.getClothesEntity().getClothesId();

    }



}

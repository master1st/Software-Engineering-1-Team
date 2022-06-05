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
    private List<String> clothesImageList; //string ->file
    private String codyImage;
    private Long codyNum;


    public void toDto (CodyEntity codyEntity) {
        this.codyImage=codyEntity.getCodyImage();
        this.codyNum=codyEntity.getCodyId().getCodyNum();
        this.clothesImageList.add(codyEntity.getClothesEntity().getClothesImage());
    }

    public CodyReturnDto() {
        this.clothesImageList=new ArrayList<>();
    }

}

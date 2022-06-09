package swEngineeringTeam1.closetProject.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "cody")
public class CodyEntity {

    @EmbeddedId
    private CodyId codyId;

    @ManyToOne
    @MapsId("clothesId")//외래 키와 매핑한 연관관계를 기본 키에도 매핑하겠다는 의미
    //mappedby 의 값은 해당하는 엔티티의 어떤 필드와 매칭이 되는가를 지정해주는겁니다.
    //private List<ClothesEntity> clothesEntity = new ArrayList<>();
    private ClothesEntity clothesEntity;

    @ManyToOne
    @MapsId("userCode")
    private UserEntity userEntity;

    private String codyImage;

    @Builder
    public CodyEntity(UserEntity user, ClothesEntity clothesEntity, String codyImage, Long maxCodyNum) {
        this.userEntity=user;
        this.clothesEntity=clothesEntity;
        this.codyImage=codyImage;
        this.codyId= new CodyId(clothesEntity.getClothesId(), user.getUserCode(), maxCodyNum );
    }

}

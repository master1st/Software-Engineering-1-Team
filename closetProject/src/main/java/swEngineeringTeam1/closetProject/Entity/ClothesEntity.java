package swEngineeringTeam1.closetProject.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import swEngineeringTeam1.closetProject.Dto.ClothesDtoForCody;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "clothes")
@AllArgsConstructor
public class ClothesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clothesId;

    @ManyToOne
    @JoinColumn(name = "userCode")
    private UserEntity user;

    private String season;
    private String color;
    private String type;
    private String material;

    private String clothesImage;

    @Builder
    public ClothesEntity (UserEntity user, ClothesDtoForCody clothesDtoForCody) {
        this.user=user;
        this.clothesId= clothesDtoForCody.getClothesId();
        this.clothesImage= clothesDtoForCody.getClothesImage();
        this.season= clothesDtoForCody.getSeason();
        this.color= clothesDtoForCody.getColor();
        this.type= clothesDtoForCody.getType();
        this.material= clothesDtoForCody.getMaterial();

    }
}

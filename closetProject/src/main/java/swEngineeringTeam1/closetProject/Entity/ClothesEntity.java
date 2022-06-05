package swEngineeringTeam1.closetProject.Entity;

import lombok.*;
import swEngineeringTeam1.closetProject.Dto.ClothesDto;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "clothes")
@Getter
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

    @Builder
    public ClothesEntity(UserEntity user,ClothesDto clothesDto){
        this.user=user;
        this.clothesImage=(clothesDto.getClothesImage());
        this.season=(clothesDto.getSeason());
        this.color=(clothesDto.getColor());
        this.type=(clothesDto.getType());
        this.material=(clothesDto.getMaterial());
    }
}

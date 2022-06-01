package swEngineeringTeam1.closetProject.Entity;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "clothes")
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
}

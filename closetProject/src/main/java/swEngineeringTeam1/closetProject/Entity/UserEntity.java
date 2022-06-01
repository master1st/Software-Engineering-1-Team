package swEngineeringTeam1.closetProject.Entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import swEngineeringTeam1.closetProject.Dto.LoginAndSignupDto;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "user")
@Getter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userCode;

    private String id;

    private String password;

    @Builder
    public UserEntity (LoginAndSignupDto loginAndSignupDto) {
        this.id=loginAndSignupDto.getId();
        this.password=loginAndSignupDto.getPassword();
    }
}

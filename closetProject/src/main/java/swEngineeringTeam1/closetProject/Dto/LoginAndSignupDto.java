package swEngineeringTeam1.closetProject.Dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginAndSignupDto {
    private String id;
    private String password;

    @Builder
    public LoginAndSignupDto(String id, String password) {
        this.id=id;
        this.password=password;
    }
}

package swEngineeringTeam1.closetProject.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import swEngineeringTeam1.closetProject.Dto.LoginAndSignupDto;
import swEngineeringTeam1.closetProject.Repository.LoginRepository;
import swEngineeringTeam1.closetProject.Entity.UserEntity;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final LoginRepository loginRepository;

    public UserEntity login(LoginAndSignupDto loginAndSignupDto) {
        UserEntity userEntity = loginRepository.findById(loginAndSignupDto.getId());
        String realPassword = userEntity.getPassword();
        if (realPassword.equals(loginAndSignupDto.getPassword())) //login success
            return userEntity;
        else //login fail
            return null;
    }

    public UserEntity signup(LoginAndSignupDto loginAndSignupDto) {
        UserEntity save = loginRepository.save(new UserEntity(loginAndSignupDto));
        //no password encryption
        return save;
    }
}

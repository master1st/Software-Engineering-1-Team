package swEngineeringTeam1.closetProject.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import swEngineeringTeam1.closetProject.Dto.LoginAndSignupDto;
import swEngineeringTeam1.closetProject.Repository.LoginRepository;
import swEngineeringTeam1.closetProject.Service.LoginService;
import swEngineeringTeam1.closetProject.Entity.UserEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final LoginRepository loginRepository;

    @PostMapping("/login")
    public Map<String, Object> login (@RequestBody LoginAndSignupDto loginAndSignupDto, HttpSession session) {
        return loginService.login(loginAndSignupDto, session);
    }

    @PostMapping("/signup")
    public Map<String,Object> signup(@RequestBody LoginAndSignupDto loginAndSignupDto) {
        return loginService.signup(loginAndSignupDto);
    }

    @PostMapping("/logout")
    public Map<String,Object> logout (HttpSession session) {
        session.invalidate();
        Map<String,Object> response = new HashMap<>();
        response.put("success",true);
        response.put("message","로그아웃에 성공하였습니다");
        return response;
    }

    @Transactional
    @PostMapping("/deleteUser")
    public  Map<String,Object> deleteUser (@RequestParam Long userCode,HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
      //  UserEntity loginUser = loginService.getLoginUser(request);
        UserEntity loginUser = loginRepository.findById(userCode).get();
        return loginService.deleteUser(loginUser.getUserCode());

    }
}

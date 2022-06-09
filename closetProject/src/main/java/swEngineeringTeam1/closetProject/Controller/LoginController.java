package swEngineeringTeam1.closetProject.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import swEngineeringTeam1.closetProject.Dto.LoginAndSignupDto;
import swEngineeringTeam1.closetProject.Service.LoginService;
import swEngineeringTeam1.closetProject.Entity.UserEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public HttpSession login (@RequestBody LoginAndSignupDto loginAndSignupDto, HttpServletRequest request) {
        UserEntity isLogin = loginService.login(loginAndSignupDto);
        HttpSession session = request.getSession();
        System.out.println("ddd");
        if(isLogin!=null) {//login success
            session.setAttribute("user", isLogin.getUserCode());
            System.out.println("here");
        }
        else {
            session.setAttribute("user", "fail"); //modifying null is need
            System.out.println("or");
        }return session;
    }

    @PostMapping("/signup")
    public UserEntity signup(@RequestBody LoginAndSignupDto loginAndSignupDto) {
        UserEntity signup = loginService.signup(loginAndSignupDto);
        return signup;
    }

    @PostMapping("/logout")
    public void logout ( HttpServletRequest request) {
        HttpSession session= request.getSession();
        session.invalidate();
    }

    @PostMapping("/deleteUser")
    public void deleteUser (HttpServletRequest request) {
        HttpSession session= request.getSession();
        Long userCode = (Long) session.getAttribute("user");
        session.invalidate();
        loginService.deleteUser(userCode);
    }
}

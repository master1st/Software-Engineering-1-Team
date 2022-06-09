package swEngineeringTeam1.closetProject.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import swEngineeringTeam1.closetProject.Dto.LoginAndSignupDto;
import swEngineeringTeam1.closetProject.Repository.LoginRepository;
import swEngineeringTeam1.closetProject.Entity.UserEntity;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final LoginRepository loginRepository;

    public UserEntity getUser(Long userCode) {
        return loginRepository.findById(userCode).orElse(null);
    }

    public Map<String, Object> login(LoginAndSignupDto loginAndSignupDto, HttpSession session) {
        Optional<UserEntity> userEntity = loginRepository.findById(loginAndSignupDto.getId());
        Map<String, Object> response = new HashMap<>();
        if (userEntity.isEmpty()) {
            response.put("success", false);
            response.put("message", "존재하지 않는 아이디입니다");
        } else {
            String realPassword = userEntity.get().getPassword();
            if (realPassword.equals(loginAndSignupDto.getPassword())) {//login success

                String sessionId = UUID.randomUUID().toString(); //random sessionId

                session.setAttribute(sessionId, userEntity.get().getUserCode());
                response.put("success", true);
                response.put("message", "로그인에 성공하였습니다");
                response.put("sessionId",sessionId);

            } else {
                response.put("success", false);
                response.put("message", "비밀번호가 일치하지 않습니다");
            }
        }
        return  response;
    }

        public Map<String, Object> signup (LoginAndSignupDto loginAndSignupDto){
            boolean b = existCheck(loginAndSignupDto.getId());
            Map<String, Object> response = new HashMap<>();
            if (b == false) {
                loginRepository.save(new UserEntity(loginAndSignupDto));
                response.put("success", true);
                response.put("message", "회원가입이 완료되었습니다");
            } else {
                response.put("success", false);
                response.put("message", "이미 존재하는 아이디입니다");
            }
            return response;
        }

        public Map<String, Object> deleteUser (Long userCode){
            loginRepository.deleteById(userCode);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "회원탈퇴가 완료되었습니다");
            return response;
        }

        public boolean existCheck (String id){
            Optional<UserEntity> byId = loginRepository.findById(id);
            if (byId.isPresent()) return true;
            else return false;
        }

        public Optional<Object> getUserCodeBySession (String sessionId, HttpServletRequest request) {
            return Optional.ofNullable(request.getSession().getAttribute(sessionId));
        }

        public String getSessionId (HttpServletRequest request) {
            Cookie[] cookies = request.getCookies();
            String sessionId="";
            for (Cookie c : cookies) {
                if (c.getName().equals("sessionId")) {
                    sessionId = c.getValue();
                }
            }
            return sessionId;

        }

        public UserEntity getLoginUser (HttpServletRequest request) {
            String sessionId = getSessionId(request);
            Long userCode = (Long) getUserCodeBySession(sessionId, request).orElseThrow();
            return loginRepository.findById(userCode).get();
        }
    }


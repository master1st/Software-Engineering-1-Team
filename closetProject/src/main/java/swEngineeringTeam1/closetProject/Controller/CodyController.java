package swEngineeringTeam1.closetProject.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.catalina.User;
import org.json.simple.parser.ParseException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import swEngineeringTeam1.closetProject.Dto.ClothesDtoForCody;
import swEngineeringTeam1.closetProject.Dto.CodyRequestDto;
import swEngineeringTeam1.closetProject.Dto.CodyReturnDto;
import swEngineeringTeam1.closetProject.Entity.CodyEntity;
import swEngineeringTeam1.closetProject.Entity.UserEntity;
import swEngineeringTeam1.closetProject.Repository.ClothesRepository;
import swEngineeringTeam1.closetProject.Service.CodyService;
import swEngineeringTeam1.closetProject.Service.LoginService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@Getter
@Setter
@RequiredArgsConstructor
public class CodyController {
    private final CodyService codyService;
    private final LoginService loginService;

    @PostMapping(value = "/mycody", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String,Object> createCody(@RequestParam String jsonString, @RequestParam(required = false) MultipartFile file, HttpServletRequest request) {
        UserEntity user = loginService.getLoginUser(request);

        ObjectMapper mapper = new ObjectMapper();
        CodyRequestDto codyRequestDto = null;
        try {
            codyRequestDto = mapper.readValue(jsonString, CodyRequestDto.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        List<ClothesDtoForCody> clothesList = codyRequestDto.getClothesList();

        return codyService.createCody( user, clothesList, file);
    }

    @GetMapping(value = "/mycody")
    public Map<String, Object> getCody(HttpServletRequest request) throws IOException {
        UserEntity user = loginService.getLoginUser(request);
        return codyService.getAllCody(user);
    }

    @GetMapping("/mycody/clothes")
    public Map<String,Object> getClothes(HttpServletRequest request) {
        UserEntity user = loginService.getLoginUser(request);
        return codyService.getClothes(user);
    }

    @GetMapping("/mycody/{codyNum}")
    public Map<String,Object> getExistingCody(HttpServletRequest request, @PathVariable Long codyNum) throws IOException {
        return codyService.getExistingCody(codyNum);
    }

    @Transactional
    @PutMapping(value = "/mycody/{codyNum}", consumes =  MediaType.MULTIPART_FORM_DATA_VALUE)
    public void updateCody(HttpServletRequest request, @PathVariable Long codyNum, @RequestParam  String jsonString, @RequestParam(required = false) MultipartFile file) throws IOException {
        UserEntity user = loginService.getLoginUser(request);

        ObjectMapper mapper = new ObjectMapper();
        CodyRequestDto codyRequestDto = mapper.readValue(jsonString, CodyRequestDto.class);
        List<ClothesDtoForCody> clothesList = codyRequestDto.getClothesList();
        codyService.updateCody(user, codyNum, clothesList, file);
    }

    @Transactional
    @DeleteMapping("/mycody/{codyNum}")
    public Map<String,Object> deleteCody(HttpServletRequest request,@PathVariable Long codyNum) {

        return codyService.deleteCody(codyNum);
    }


}

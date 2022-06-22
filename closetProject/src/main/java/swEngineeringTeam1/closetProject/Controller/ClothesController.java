package swEngineeringTeam1.closetProject.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import swEngineeringTeam1.closetProject.Dto.ClothesDto;
import swEngineeringTeam1.closetProject.Dto.ClothesReturnDto;
import swEngineeringTeam1.closetProject.Entity.UserEntity;
import swEngineeringTeam1.closetProject.Service.ClothesService;
import swEngineeringTeam1.closetProject.Service.LoginService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mycloset")
public class ClothesController {

    private final ClothesService clothesService;
    private final LoginService loginService;

    public UserEntity getUser(Long userCode){
        return loginService.getUser(userCode);
    }

    //로그인 상태로 옷장 접속시 userCode를 통해 해당 사용자의 옷 데이터를 전송
    @GetMapping("/")
    public Map<String, Object> readClothes(
            @RequestParam(required = false) String season,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String material,
            @RequestParam Long userCode) throws IOException {
        //Long userCode = getUserCodeFromRequest(request);
        UserEntity user = getUser(userCode);
        return clothesService.readClothes(user, season, color, type, material);
    }

    @PostMapping("/")
    public Map<String, Object> createClothes (@RequestParam String jsonClothesDto, @RequestParam Long userCode, @RequestPart MultipartFile file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ClothesDto clothesDto = null;
        UserEntity user= getUser(userCode);
        try {
            clothesDto = mapper.readValue(jsonClothesDto, ClothesDto.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return clothesService.createClothes(clothesDto, file, user);
    }

    @GetMapping("/{id}")
    public Map<String, Object> updateClothes(@PathVariable Long id, @RequestParam Long userCode){
        UserEntity user = getUser(userCode);
        return clothesService.updateClothes(id,user);
    }

    @PutMapping("/{id}")
    public Map<String, Object> updateClothes(@PathVariable Long id, @RequestParam String jsonClothesDto, @RequestPart MultipartFile file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ClothesDto clothesDto = null;
        try {
            clothesDto = mapper.readValue(jsonClothesDto, ClothesDto.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return clothesService.finUpdateClothes(id, file, clothesDto);
    }

    @DeleteMapping("/{id}")
    public Map<String, Object> deleteClothes(@PathVariable Long id, @RequestParam Long userCode){
        UserEntity user = getUser(userCode);
        return clothesService.deleteClothes(id,user);
    }
}

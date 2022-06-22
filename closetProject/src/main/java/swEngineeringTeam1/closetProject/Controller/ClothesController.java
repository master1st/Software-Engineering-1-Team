package swEngineeringTeam1.closetProject.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import swEngineeringTeam1.closetProject.Dto.ClothesDto;
import swEngineeringTeam1.closetProject.Dto.ClothesReturnDto;
import swEngineeringTeam1.closetProject.Entity.ClothesEntity;
import swEngineeringTeam1.closetProject.Entity.UserEntity;
import swEngineeringTeam1.closetProject.Service.ClothesService;
import swEngineeringTeam1.closetProject.Service.LoginService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mycloset")
public class ClothesController {

    private final ClothesService clothesService;
    private final LoginService loginService;

    //로그인 상태로 옷장 접속시 userCode를 통해 해당 사용자의 옷 데이터를 전송
    @GetMapping("/")
    public List<ClothesReturnDto> readClothes(
            @RequestParam(required = false) String season,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String material,
            HttpServletRequest request) throws IOException {
        //Long userCode = getUserCodeFromRequest(request);
        UserEntity user = loginService.getLoginUser(request);
        return clothesService.readClothes(user, season, color, type, material);
    }

    @PostMapping("/create")
    public String createClothes (@RequestParam ClothesDto clothesDto, @RequestPart MultipartFile file, HttpServletRequest request) throws IOException {
        UserEntity user = loginService.getLoginUser(request);
        return clothesService.createClothes(clothesDto, file, user);
    }

    @GetMapping("/update/{id}")
    public ClothesEntity updateClothes(@PathVariable Long id, HttpServletRequest request){
        UserEntity user = loginService.getLoginUser(request);
        return clothesService.updateClothes(id,user);
    }

    @PutMapping("/update/{id}")
    public String updateClothes(@PathVariable Long id, @RequestParam ClothesDto clothesDto, @RequestPart MultipartFile file) throws IOException {
        return clothesService.finUpdateClothes(id, file, clothesDto);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteClothes(@PathVariable Long id, HttpServletRequest request){
        UserEntity user = loginService.getLoginUser(request);
        clothesService.deleteClothes(id,user);
        return "삭제 성공";
    }
}

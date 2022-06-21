package swEngineeringTeam1.closetProject.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import swEngineeringTeam1.closetProject.Dto.ClothesDto;
import swEngineeringTeam1.closetProject.Entity.ClothesEntity;
import swEngineeringTeam1.closetProject.Entity.UserEntity;
import swEngineeringTeam1.closetProject.Service.ClothesService;
import swEngineeringTeam1.closetProject.Service.LoginService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mycloset")
public class ClothesController {

    private final ClothesService clothesService;
    private final LoginService loginService;

    //로그인 상태로 옷장 접속시 userCode를 통해 해당 사용자의 옷 데이터를 전송
    @GetMapping("/")
    public List<ClothesEntity> readClothes(
            @RequestParam(required = false) String season,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String material,
            HttpServletRequest request){
        //Long userCode = getUserCodeFromRequest(request);
        UserEntity user = loginService.getLoginUser(request);
        return clothesService.readClothes(user, season, color, type, material);
    }

    @PostMapping("/create")
    public String createClothes(@RequestBody ClothesDto clothesDto, HttpServletRequest request){
        UserEntity user = loginService.getLoginUser(request);
        return clothesService.createClothes(clothesDto, user);
    }

    @GetMapping("/update/{id}")
    public ClothesEntity updateClothes(@PathVariable Long id, HttpServletRequest request){
        UserEntity user = loginService.getLoginUser(request);
        return clothesService.updateClothes(id,user);
    }

    @PutMapping("/update/{id}")
    public String updateClothes(@PathVariable Long id, @RequestBody ClothesDto clothesDto){
        return clothesService.finUpdateClothes(id, clothesDto);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteClothes(@PathVariable Long id, HttpServletRequest request){
        UserEntity user = loginService.getLoginUser(request);
        clothesService.deleteClothes(id,user);
        return "삭제 성공";
    }
}

package swEngineeringTeam1.closetProject.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import swEngineeringTeam1.closetProject.Dto.ClothesDto;
import swEngineeringTeam1.closetProject.Dto.ClothesIdDto;
import swEngineeringTeam1.closetProject.Dto.ReadClothesDto;
import swEngineeringTeam1.closetProject.Entity.ClothesEntity;
import swEngineeringTeam1.closetProject.Service.ClothesService;
import swEngineeringTeam1.closetProject.Service.LoginService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mycloset")
public class ClothesController {

    private final ClothesService clothesService;

    private Long getUserCodeFromRequest(HttpServletRequest request){
        HttpSession session = request.getSession();
        return (Long)session.getAttribute("userCode");
    }

    //로그인 상태로 옷장 접속시 userCode를 통해 해당 사용자의 옷 데이터를 전송
    @GetMapping("/")
    public List<ClothesEntity> allClothes(@RequestParam(value="userCode") Long userCode/*,HttpServletRequest request*/){
        //Long userCode = getUserCodeFromRequest(request);
        return clothesService.allClothes(userCode);
    }

    @PostMapping("/create")
    public ClothesEntity createClothes(@RequestBody ClothesDto clothesDto, HttpServletRequest request){
//        HttpSession session = request.getSession();
//        Long userCode = (Long)session.getAttribute("userCode");
//        Long userCode = getUserCodeFromRequest(request);
        Long userCode = clothesDto.getUserCode();
        return clothesService.createClothes(clothesDto, userCode);
    }

    @PostMapping("/read")
    public List<ClothesEntity> readClothes(@RequestBody ReadClothesDto readClothesDto, HttpServletRequest request){
        Long userCode = getUserCodeFromRequest(request);
        List<ClothesEntity> clothes = clothesService.readClothes(readClothesDto, userCode);
        return clothes;
    }

//    @PutMapping("/update")
//    public String updateClothes(@RequestBody ClothesDto clothesDto, HttpServletRequest request){
//        Long userCode =
//    }

//    @PostMapping("/update")
//    public String updateClothes(@RequestBody ClothesDto clothesDto, HttpServletRequest request){
//        Long userCode =
//    }

    @GetMapping("/delete")
    public String deleteClothes(@RequestBody ClothesIdDto clothesIdDto){
        clothesService.deleteClothes(clothesIdDto);
        return "삭제 성공";
    }
}

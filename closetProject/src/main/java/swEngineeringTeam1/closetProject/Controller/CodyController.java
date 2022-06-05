package swEngineeringTeam1.closetProject.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.json.simple.parser.ParseException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import swEngineeringTeam1.closetProject.Dto.ClothesDtoForCody;
import swEngineeringTeam1.closetProject.Dto.CodyRequestDto;
import swEngineeringTeam1.closetProject.Dto.CodyReturnDto;
import swEngineeringTeam1.closetProject.Entity.UserEntity;
import swEngineeringTeam1.closetProject.Service.CodyService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@RestController
@Getter
@Setter
@RequiredArgsConstructor
public class CodyController {
    private final CodyService codyService;

    @PostMapping(value = "/mycody", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void createCody(@RequestParam String jsonString, @RequestParam(required = false) MultipartFile file, HttpServletRequest request) throws IOException, ParseException {
        HttpSession session= request.getSession();
        UserEntity user = (UserEntity) session.getAttribute("user");

     // JSONArray jsonArray = codyService.toJsonArray(jsonString);
        ObjectMapper mapper = new ObjectMapper();
        CodyRequestDto codyRequestDto = mapper.readValue(jsonString, CodyRequestDto.class);
        List<ClothesDtoForCody> clothesList = codyRequestDto.getClothesList();

      //  List<ClothesDto> clothesList = clothes.getClothesList();
//        for (ClothesDto c : clothesList) {
//            System.out.println(c.getClothesId());
//            System.out.println(c.getClothesImage());
//            System.out.println(c.getMaterial());
//            System.out.println(c.getSeason());
//            System.out.println(c.getType());
//            System.out.println(c.getColor());
//            System.out.println(c.getUserCode());
//        }
        codyService.createCody(user, clothesList, file);
    }

    @GetMapping("/mycody")
    public List<CodyReturnDto> getCody(HttpServletRequest request) {
        HttpSession session= request.getSession();
        UserEntity user = (UserEntity) session.getAttribute("user");
        List<CodyReturnDto> codys = codyService.getCody(user);
        return codys;

    }

//    @PostMapping
//    public void updateCody() {
//
//    }

    @DeleteMapping
    public void deleteCody(HttpServletRequest request) {
        HttpSession session= request.getSession();
        UserEntity user = (UserEntity) session.getAttribute("user");
        codyService.deleteCody();
    }


}

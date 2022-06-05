package swEngineeringTeam1.closetProject.Service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import swEngineeringTeam1.closetProject.Dto.ClothesDtoForCody;
import swEngineeringTeam1.closetProject.Dto.CodyReturnDto;
import swEngineeringTeam1.closetProject.Entity.ClothesEntity;
import swEngineeringTeam1.closetProject.Entity.CodyEntity;
import swEngineeringTeam1.closetProject.Entity.UserEntity;
import swEngineeringTeam1.closetProject.Repository.CodyRepository;
import swEngineeringTeam1.closetProject.Repository.LoginRepository;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class CodyService {
    private final CodyRepository codyRepository;
    private final LoginRepository loginRepository;
    private final ServletContext servletContext;

    public List<CodyReturnDto> getCody (UserEntity user) {
        List<CodyEntity> codyEntities = codyRepository.findAllByCodyIdUserCode(1L);

        List<CodyReturnDto> returnDtos = new ArrayList<>();
        CodyReturnDto codyReturnDto = new CodyReturnDto();
        Long currentNum=1L;
        for (CodyEntity c : codyEntities) {
            if (c.getCodyId().getCodyNum() > currentNum) {
                returnDtos.add(codyReturnDto);
                currentNum++;
                codyReturnDto = new CodyReturnDto();
            }
            codyReturnDto.toDto(c);
        }
        return returnDtos;
    }

    public void createCody(UserEntity user, List<ClothesDtoForCody> clothes, MultipartFile file) throws IOException {
        String loc = "default";
        loc=imageSave(file);
        Optional<UserEntity> mock = loginRepository.findById(1L);

        Long maxCodyNum = codyRepository.findMaxCodyNum();
        if(maxCodyNum==null) maxCodyNum=0L; //if there is no cody

        for (ClothesDtoForCody c : clothes) {
            ClothesEntity clothesEntity = new ClothesEntity(mock.get(), c);
            codyRepository.save(new CodyEntity(mock.get(), clothesEntity, loc, maxCodyNum));
        }
    }

    public void updateCody() {

    }

    public void deleteCody() {

    }

    public String imageSave (MultipartFile file) throws IOException {
        String originalFileName = file.getOriginalFilename();
        System.out.println("here");
        //String root= servletContext.getRealPath("/");
      //  System.out.println(root);
        File dest = new File("thisIsTest");
        file.transferTo(dest);
        return originalFileName;
    }

    public JSONArray toJsonArray (String s) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONArray parse = (JSONArray) parser.parse(s);
        return parse;
    }


}

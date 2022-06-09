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
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class CodyService {
    private final CodyRepository codyRepository;
    private final LoginRepository loginRepository;
    private final ServletContext servletContext;

    public List<CodyReturnDto> getAllCody (UserEntity user) {
        List<CodyEntity> codyEntities = codyRepository.findAllByCodyIdUserCode(user.getUserCode());

        List<CodyReturnDto> returnDtos = new ArrayList<>();
        CodyReturnDto codyReturnDto = new CodyReturnDto();
        Long currentNum=1L; //initialization
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

    public Map<String,Object> createCody(UserEntity user, List<ClothesDtoForCody> clothes, MultipartFile file) {
        Map<String,Object> response = new HashMap<>();
        try {
            String loc = "default";
           // loc = imageSave(file);
            // Optional<UserEntity> mock = loginRepository.findById(1L);

            Long maxCodyNum = codyRepository.findMaxCodyNum();
            if (maxCodyNum == null) maxCodyNum = 0L; //if there is no cody

            for (ClothesDtoForCody c : clothes) {
                ClothesEntity clothesEntity = new ClothesEntity(user, c);
                codyRepository.save(new CodyEntity(user, clothesEntity, loc, maxCodyNum));
            }
            response.put("success",true);
            response.put("message","코디 저장이 완료되었습니다");

        }
        catch (Exception e) {
            System.out.println(e);
            response.put("sucess", false);
            response.put("message","코디 저장을 실패하였습니다");
        }
        return response;
    }

    public List<CodyEntity> getExistingCody(Long codyNum){
        List<CodyEntity> cody = codyRepository.findAllByCodyIdCodyNum(codyNum);
        return cody;
    }

    public void updateCody(UserEntity user, Long codyNum, List<ClothesDtoForCody> clothesList, MultipartFile file) throws IOException {
        deleteCody(codyNum);
        createCody(user, clothesList, file);
    }

    public Map<String, Object> deleteCody(Long codyNum) {
        Map<String,Object> response = new HashMap<>();
        try {
            codyRepository.deleteByCodyIdCodyNum(codyNum);
            response.put("success",true);
            response.put("message","코디 삭제가 완료되었습니다");
        }
        catch (Exception e) {
            response.put("success",false);
            response.put("message","코디 삭제에 실패하였습니다");
        }
        return response;

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

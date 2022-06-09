package swEngineeringTeam1.closetProject.Service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import swEngineeringTeam1.closetProject.Dto.ClothesDtoForCody;
import swEngineeringTeam1.closetProject.Dto.CodyReturnDto;
import swEngineeringTeam1.closetProject.Entity.ClothesEntity;
import swEngineeringTeam1.closetProject.Entity.CodyEntity;
import swEngineeringTeam1.closetProject.Entity.CodyId;
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

    public Map<String, Object> getAllCody (UserEntity user) {
        Map<String,Object> response = new HashMap<>();
        List<CodyEntity> codyEntities = codyRepository.findAllByCodyIdUserCode(user.getUserCode(), Sort.by("codyId.codyNum"));

       if(codyEntities.isEmpty()) {
           response.put("success",false);
           response.put("message","저장된 코디가 없습니다");
       }

       else {
           List<List<CodyReturnDto>> cody = new ArrayList<>();
           Long currentCodyNum = codyEntities.get(0).getCodyId().getCodyNum();
           ArrayList<CodyReturnDto> codyReturnDtos = new ArrayList<>();

           for (CodyEntity c : codyEntities) {
               if (currentCodyNum < c.getCodyId().getCodyNum()) {
                   cody.add(codyReturnDtos);
                   codyReturnDtos = new ArrayList<>();
               }
               codyReturnDtos.add(new CodyReturnDto(c));
               currentCodyNum= c.getCodyId().getCodyNum();
           }
           cody.add(codyReturnDtos);

           response.put("success", true);
           response.put("message", "코디 불러오기가 완료되었습니다");
           response.put("codyList", cody);
       }
        return response;
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

    public Map<String,Object> getExistingCody(Long codyNum){
        List<CodyEntity> codyEntities = codyRepository.findAllByCodyIdCodyNum(codyNum);
        Map<String,Object> response = new HashMap<>();
        if (codyEntities.isEmpty()) {
            response.put("success",false);
            response.put("message","해당하는 코디가 없습니다");
        }
        else {
            List<CodyReturnDto> cody = new ArrayList<>();
            for (CodyEntity c : codyEntities) {
                cody.add(new CodyReturnDto(c));
            }
            response.put("success",true);
            response.put("message","코디 찾기에 성공하였습니다");
            response.put("cody",cody);
        }
        return response;
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

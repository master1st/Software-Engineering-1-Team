package swEngineeringTeam1.closetProject.Service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class CodyService {
    private final CodyRepository codyRepository;
    private final LoginRepository loginRepository;
    private final ServletContext servletContext;

    public Map<String, Object> getAllCody (UserEntity user) throws IOException {
        Map<String,Object> response = new HashMap<>();
        List<CodyEntity> codyEntities = codyRepository.findAllByCodyIdUserCode(user.getUserCode(), Sort.by("codyId.codyNum"));

       if(codyEntities.isEmpty()) {
           response.put("success",false);
           response.put("message","저장된 코디가 없습니다");
       }

       else {
           List<List<CodyReturnDto>> result = new ArrayList<>();
           Long currentCodyNum = codyEntities.get(0).getCodyId().getCodyNum();
           ArrayList<CodyReturnDto> codyReturnDtos = new ArrayList<>();

           for (CodyEntity c : codyEntities) {
               if (currentCodyNum < c.getCodyId().getCodyNum()) {
                   result.add(codyReturnDtos);
                   codyReturnDtos = new ArrayList<>();
               }
               CodyReturnDto noImagedto = new CodyReturnDto(c);


               noImagedto.setCodyImage(ImageRead(c.getCodyImage()));
               codyReturnDtos.add(noImagedto);

               currentCodyNum= c.getCodyId().getCodyNum();
           }
           result.add(codyReturnDtos);

           response.put("success", true);
           response.put("message", "코디 불러오기가 완료되었습니다");
           response.put("codyList", result);
       }
        return response;
    }

    public Map<String,Object> createCody(UserEntity user, List<ClothesDtoForCody> clothes, MultipartFile file) {
        Map<String,Object> response = new HashMap<>();
        try {

            Long maxCodyNum = codyRepository.findMaxCodyNum();
            if (maxCodyNum == null) maxCodyNum = 0L; //if there is no cody

            String loc = imageSave(file,maxCodyNum+1);

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
        Map<String,Object> response = new HashMap<>();
        try {
           deleteCody(codyNum);
           createCody(user, clothesList, file);
           response.put("success",true);
           response.put("message","코디 수정이 완료되었습니다");
       }
       catch (Exception e) {
           e.printStackTrace();
           response.put("success",false);
           response.put("message","코디 수정이 실패하였습니다");
       }
    }

    public Map<String, Object> deleteCody(Long codyNum) {
        Map<String,Object> response = new HashMap<>();
        try {
            CodyEntity cody = codyRepository.findFirstByCodyIdCodyNum(codyNum);
            String codyImageName = cody.getCodyImage();
            File file = new File(getFilePath()+codyNum+"_"+codyImageName);
            file.delete();
            codyRepository.deleteByCodyIdCodyNum(codyNum);
            response.put("success",true);
            response.put("message","코디 삭제가 완료되었습니다");
        }
        catch (Exception e) {
            e.printStackTrace();
            response.put("success",false);
            response.put("message","코디 삭제에 실패하였습니다");
        }
        return response;

    }

    public String getFilePath() {
        return servletContext.getRealPath("/")+"codyImage\\";
    }

    public String imageSave (MultipartFile file, Long codyNum) throws IOException {
        String originalFileName = file.getOriginalFilename();

        String root = getFilePath();

        File dest = new File(root+codyNum+"_"+originalFileName);
        file.transferTo(dest);
        return originalFileName;
    }

    public JSONArray toJsonArray (String s) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONArray parse = (JSONArray) parser.parse(s);
        return parse;
    }

    public String  ImageRead (String fileName) throws IOException {
        String filePath = getFilePath();
        System.out.println(filePath+fileName);
        FileInputStream fis = new FileInputStream(filePath+fileName);
        return Base64Utils.encodeToString(fis.readAllBytes()); //base64로 인코딩한 이미지 정보를 리턴
    }

}

package swEngineeringTeam1.closetProject.Service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import swEngineeringTeam1.closetProject.Dto.ClothesDto;
import swEngineeringTeam1.closetProject.Dto.ClothesReturnDto;
import swEngineeringTeam1.closetProject.Entity.ClothesEntity;
import swEngineeringTeam1.closetProject.Entity.UserEntity;
import swEngineeringTeam1.closetProject.Repository.ClothesRepository;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ClothesService {
    private final ClothesRepository clothesRepository;
    private final ServletContext servletContext;

    public Map<String, Object> createClothes(ClothesDto clothesDto, MultipartFile file, UserEntity user) throws IOException{
        Map<String,Object> response = new HashMap<>();
        try {
            String loc = imageSave(file,clothesRepository.findMaxclothesNum()+1);
            clothesRepository.save(new ClothesEntity(user,loc,clothesDto));
            response.put("success",true);
            response.put("message","저장 성공");
            return response;
        }
        catch (Exception e){
            response.put("success",false);
            response.put("message","예외 발생");
            return response;
        }
    }

    public Map<String, Object> readClothes(UserEntity user, String season, String color, String type, String material) throws IOException {
        Map<String,Object> response = new HashMap<>();
        List<ClothesEntity> clothesEntityList;
        if(season==null &&
            color==null &&
            type==null &&
            material==null
        )
            clothesEntityList = clothesRepository.findAllByUser(user);
        else if(season==null &&
                color==null &&
                type==null
        ) clothesEntityList = clothesRepository.findAllByUserAndMaterial(user,material);
        else if(season==null &&
                color==null &&
                material==null
        ) clothesEntityList = clothesRepository.findAllByUserAndType(user,type);
        else if(season==null &&
                type==null &&
                material==null
        ) clothesEntityList = clothesRepository.findAllByUserAndColor(user, color);
        else if(color==null &&
                type==null &&
                material==null
        ) clothesEntityList = clothesRepository.findAllByUserAndSeason(user,season);
        else if(season==null &&
                color==null
        ) clothesEntityList = clothesRepository.findAllByUserAndTypeAndMaterial(user,type,material);
        else if(season==null &&
                type==null
        ) clothesEntityList = clothesRepository.findAllByUserAndColorAndMaterial(user,color,material);
        else if(color==null &&
                type==null
        ) clothesEntityList = clothesRepository.findAllByUserAndSeasonAndMaterial(user,season,material);
        else if(color==null &&
                material==null
        ) clothesEntityList = clothesRepository.findAllByUserAndSeasonAndType(user,season,type);
        else if(season==null &&
                material==null
        ) clothesEntityList = clothesRepository.findAllByUserAndColorAndType(user,color,type);
        else if(type==null &&
                material==null
        ) clothesEntityList = clothesRepository.findAllByUserAndSeasonAndColor(user,season,color);
        else if(material==null
        ) clothesEntityList = clothesRepository.findAllByUserAndSeasonAndColorAndType(user,season, color, type);
        else if(type==null
        ) clothesEntityList = clothesRepository.findAllByUserAndSeasonAndColorAndMaterial(user,season,color,material);
        else if(color==null
        ) clothesEntityList = clothesRepository.findAllByUserAndSeasonAndTypeAndMaterial(user,season, type, material);
        else if(season==null
        ) clothesEntityList = clothesRepository.findAllByUserAndColorAndTypeAndMaterial(user,color, type, material);
        else
            clothesEntityList = clothesRepository.findAllByUserAndSeasonAndColorAndTypeAndMaterial(user,season,color,type,material);
        List<ClothesReturnDto> clothesReturnDtoList = new ArrayList<>();
        for (ClothesEntity c : clothesEntityList){
            ClothesReturnDto clothesReturnDto = new ClothesReturnDto(c,getFilePath()+c.getClothesImage());
            clothesReturnDtoList.add(clothesReturnDto);
        }
        response.put("success",true);
        response.put("clothesList",clothesReturnDtoList);
        return response;
    }
    public Map<String, Object> updateClothes(Long clothesId, UserEntity user){
        Map<String,Object> response = new HashMap<>();
        ClothesEntity clothes = clothesRepository.findById(clothesId).orElse(null);
        ClothesReturnDto clothesReturnDto = new ClothesReturnDto(clothes,getFilePath()+clothes.getClothesImage());
        //수정하려는 옷과 접속한 user의 정보가 일치하지 않으면 null 반환
        if (clothes.getUser() != user ){
            response.put("success",false);
        }
        else {
            response.put("success",true);
            response.put("clothes",clothesReturnDto);
        }
        return response;
    }

    public Map<String,Object> finUpdateClothes(Long clothesId, MultipartFile newImage, ClothesDto clothesDto) throws IOException{
        Map<String,Object> response = new HashMap<>();
        ClothesEntity clothes = clothesRepository.findById(clothesId).orElse(null);
        String clothesImageName = clothes.getClothesImage();
        File file = new File(getFilePath()+clothesImageName);
        file.delete();
        try {
            String loc = imageSave(newImage,clothesId);
            clothes.setClothesImage(loc);
            clothes.setColor(clothesDto.getColor());
            clothes.setSeason(clothesDto.getSeason());
            clothes.setType(clothesDto.getType());
            clothes.setMaterial(clothesDto.getMaterial());
            clothesRepository.save(clothes);
            response.put("success",true);
            return response;
        }
        catch (Exception e){
            response.put("success",false);
            return response;
        }
    }

    public Map<String, Object> deleteClothes(Long clothesId, UserEntity user){
        Map<String,Object> response = new HashMap<>();
        ClothesEntity clothes= clothesRepository.findById(clothesId).orElse(null);
        if (clothes.getUser() != user ) {
            response.put("success",false);
            response.put("message","권한이 없습니다.");
        }
        else{
            String clothesImageName = clothes.getClothesImage();
            File file = new File(getFilePath()+clothesImageName);
            file.delete();
            clothesRepository.delete(clothes);
            response.put("success",true);
            response.put("message","삭제 성공");
        }
        return response;
    }

    public String getFilePath() {
        return servletContext.getRealPath("/")+"clothesImage\\";
    }

    public String imageSave (MultipartFile file,Long clothesId) throws IOException {
        String originalFileName = file.getOriginalFilename();

        String root = getFilePath();
        File dest = new File(root+clothesId+"_"+originalFileName);
        file.transferTo(dest);
        return clothesId+"_"+originalFileName;
    }

}

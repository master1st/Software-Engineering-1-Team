package swEngineeringTeam1.closetProject.Service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.web.multipart.MultipartFile;
import swEngineeringTeam1.closetProject.Dto.ClothesDto;
import swEngineeringTeam1.closetProject.Dto.ClothesReturnDto;
import swEngineeringTeam1.closetProject.Entity.ClothesEntity;
import swEngineeringTeam1.closetProject.Entity.UserEntity;
import swEngineeringTeam1.closetProject.Repository.ClothesRepository;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClothesService {
    private final ClothesRepository clothesRepository;
    private final LoginService loginService;
    private final ServletContext servletContext;

    public String createClothes(ClothesDto clothesDto, MultipartFile file, UserEntity user) throws IOException{
        try {
            String loc = imageSave(file);
            clothesRepository.save(new ClothesEntity(user,loc,clothesDto));
            return "저장 성공";
        }
        catch (Exception e){
            return "저장 실패";
        }
    }

    public List<ClothesReturnDto> readClothes(UserEntity user, String season, String color, String type, String material) throws IOException {
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
            ClothesReturnDto clothesReturnDto = new ClothesReturnDto(c);
            clothesReturnDto.setClothesImage(ImageRead(c.getClothesImage()));
            clothesReturnDtoList.add(new ClothesReturnDto(c));
        }
        return clothesReturnDtoList;
    }
    public ClothesEntity updateClothes(Long clothesId, UserEntity user){
        ClothesEntity clothes = clothesRepository.findById(clothesId).orElse(null);
        //수정하려는 옷과 접속한 user의 정보가 일치하지 않으면 null 반환
        if (clothes.getUser() != user ){
            return null;
        }
        else {
            return clothes;
        }
    }

    public String finUpdateClothes(Long clothesId, MultipartFile newImage, ClothesDto clothesDto) throws IOException{
        ClothesEntity clothes = clothesRepository.findById(clothesId).orElse(null);
        String clothesImageName = clothes.getClothesImage();
        File file = new File(getFilePath()+clothesImageName);
        file.delete();
        try {
            String loc = imageSave(newImage);
            clothes.setClothesImage(loc);
            clothes.setColor(clothesDto.getColor());
            clothes.setSeason(clothesDto.getSeason());
            clothes.setType(clothesDto.getType());
            clothes.setMaterial(clothesDto.getMaterial());
            clothesRepository.save(clothes);
            return "업데이트 성공";
        }
        catch (Exception e){
            return "업데이트 실패";
        }
    }

    public String deleteClothes(Long clothesId, UserEntity user){
        ClothesEntity clothes= clothesRepository.findById(clothesId).orElse(null);
        if (clothes.getUser() != user ) {
            return "권한이 없습니다.";
        }
        else{
            String clothesImageName = clothes.getClothesImage();
            File file = new File(getFilePath()+clothesId+"_"+clothesImageName);
            file.delete();
            clothesRepository.delete(clothes);
            return "삭제 성공";
        }
    }

    public String getFilePath() {
        return servletContext.getRealPath("/")+"clothesImage\\";
    }

    public String imageSave (MultipartFile file) throws IOException {
        String originalFileName = file.getOriginalFilename();

        String root = getFilePath();

        File dest = new File(root+originalFileName);
        file.transferTo(dest);
        return originalFileName;
    }

    public String ImageRead (String fileName) throws IOException {
        String filePath = getFilePath();
        System.out.println(filePath+fileName);
        FileInputStream fis = new FileInputStream(filePath+fileName);
        return Base64Utils.encodeToString(fis.readAllBytes()); //base64로 인코딩한 이미지 정보를 리턴
    }

}

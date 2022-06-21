package swEngineeringTeam1.closetProject.Service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import swEngineeringTeam1.closetProject.Dto.ClothesDto;
import swEngineeringTeam1.closetProject.Entity.ClothesEntity;
import swEngineeringTeam1.closetProject.Entity.UserEntity;
import swEngineeringTeam1.closetProject.Repository.ClothesRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClothesService {
    private final ClothesRepository clothesRepository;
    private final LoginService loginService;

    public String createClothes(ClothesDto clothesDto, UserEntity user){
        clothesRepository.save(new ClothesEntity(user,clothesDto));
        return "저장 성공";
    }

    public List<ClothesEntity> readClothes(UserEntity user, String season, String color, String type, String material){
        if(season==null &&
            color==null &&
            type==null &&
            material==null
        )
            return clothesRepository.findAllByUser(user);

        else if(season==null &&
                color==null &&
                type==null
        ) return clothesRepository.findAllByUserAndMaterial(user,material);
        else if(season==null &&
                color==null &&
                material==null
        ) return clothesRepository.findAllByUserAndType(user,type);
        else if(season==null &&
                type==null &&
                material==null
        ) return clothesRepository.findAllByUserAndColor(user, color);
        else if(color==null &&
                type==null &&
                material==null
        ) return clothesRepository.findAllByUserAndSeason(user,season);
        else if(season==null &&
                color==null
        ) return clothesRepository.findAllByUserAndTypeAndMaterial(user,type,material);
        else if(season==null &&
                type==null
        ) return clothesRepository.findAllByUserAndColorAndMaterial(user,color,material);
        else if(color==null &&
                type==null
        ) return clothesRepository.findAllByUserAndSeasonAndMaterial(user,season,material);
        else if(color==null &&
                material==null
        ) return clothesRepository.findAllByUserAndSeasonAndType(user,season,type);
        else if(season==null &&
                material==null
        ) return clothesRepository.findAllByUserAndColorAndType(user,color,type);
        else if(type==null &&
                material==null
        ) return clothesRepository.findAllByUserAndSeasonAndColor(user,season,color);
        else if(material==null
        ) return clothesRepository.findAllByUserAndSeasonAndColorAndType(user,season, color, type);
        else if(type==null
        ) return clothesRepository.findAllByUserAndSeasonAndColorAndMaterial(user,season,color,material);
        else if(color==null
        ) return clothesRepository.findAllByUserAndSeasonAndTypeAndMaterial(user,season, type, material);
        else if(season==null
        ) return clothesRepository.findAllByUserAndColorAndTypeAndMaterial(user,color, type, material);
        else
            return clothesRepository.findAllByUserAndSeasonAndColorAndTypeAndMaterial(user,season,color,type,material);
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

    public String finUpdateClothes(Long clothesId, ClothesDto clothesDto){
        ClothesEntity clothes = clothesRepository.findById(clothesId).orElse(null);
        clothes.setClothesImage(clothesDto.getClothesImage());
        clothes.setColor(clothesDto.getColor());
        clothes.setSeason(clothesDto.getSeason());
        clothes.setType(clothesDto.getType());
        clothes.setMaterial(clothesDto.getMaterial());
        clothesRepository.save(clothes);
        return "업데이트 성공";
    }

    public String deleteClothes(Long clothesId, UserEntity user){
        ClothesEntity clothes= clothesRepository.findById(clothesId).orElse(null);
        if (clothes.getUser() != user ) {
            return "권한이 없습니다.";
//            return True;
        }
        else{
            clothesRepository.delete(clothes);
            return "삭제 성공";
        }
    }
}

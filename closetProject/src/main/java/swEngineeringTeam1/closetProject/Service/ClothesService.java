package swEngineeringTeam1.closetProject.Service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import swEngineeringTeam1.closetProject.Dto.ClothesDto;
import swEngineeringTeam1.closetProject.Dto.ReadClothesDto;
import swEngineeringTeam1.closetProject.Entity.ClothesEntity;
import swEngineeringTeam1.closetProject.Entity.UserEntity;
import swEngineeringTeam1.closetProject.Repository.ClothesRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClothesService {
    private final ClothesRepository clothesRepository;
    private final LoginService loginService;

    public List<ClothesEntity> allClothes(Long userCode){
        return clothesRepository.findAllByUser(loginService.getUser(userCode));
    }

    public ClothesEntity createClothes(ClothesDto clothesDto, Long userCode){
        UserEntity user = loginService.getUser(userCode);
        ClothesEntity save = clothesRepository.save(new ClothesEntity(user,clothesDto));
        return save;
    }

    public List<ClothesEntity> readClothes(ReadClothesDto readClothesDto,Long UserCode){
        UserEntity user = loginService.getUser(UserCode);
        if(readClothesDto.getSeason().equals(null) &&
            readClothesDto.getColor().equals(null) &&
            readClothesDto.getType().equals(null) &&
            readClothesDto.getMaterial().equals(null)
        ) return clothesRepository.findAllByUser(user);
        else if(readClothesDto.getSeason().equals(null) &&
                readClothesDto.getColor().equals(null) &&
                readClothesDto.getType().equals(null)
        ) return clothesRepository.findAllByUserAndMaterial(user,readClothesDto.getMaterial());
        else if(readClothesDto.getSeason().equals(null) &&
                readClothesDto.getColor().equals(null) &&
                readClothesDto.getMaterial().equals(null)
        ) return clothesRepository.findAllByUserAndType(user,readClothesDto.getType());
        else if(readClothesDto.getSeason().equals(null) &&
                readClothesDto.getType().equals(null) &&
                readClothesDto.getMaterial().equals(null)
        ) return clothesRepository.findAllByUserAndColor(user, readClothesDto.getColor());
        else if(readClothesDto.getColor().equals(null) &&
                readClothesDto.getType().equals(null) &&
                readClothesDto.getMaterial().equals(null)
        ) return clothesRepository.findAllByUserAndSeason(user,readClothesDto.getSeason());
        else if(readClothesDto.getSeason().equals(null) &&
                readClothesDto.getColor().equals(null)
        ) return clothesRepository.findAllByUserAndTypeAndMaterial(user,readClothesDto.getType(),readClothesDto.getMaterial());
        else if(readClothesDto.getSeason().equals(null) &&
                readClothesDto.getType().equals(null)
        ) return clothesRepository.findAllByUserAndColorAndMaterial(user,readClothesDto.getColor(),readClothesDto.getMaterial());
        else if(readClothesDto.getColor().equals(null) &&
                readClothesDto.getType().equals(null)
        ) return clothesRepository.findAllByUserAndSeasonAndMaterial(user,readClothesDto.getSeason(),readClothesDto.getMaterial());
        else if(readClothesDto.getColor().equals(null) &&
                readClothesDto.getMaterial().equals(null)
        ) return clothesRepository.findAllByUserAndSeasonAndType(user,readClothesDto.getSeason(),readClothesDto.getType());
        else if(readClothesDto.getSeason().equals(null) &&
                readClothesDto.getMaterial().equals(null)
        ) return clothesRepository.findAllByUserAndColorAndType(user,readClothesDto.getColor(),readClothesDto.getType());
        else if(readClothesDto.getType().equals(null) &&
                readClothesDto.getMaterial().equals(null)
        ) return clothesRepository.findAllByUserAndSeasonAndColor(user,readClothesDto.getSeason(),readClothesDto.getColor());
        else if(readClothesDto.getMaterial().equals(null)
        ) return clothesRepository.findAllByUserAndSeasonAndColorAndType(user,readClothesDto.getSeason(), readClothesDto.getColor(), readClothesDto.getType());
        else if(readClothesDto.getType().equals(null)
        ) return clothesRepository.findAllByUserAndSeasonAndColorAndMaterial(user,readClothesDto.getSeason(),readClothesDto.getColor(),readClothesDto.getMaterial());
        else if(readClothesDto.getColor().equals(null)
        ) return clothesRepository.findAllByUserAndSeasonAndTypeAndMaterial(user,readClothesDto.getSeason(), readClothesDto.getType(), readClothesDto.getMaterial());
        else if(readClothesDto.getSeason().equals(null)
        ) return clothesRepository.findAllByUserAndColorAndTypeAndMaterial(user,readClothesDto.getColor(), readClothesDto.getType(), readClothesDto.getMaterial());
        else
            return clothesRepository.findAllByUserAndSeasonAndColorAndTypeAndMaterial(user,readClothesDto.getSeason(),readClothesDto.getColor(),readClothesDto.getType(),readClothesDto.getMaterial());
    }
    public ClothesEntity updateClothes(Long clothesId, Long userCode){
        ClothesEntity clothes = clothesRepository.findById(clothesId).orElse(null);
        UserEntity user = loginService.getUser(userCode);
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
        return "Success Update";
    }

    public String deleteClothes(Long clothesId, Long userCode){
        ClothesEntity clothes= clothesRepository.findById(clothesId).orElse(null);
        UserEntity user = loginService.getUser(userCode);
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

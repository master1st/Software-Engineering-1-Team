package swEngineeringTeam1.closetProject.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CodyRequestDto {
    private List<ClothesDtoForCody> clothesList;
}

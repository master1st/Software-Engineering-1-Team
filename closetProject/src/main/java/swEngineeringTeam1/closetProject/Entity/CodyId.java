package swEngineeringTeam1.closetProject.Entity;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
public class CodyId implements Serializable{


    private Long clothesId;
    private Long userCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CodyId codyId = (CodyId) o;
        return  clothesId.equals(codyId.clothesId)
                && userCode.equals((codyId.userCode));

    }

    @Override
    public int hashCode() {
        return Objects.hash( clothesId, userCode);
    }

    @Builder
    public CodyId (Long clothesId,  Long userCode) {
        this.clothesId=clothesId;
        //this.codyImage=codyImage;
        this.userCode=userCode;
    }

}

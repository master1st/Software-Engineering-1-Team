package swEngineeringTeam1.closetProject.Entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
public class CodyId implements Serializable{

    @Column(name = "clothesId")
    private Long clothesId;
    @Column(name = "userCode")
    private Long userCode;
    @Column(name = "codyNum")
    private Long codyNum;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CodyId codyId = (CodyId) o;
        return  clothesId.equals(codyId.clothesId)
                && userCode.equals(codyId.userCode)
                && codyNum.equals(codyId.codyNum);

    }

    @Override
    public int hashCode() {
        return Objects.hash( clothesId, userCode, codyNum);
    }

    @Builder
    public CodyId (Long clothesId,  Long userCode, Long codyNum) {
        this.clothesId=clothesId;
        //this.codyImage=codyImage;
        this.userCode=userCode;
        this.codyNum=codyNum+1;
    }

}

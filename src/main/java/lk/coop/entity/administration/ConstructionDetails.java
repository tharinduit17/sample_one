package lk.coop.entity.administration;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lk.coop.entity.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name="bc_con_details")
public class ConstructionDetails extends BaseEntity {

    @Column(length = 100)
    private  String typeName;

    @Column(length = 200)
    private String typeDescription;

    @Column(name = "inactive_reason")
    private String inactiveReason;

    @OneToMany(mappedBy = "constructionDetail", cascade = CascadeType.ALL)
//    @JsonIgnore
    private List<ConstructionDetailsItems> constructionDetailsItems;


}

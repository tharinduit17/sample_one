package lk.coop.entity.administration;

import lk.coop.entity.BaseEntity;
import lk.coop.enums.IsDefoult;
import lk.coop.enums.Status;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Data
@Entity
@Table(name = "bc_product_cover")
public class ProductCover extends BaseEntity{

    @Column(length = 5)
    private Integer pc_code;

    @Column(length =255)
    private String pc_description;

    @Column(precision = 12, scale = 2)
    private Double pc_rate;

    @Column(precision = 5, scale = 2)
    private Double pc_excess;

    @Column(precision = 10, scale = 2)
    private Double pc_excess_amount;

    @Column(length = 255)
    private String pc_tax_cover;

    @Column
    private Integer pc_cal_seq;

    @Column(precision = 10, scale = 2)
    private Double pc_event_limit;

    @Column(name = "pc_is_default", nullable = false)
    @ColumnDefault("1")
    private IsDefoult isDefoult;

    @Column(name = "inactive_reason")
    private String inactiveReason;

    @ManyToOne
    @JoinColumn(name = "insurance_cover_id", referencedColumnName = "id", nullable = false)
    private Cover cover;

    @OneToOne
    @JoinColumn(name = "insurance_product_id", referencedColumnName = "id", nullable = false)
    private InsuranceProduct insuranceProduct;

}

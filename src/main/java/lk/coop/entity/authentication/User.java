package lk.coop.entity.authentication;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lk.coop.entity.administration.BankBranch;
import lk.coop.entity.administration.Intermediary;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "BC_USER_TABLE")
public class User {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(
                            name = "uuid_gen_strategy_class",
                            value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
                    )
            }
    )
    private String id;

    @Column(unique = true)
    private String username;
    @Column

    @JsonIgnore
    private String password;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String phone;

    @Column
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "USER_ROLES",
            joinColumns = {
                    @JoinColumn(name = "USER_ID")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "ROLE_ID")})
    private List<Role> roles;

    @OneToOne
    @JoinColumn(name = "intermediary_id", referencedColumnName = "id", nullable = false)
    private Intermediary intermediary;

    @ManyToOne
    @JoinColumn(name = "bb_id", referencedColumnName = "id", nullable = false)
    private BankBranch bankBranch;
}
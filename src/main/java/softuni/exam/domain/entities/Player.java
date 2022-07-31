package softuni.exam.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "players")
public class Player extends BaseEntity {

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "number", nullable = false)
    private Integer number;

    @Column(name = "salary", nullable = false)
    private BigDecimal salary;

    @Enumerated(EnumType.STRING)
    private Position position;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "picture_id")
    private Picture picture;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "team_id")
    private Team team;


}

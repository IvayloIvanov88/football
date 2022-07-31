package softuni.exam.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "teams")
public class Team extends BaseEntity {

    @Column(name = "name")
    @Length(min = 3)
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "picture_id")
    private Picture picture;

}

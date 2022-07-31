package softuni.exam.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "pictures")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Picture extends BaseEntity {

    @Column(name = "url", nullable = false)
    private String url;
}

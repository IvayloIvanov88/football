package softuni.exam.domain.dtos;

import com.google.gson.annotations.Expose;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import softuni.exam.domain.entities.Position;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class PlayerSeedDto {

    @Expose
    @NotNull
    @Length(min = 3, max = 15)
    private String firstName;

    @Expose
    @Length(min = 3, max = 15)
    private String lastName;

    @Expose
    @Min(value = 1)
    @Max(value = 99)
    private Integer number;

    @Expose
    @DecimalMin(value = "0")
    private BigDecimal salary;

    @Expose
    @NotNull
    private Position position;

    @Expose
    @NotNull
    private PictureSeedDto picture;

    @Expose
    @NotNull
    private TeamSeedDto team;
}

package legoset.model;

import java.time.Year;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.*;

import jpa.YearConverter;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class LegoSet {

    @Id
    private String number;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false)
    @Convert(converter=YearConverter.class)
    private Year year;

    @Column(nullable=false)
    private int pieces;

}

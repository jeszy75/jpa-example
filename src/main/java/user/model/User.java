package user.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User {

    @Id
    private String username;

    @Column(nullable=false)
    private String password;

    @Version
    @Setter(AccessLevel.NONE)
    private long version;
}

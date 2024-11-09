package dbproject.DBproject.Customers.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Customers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "이름은 Null이 허용되지 않습니다.")
    @Column(nullable = false)
    private String name;

    @NotEmpty(message = "전화번호는 Null이 허용되지 않습니다.")
    @Column(nullable = false)
    private String phone;

    private String email;
    private int count;
    private LocalDate lastVisit;
    private String note;
}

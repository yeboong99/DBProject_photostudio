package dbproject.DBproject.Equipment.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Builder
@AllArgsConstructor
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private EquipmentCategory category;

    @Column(length = 200)
    private String name;

    @Column(length = 2)
    private String status;

    @Column
    private int price;

    @Column(length = 2048)
    private String image_path;
}

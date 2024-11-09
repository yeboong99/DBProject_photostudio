package dbproject.DBproject.room.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Builder
@AllArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length=200)
    private String name;

    @Column
    private Integer capacity;

    @Column
    private Integer price;

    @Column(length=2048)
    private String image_path;
}

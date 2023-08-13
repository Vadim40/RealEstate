package by.bsu.RealEstate.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "real_estates")
@SQLDelete(sql = "UPDATE real_estates SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RealEstate {
    @Id
    @SequenceGenerator(
            name = "real_estate_sequence",
            sequenceName = "real_estate_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "real_estate_sequence"
    )
    private long id;
    @Column(name = "type")
    private String type;
    @Column(name = "price")
    private int price;
    @Column(name = "square")
    private int square;
    @Column(name = "count_rooms")

    private int countRooms;
    @Column(name = "user_id")
    private long userId;
    @Column(name = "deleted")
    private boolean deleted = Boolean.FALSE;




}
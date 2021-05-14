package com.oneirros.sally2.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//import javax.persistence.*;

@Data
//@Entity(name = "user")
@Builder // Tworzenie nowej instancji
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

//    @Id
//    @SequenceGenerator(
//            name = "userSequence",
//            sequenceName = "userSequence",
//            allocationSize = 1
//    )
//    @GeneratedValue(
//            strategy = GenerationType.SEQUENCE,
//            generator = "userSequence"
//    )
    private Long id;
    private String email;


}

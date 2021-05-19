package com.oneirros.sally2.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;


@Data // Setery, Getery, ToString itp
@Table(name = "users")
@Entity
@Builder // Tworzenie nowej instancji
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "details_id", referencedColumnName = "id")
    private UserDetails userDetails;
    private UserRole role;
    private String email;
    private String login;
    private String password;
    private LocalDate createdOn;
}

package com.profilepicapi.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private String email;
    @Lob
    @Column(columnDefinition = "LONGBLOB", nullable = true)
    private byte[] profilePic;


}

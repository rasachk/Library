package com.rasachk.libraryapi.member.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Table(name = "LIB_MEMBERS")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MEMBER_ID")
    private Long id;
    @Column(name = "MEMBER_USERNAME")
    private String username;
    @Column(name = "MEMBER_PASSWORD")
    private String password;
    @Column(name = "MEMBER_NAME")
    private String name;
    @Column(name = "MEMBER_GENDER")
    private String gender;
    @Column(name = "MEMBER_AGE")
    private Integer age;
    @Column(name = "MEMBER_AVAILABILITY")
    private Boolean availability = true;
    @Enumerated(EnumType.STRING)
    @Column(name = "MEMBER_ROLES")
    private Role role;


    @Override
    public String toString() {
        return String.format("Member [id=%s, username=%s, password=%s, name=%s, gender=%s, age=%s, availability=%s]", id, username, password, name, gender, age, availability);
    }
}

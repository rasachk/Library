package com.rasachk.libraryapi.member.model.entity;

import com.rasachk.libraryapi.member.model.enums.GenderEnum;
import com.rasachk.libraryapi.member.model.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Table(name = Member.TABLE_NAME)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Member {

    public static final String TABLE_NAME = "TBL_MEMBER";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "C_MEMBER_ID")
    private Long id;
    @Column(name = "C_MEMBER_USERNAME")
    private String username;
    @Column(name = "C_MEMBER_PASSWORD")
    private String password;
    @Column(name = "C_MEMBER_NAME")
    private String name;
    @Column(name = "C_MEMBER_GENDER")
    private String gender;
    @Column(name = "C_MEMBER_AGE")
    private Integer age;
    @Column(name = "C_MEMBER_AVAILABILITY")
    private Boolean availability = true;
    @Enumerated(EnumType.STRING)
    @Column(name = "C_MEMBER_ROLES")
    private Role role;


    @Override
    public String toString() {
        return String.format("Member [id=%s, username=%s, password=%s, name=%s, gender=%s, age=%s, availability=%s]", id, username, password, name, gender, age, availability);
    }
}

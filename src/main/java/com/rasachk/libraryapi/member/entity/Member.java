package com.rasachk.libraryapi.member.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;


@Entity
@Table(name = "LIB_MEMBERS")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member implements UserDetails {
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
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword(){
        return password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    @Override
    public String toString() {
        return String.format("Member [id=%s, username=%s, password=%s, name=%s, gender=%s, age=%s, availability=%s]", id, username, password,name,gender,age,availability);
    }
}

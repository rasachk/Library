package com.rasachk.libraryapi.member.model.dto;

import com.rasachk.libraryapi.member.model.enums.GenderEnum;

public class MemberDto {
    private String username;
    private String password;
    private String name;
    private GenderEnum gender;
    private Integer age;

    public MemberDto() {
    }

    public MemberDto(String username, String password, String name, GenderEnum gender, Integer age) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GenderEnum getGender() {
        return gender;
    }

    public void setGender(GenderEnum gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }


    @Override
    public String toString() {
        return String.format("Member [username=%s, password=%s, name=%s, gender=%s, age=%s]", username, password, name, gender, age);
    }
}

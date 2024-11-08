package com.rasachk.libraryapi.authentication;

import com.rasachk.libraryapi.member.model.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {
    private String username;

    private RoleEnum roleEnum;
    private String token;

}

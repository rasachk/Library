package com.rasachk.libraryapi.authentication;

import com.rasachk.libraryapi.member.entity.Role;
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

    private Role role;
    private String token;

}

package com.example.springbootclient.common.dto;

import com.example.springbootclient.domain.entity.Authority;
import com.example.springbootclient.domain.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberRequestDto {

    private String memberId;
    private String password;
    private String name;
    private String email;
    private String phoneNumber;

    public Member toMember(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .memberId(memberId)
                .password(passwordEncoder.encode(password))
                .authority(Authority.GROUP_USER)
                .name(name)
                .email(email)
                .phoneNumber(phoneNumber)
                .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(memberId, password);
    }
}

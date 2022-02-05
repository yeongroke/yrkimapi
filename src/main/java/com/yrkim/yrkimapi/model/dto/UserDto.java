package com.yrkim.yrkimapi.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.yrkim.yrkimapi.model.entity.Role;
import com.yrkim.yrkimapi.model.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserDto extends BaseTimeDto {

    @NotNull
    @Schema(description = "회원번호")
    private Long id;

    @NotNull
    @Schema(description = "회원 이름")
    private String username;

    @NotNull
    @Schema(description = "회원 비밀번호")
    private String password;

    @NotNull
    @Schema(description = "회원 이메일")
    private String email;

    @NotNull
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Set<Role> roles;

    public UserDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.roles = user.getRoles();
        setCreated(user.getCreated());
        setModified(user.getModified());
    }

    public User toEntity() {
        User user = User.builder()
                .id(this.id)
                .email(this.email)
                .username(this.username)
                .password(this.password)
                .roles(this.roles)
                .build();

        return user;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}

package com.yrkim.yrkimapi.model.entity;

import com.yrkim.yrkimapi.model.dto.UserDto;
import com.yrkim.yrkimapi.utils.entity.EntityId;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@DynamicUpdate
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseTime {

    @Id @EntityId(id = "id" , property = "id")
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "username",
            nullable = false,
            updatable = false)
    @Size(max = 14)
    private String username;

    @NotBlank
    @NaturalId
    @Size(max = 100)
    @Column(name = "password",
            nullable = false,
            updatable = false)
    private String password;

    @NotBlank
    @Column(name = "email",
            nullable = false,
            updatable = false)
    @Size(max = 50)
    @Email
    private String email;

    @ManyToMany(fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<Role> roles = new HashSet<>();

    @Transient
    public UserDto toDto() {
        return new UserDto(this);
    }
}

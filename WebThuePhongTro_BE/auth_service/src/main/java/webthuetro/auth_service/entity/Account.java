package webthuetro.auth_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Accounts")
public class Account {

    @Id
    private String accountId;
    private String phoneNumber;
    private String email;
    private String password;
    private boolean emailVerified = false;
    private boolean phoneVerified = false;
    private boolean isDelete = false;
    private String googleId;
    private boolean linkedGoogle = false;
    private String googleName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
}

package EMS.User_Service.Entity;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;

@Getter
public class UserPrinciple implements UserDetails {
    private String username;
    private String password;

    private User.Role role;
    public UserPrinciple(User user) {
        this.username = user.getUserName();
        this.password = user.getPassword();
        this.role = user.getRole();
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

}

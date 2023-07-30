package hac.Models.User;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static hac.Models.User.Permission.*;

/**
 * <h1>Role</h1>
 * <p>
 *     Each role has a set of permissions
 * </p>
 */
@RequiredArgsConstructor
public enum Role {

    USER(
            Set.of(
                    MANAGER_READ,
                    MANAGER_CREATE
            )
    ),
    ADMIN(
            Set.of(
                    ADMIN_READ,
                    ADMIN_UPDATE,
                    ADMIN_DELETE,
                    ADMIN_CREATE,
                    MANAGER_READ,
                    MANAGER_UPDATE,
                    MANAGER_DELETE,
                    MANAGER_CREATE
            )
    ),
    MANAGER(
            Set.of(MANAGER_READ,
                    MANAGER_UPDATE,
                    MANAGER_DELETE,
                    MANAGER_CREATE)
    );

    @Getter
    private final Set<Permission> permissions;

    /**
     * Returns a list of authorities for a given role
     * @return List of authorities
     */
    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
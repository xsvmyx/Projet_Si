package tpsi2.conference.config;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserPrincipalAuthenticationToken extends AbstractAuthenticationToken {

    private final UserPrincipal userPrincipal;

    public UserPrincipalAuthenticationToken(UserPrincipal usp) {
        super(usp.getAuthorities());
        userPrincipal=usp;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public UserPrincipal getPrincipal() {
        return userPrincipal;
    }
}

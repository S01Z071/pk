/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.web.auth;

import java.util.ArrayList;
import java.util.Collection;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.GrantedAuthority;

/**
 *
 * @author andrej
 */
public class AuthenticationWrapper implements Authentication {

    private Authentication original;
    private Collection<? extends GrantedAuthority> extraRoles;

    public AuthenticationWrapper(Authentication original, Collection<? extends GrantedAuthority> extraRoles) {
        this.original = original;
        this.extraRoles = extraRoles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<? extends GrantedAuthority> originalRoles = original.getAuthorities();
        ArrayList< GrantedAuthority> roles = new ArrayList< GrantedAuthority>();
        /*for (GrantedAuthority grantedAuthority : originalRoles) {
            roles.add(grantedAuthority);
        }*/
    /*    for (GrantedAuthority grantedAuthority : extraRoles) {
            roles.add(grantedAuthority);
        }*/
        return extraRoles;
    }

    @Override
    public String getName() {
        return original.getName();
    }

    @Override
    public Object getCredentials() {
        return original.getCredentials();
    }

    @Override
    public Object getDetails() {
        return original.getDetails();
    }

    @Override
    public Object getPrincipal() {
        return original.getPrincipal();
    }

    @Override
    public boolean isAuthenticated() {
        return original.isAuthenticated();
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        original.setAuthenticated(isAuthenticated);
    }
}

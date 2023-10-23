/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.web.auth;

import java.util.ArrayList;
import java.util.List;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 *
 * @author Uporabnik
 */
public class LdapAuthenticationProvider implements AuthenticationProvider {
    
    LdapTemplate ldapTemplate;
    String userSearchFilter;
    
    public void setLdapTemplate(LdapTemplate ldapTemplate) {
        this.ldapTemplate = ldapTemplate;
    }
    
    public void setUserSearchFilter(String userSearchFilter) {
        this.userSearchFilter = userSearchFilter;
    }
    
    @Override
    public Authentication authenticate(Authentication a) throws AuthenticationException {
        String name = a.getName();
        if (name != null) {
            name = name.trim();
        }
        String pass = a.getCredentials().toString();
        if (pass != null) {
            pass = pass.trim();
        }
        if (pass == null || pass.isEmpty()) {
            return null;
        }
        ldapTemplate.setDefaultCountLimit(1);
        // boolean isAuth = ldapTemplate.authenticate("", "(&(cn=" + name + ")(objectClass=person))", pass);
        boolean isAuth = ldapTemplate.authenticate("", userSearchFilter.replace("{0}", name), pass);
        if (isAuth) {
            List<GrantedAuthority> grantedAuths = new ArrayList<>();
            grantedAuths.add(new SimpleGrantedAuthority("ROLE_AUTHENTICATED"));
            Authentication auth = new UsernamePasswordAuthenticationToken(name, pass.toCharArray(), grantedAuths);
            return auth;
        }
        return null;
        
    }
    
    @Override
    public boolean supports(Class<?> type) {
        return type.equals(UsernamePasswordAuthenticationToken.class);
    }
}

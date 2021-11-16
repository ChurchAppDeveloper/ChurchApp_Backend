package com.church.barnabas.security;
import java.util.Collection;
import java.util.HashSet;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.church.barnabas.entity.UserTable;


public class CustomUserDetail extends UserTable implements UserDetails {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserTable users;
    private String role;

    public CustomUserDetail() {
        super();
        // TODO Auto-generated constructor stub
    }

    public CustomUserDetail(UserTable users) {
        super();
        this.users = users;
    }

    public CustomUserDetail(UserTable users, String role) {
        super();
        this.users = users;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authority = new HashSet<>(1);
        authority.add(new SimpleGrantedAuthority(role));
        return authority;
    }

    @Override
    public String getPassword() {
        // TODO Auto-generated method stub
        return users.getOtp();
    }

    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        return users.getContactNo();
    }

    @Override
    public Integer getId() {

        return users.getId();
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return true;
    }

}
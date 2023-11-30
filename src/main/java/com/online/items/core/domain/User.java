/**
 * © Panov Vitaly 2023 - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Panov Vitaly <vetalpanov@gmail.com>, September 2022
 */

package com.online.items.core.domain;

import com.online.items.core.web.model.RoleModel;
import com.online.items.core.web.model.UserModel;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Document( collection = "user")
public class User extends AbstractDocument implements UserDetails {
    @Field( name = "email")
    @Indexed( unique = true )
    private EmailAddress emailAddress;

    private String       password;

    private Date creationDate;

    @DBRef
    private Set<Role> roles;

    private boolean disabled;

    private String avatar;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> roles = new HashSet<>();
        getRoles().forEach((role) -> {
            roles.add(new SimpleGrantedAuthority(role.getName()));
        });

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);

        return grantedAuthorities;
    }

    @Override
    public String getUsername() {
        return emailAddress.toString();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public User() {
    }

    public User( UserModel model ){
        this.id           = model.getId();
        this.emailAddress = new EmailAddress( model.getEmail() );
        this.avatar       = model.getAvatar();

        Set<RoleModel> roles = model.getRoles();
        this.roles = new HashSet<>(roles.size());
        roles.stream().forEach(r -> this.roles.add( new Role(r) ));
    }

    public User(EmailAddress emailAddress) {
        this.emailAddress = emailAddress;
        this.creationDate = new Date();
        this.roles = new HashSet<>();
        this.disabled = true;
    }

    public User(EmailAddress emailAddress, String pass) {
        this(emailAddress, pass, new HashSet<>());
    }

    public User(EmailAddress emailAddress, String password, Set<Role> roles) {
        this.emailAddress = emailAddress;
        this.password = password;
        this.creationDate = new Date();
        this.roles = roles;
        this.disabled = true;
    }

    public EmailAddress getEmailAddress() {
        return emailAddress;
    }

    public String getPassword() {
        return this.password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public void setEmailAddress(EmailAddress email) {
        this.emailAddress = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean addRole( Role role ){
        if( this.roles != null ) this.roles = new HashSet<>();

        return this.roles.add( role );
    }
}

/**
 * © Panov Vitaly 2023 - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Panov Vitaly <vetalpanov@gmail.com>, September 2022
 */

package com.online.items.core.domain;

import com.online.items.core.dto.RoleDto;
import com.online.items.core.dto.UserDto;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;

@Document( collection = "user")
public class User extends AbstractDocument {
    @Field( name = "email")
    @Indexed( unique = true )
    private EmailAddress emailAddress;

    private String       password;

    private Date creationDate;

    @DBRef
    private Set<Role> roles;

    private boolean disabled;

    private String avatar;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> roles = new HashSet<>();
        getRoles().forEach((role) -> {
            roles.add(new SimpleGrantedAuthority("ROLE_"+role.getName()));
        });

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);

        return grantedAuthorities;
    }

    public User() {
    }

    public User fromDto( UserDto dto ) {
        this.id           = dto.getId();
        this.emailAddress = new EmailAddress( dto.getEmail() );
        this.avatar       = dto.getAvatar();

        Set<RoleDto> roles = dto.getRoles();
        this.roles = new HashSet<>(roles.size());
        roles.stream().forEach(r -> this.roles.add( new Role(r) ));

        return this;
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

    public User setEmailAddress(EmailAddress email) {
        this.emailAddress = email;
        return this;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public User setRoles(Set<Role> roles) {
        this.roles = roles;
        return this;
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

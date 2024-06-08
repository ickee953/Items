package com.online.items.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.online.items.core.domain.Role;
import com.online.items.core.domain.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Email;
import org.springframework.validation.annotation.Validated;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class UserDto {

    private String id;

    @NotEmpty(message = "is required")
    @JsonProperty("email")
    @Pattern(regexp = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", message = "Email is not valid")
    private String email;

    private Date creationDate;

    private Set<RoleDto> roles;

    private String avatar;

    public UserDto() {
    }

    public UserDto(User user) {
        this.id     = user.getId();
        this.email  = user.getEmailAddress().toString();
        this.avatar = user.getAvatar();
        Set<Role> roles = user.getRoles();
        if( roles != null ){
            this.roles  = new HashSet<>(roles.size());
            roles.forEach(r->this.roles.add( new RoleDto(r) ) );
        }
    }

    public UserDto(String id, String email, Date creationDate, Set<RoleDto> roles, String avatar) {
        this.id = id;
        this.email = email;
        this.creationDate = creationDate;
        this.roles = roles;
        this.avatar = avatar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Set<RoleDto> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleDto> roles) {
        this.roles = roles;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}

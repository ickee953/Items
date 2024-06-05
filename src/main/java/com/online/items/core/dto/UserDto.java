package com.online.items.core.dto;

import java.util.Date;
import java.util.Set;

public class UserDto {

    private String id;

    private String email;

    private Date creationDate;

    private Set<RoleDto> roles;

    private String avatar;

    public UserDto() {
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

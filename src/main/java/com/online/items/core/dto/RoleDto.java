package com.online.items.core.dto;

import com.online.items.core.domain.Role;

public class RoleDto {
    private String id;
    private String name;

    public RoleDto() {
    }

    public RoleDto( Role role ) {
        this.id     = role.getId();
        this.name   = role.getName();
    }

    public RoleDto(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

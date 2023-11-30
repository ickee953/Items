/**
 * © Panov Vitaly 2023 - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Panov Vitaly <vetalpanov@gmail.com>, September 2022
 */

package com.online.items.core.web.model;

import com.online.items.core.domain.Role;

public class RoleModel extends AbstractModel {

    private String name;

    public RoleModel() {
    }

    public RoleModel( Role role ) {
        this.id     = role.getId();
        this.name   = role.getName();
    }

    public RoleModel(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

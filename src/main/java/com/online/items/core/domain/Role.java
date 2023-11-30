/**
 * © Panov Vitaly 2023 - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Panov Vitaly <vetalpanov@gmail.com>, September 2022
 */

package com.online.items.core.domain;

import com.online.items.core.web.model.RoleModel;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document( collection = "role")
public class Role extends AbstractDocument {
    public final static String ROLE_USER            = "ROLE_USER";
    public final static String ROLE_ADMINISTRATOR   = "ROLE_ADMINISTRATOR";

    @Indexed(unique = true)
    private String name;

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    public Role( RoleModel model ) {
        this.id     = model.getId();
        this.name   = model.getName();
    }

    public String getName() {
        return name;
    }

}

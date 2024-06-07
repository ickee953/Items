/**
 * © Panov Vitaly 2023 - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Panov Vitaly <vetalpanov@gmail.com>, September 2022
 */

package com.online.items.core.domain;

import com.online.items.core.dto.RoleDto;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document( collection = "role")
public class Role extends AbstractDocument {
    public final static String ROLE_USER            = "USER";
    public final static String ROLE_ADMINISTRATOR   = "ADMINISTRATOR";

    @Indexed(unique = true)
    private String name;

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    public Role( RoleDto dto ) {
        this.id     = dto.getId();
        this.name   = dto.getName();
    }

    public String getName() {
        return name;
    }

}

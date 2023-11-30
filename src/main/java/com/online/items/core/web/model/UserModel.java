/**
 * © Panov Vitaly 2023 - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Panov Vitaly <vetalpanov@gmail.com>, September 2022
 */

package com.online.items.core.web.model;

import com.online.items.core.domain.Role;
import com.online.items.core.domain.User;

import java.util.HashSet;
import java.util.Set;

public class UserModel extends AbstractModel {

    private String email;

    private Set<RoleModel> roles;

    private String avatar;

    public UserModel() {
    }

    public UserModel(String email) {
        this.email = email;
    }

    public UserModel(User user) {
        this.id     = user.getId();
        this.email  = user.getEmailAddress().toString();
        this.avatar = user.getAvatar();
        Set<Role> roles = user.getRoles();
        if( roles != null ){
            this.roles  = new HashSet<>(roles.size());
            roles.stream().forEach( r->this.roles.add( new RoleModel(r) ) );
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<RoleModel> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleModel> roles) {
        this.roles = roles;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

}

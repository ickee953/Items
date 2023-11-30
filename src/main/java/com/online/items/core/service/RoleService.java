/**
 * © Panov Vitaly 2023 - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Panov Vitaly <vetalpanov@gmail.com>, September 2022
 */

package com.online.items.core.service;

import com.online.items.core.domain.Role;
import com.online.items.core.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class RoleService {
    private static Logger LOGGER = LoggerFactory.getLogger( RoleService.class );

    @Autowired
    private RoleRepository repository;

    public Role getByName( String name ){
        return repository.findByName( name );
    }

    public Set<Role> getAll(){
        return new HashSet<>(repository.findAll());
    }
}

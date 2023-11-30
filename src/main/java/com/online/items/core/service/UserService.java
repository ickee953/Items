/**
 * © Panov Vitaly 2023 - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Panov Vitaly <vetalpanov@gmail.com>, September 2022
 */

package com.online.items.core.service;

import com.online.items.core.domain.EmailAddress;
import com.online.items.core.domain.User;
import com.online.items.core.repository.UserRepository;
import com.online.items.core.web.exception.UnknownIdentifierException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private static Logger LOGGER = LoggerFactory.getLogger( UserService.class );

    @Autowired
    private UserRepository repository;

    public User getByEmail(EmailAddress email ){

        Optional<User> o = repository.findByEmailAddress( email );

        return o.isPresent() ? o.get() : null;

    }

    public User update(User user) {

        if( user == null ) {
            String msg = "Updated user cannot be null!";
            LOGGER.error( msg );
            throw new NullPointerException( msg );
        }
        Optional<User> o = repository.findById( user.getId() );
        if( o.isPresent() ){
            User existedUser = o.get();
            user.setPassword( existedUser.getPassword() );
            user.setCreationDate( existedUser.getCreationDate() );
        }

        return repository.save( user );
    }

    public List<User> all(){
        return repository.findAll();
    }

    public boolean remove(String id) throws UnknownIdentifierException {

        Optional<User> item = repository.findById( id );

        if( !item.isPresent() ) throw new UnknownIdentifierException( id );

        repository.delete( item.get() );

        return true;
    }

    public User getById(String id) throws UnknownIdentifierException {
        Optional<User> o = repository.findById( id );
        if( !o.isPresent() ) throw new UnknownIdentifierException( id );
        return o.get();
    }
}

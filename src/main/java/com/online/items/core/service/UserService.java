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
import com.online.items.core.utils.exception.UnknownIdentifierException;
import com.online.items.core.utils.exception.UserAlreadyExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private static Logger LOGGER = LoggerFactory.getLogger( UserService.class );

    private final UserRepository repository;

    @Autowired
    public UserService( UserRepository repository ) {
        this.repository = repository;
    }

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

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode( user.getPassword() );

        if( user.getId() == null )
            return repository.save( new User()
                    .setPassword(encodedPassword)
                    .setEmailAddress(user.getEmailAddress())
                    .setRoles(user.getRoles())
            );

        Optional<User> existingUser = repository.findById(user.getId());
        if(  existingUser.isPresent() ) {

            existingUser.get().setPassword(encodedPassword);
            existingUser.get().setEmailAddress(user.getEmailAddress());
            existingUser.get().setRoles(user.getRoles());

            return repository.save(existingUser.get());
        }

        return null;

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

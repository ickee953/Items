/**
 * © Panov Vitaly 2023 - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Panov Vitaly <vetalpanov@gmail.com>, September 2022
 */

package com.online.items.core.security;

import com.online.items.core.domain.EmailAddress;
import com.online.items.core.domain.User;
import com.online.items.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomMongoSecurityService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomMongoSecurityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        User user = userRepository.findByEmailAddress( new EmailAddress(login) ).orElseThrow(() ->
            new UsernameNotFoundException("username not found"));

        return SecurityUser.fromUser(user);

    }
}
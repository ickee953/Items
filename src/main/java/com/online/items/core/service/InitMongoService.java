/**
 * © Panov Vitaly 2023 - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Panov Vitaly <vetalpanov@gmail.com>, November 2021
 */

package com.online.items.core.service;

import com.mongodb.client.MongoCollection;
import com.online.items.core.domain.EmailAddress;
import com.online.items.core.domain.Role;
import com.online.items.core.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class InitMongoService {
    private static Logger LOGGER = LoggerFactory.getLogger(InitMongoService.class);

    public final String COLLECTION_NAME_ROLE = "role";
    public final String COLLECTION_NAME_USER = "user";

    private final MongoTemplate mongo;

    @Autowired
    public InitMongoService(MongoTemplate mongo) {
        this.mongo = mongo;
    }

    private void initDatabase(){
        MongoCollection collectionRole = mongo.getCollection( COLLECTION_NAME_ROLE );
        //if collection roles is empty ( if app not installed )
        if( collectionRole.countDocuments() == 0 ) {
            mongo.insert( new Role( Role.ROLE_ADMINISTRATOR ), COLLECTION_NAME_ROLE);
            mongo.insert(new Role( Role.ROLE_USER ), COLLECTION_NAME_ROLE);
        }

        MongoCollection collectionUser = mongo.getCollection( COLLECTION_NAME_USER );
        if( collectionUser.countDocuments() == 0 ){
            String password = "123";

            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode( password );

            User userAdmin = new User( new EmailAddress("test@domain.com"), encodedPassword);
            //find role administrator
            Role roleAdmin = mongo.findOne( new Query().addCriteria(
                    Criteria.where("name").is(Role.ROLE_ADMINISTRATOR)), Role.class
            );

            if( roleAdmin == null ){
                LOGGER.info("Can't find admin role in database.");

                return;
            }
            userAdmin.addRole( roleAdmin );

            mongo.insert( userAdmin, COLLECTION_NAME_USER );
        }
    }

    public void init(){
        initDatabase();
    }
}
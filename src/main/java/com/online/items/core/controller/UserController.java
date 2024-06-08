/**
 * © Panov Vitaly 2023 - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Panov Vitaly <vetalpanov@gmail.com>, September 2022
 */

package com.online.items.core.controller;

import com.online.items.core.domain.*;
import com.online.items.core.dto.RoleDto;
import com.online.items.core.dto.UserDto;
import com.online.items.core.utils.BindingError;
import com.online.items.core.utils.exception.ItemCreationException;
import com.online.items.core.utils.exception.UnknownIdentifierException;
import com.online.items.core.service.*;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.*;

@Controller
@RequestMapping("/users")
public class UserController {

    private static Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService service;

    @Autowired
    private FileService fileService;

    @Autowired
    private RoleService roleService;

    @RequestMapping
    public ModelAndView get(){
        List<User> users = service.all();
        ModelAndView model = new ModelAndView("personal/users");
        //get all available authorities for user edit form
        Set<Role> roles = roleService.getAll();
        model.addObject("roles", roles.stream().map(role -> {
            return new RoleDto(role.getId(), role.getName());
        }).collect(Collectors.toList()));
        model.addObject("users", users.stream().map(user->
                new UserDto(
                        user.getId(),
                        user.getEmailAddress().toString(),
                        user.getCreationDate(),
                        user.getRoles() == null ? null : user.getRoles().stream().map(role -> {
                            return new RoleDto(role.getId(), role.getName());
                        }).collect(Collectors.toSet()),
                        user.getAvatar()
                )
        ).collect(Collectors.toList()));

        return model;
    }

    @PostMapping(
            value = "/update"
    )
    public ResponseEntity<?> update(
            @RequestPart( value = "user", required = true ) @Valid UserDto item, BindingResult bindingResult,
            @RequestPart( value = "avatar", required = false ) MultipartFile avatar,
            @RequestPart( value = "password", required = false ) String password
    ) throws ItemCreationException {
        if( bindingResult.hasErrors() ){

            List<FieldError> errors = bindingResult.getFieldErrors();

            List<BindingError> validFormErrList = new ArrayList<>(errors.size());

            errors.forEach( e ->
                    validFormErrList.add( new BindingError( e.getField(), e.getDefaultMessage() ) )
            );

            return new ResponseEntity<>( validFormErrList, HttpStatus.BAD_REQUEST );

        } else {
            User updatedUser = null;
            try {
                updatedUser = service.update(
                        new User().fromDto(item).setPassword(password)
                );
            } catch( DuplicateKeyException e ){
                e.printStackTrace();
                String msg = new StringBuilder("User: ")
                        .append( item.getEmail() ).append(" already exist!").toString();

                List<BindingError> validFormErrList = new ArrayList<>(1);
                validFormErrList.add( new BindingError( "email", msg));

                return new ResponseEntity<>( validFormErrList, HttpStatus.BAD_REQUEST );
            }
            if( avatar != null && !avatar.isEmpty() ){
                try {
                    String uploadedFilename = fileService.uploadFile(
                            avatar, item.getId() + avatar.getOriginalFilename()
                    );

                    item.setAvatar( uploadedFilename == null ? "" : "/picture/" + uploadedFilename );
                    service.update(
                            new User().fromDto(item).setPassword(password)
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return new ResponseEntity<>( new UserDto(updatedUser), HttpStatus.CREATED );
        }
    }

    @RequestMapping(
            value = "/create",
            method = RequestMethod.POST,
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    public ResponseEntity<?> create(
            @RequestBody( required = true ) String email
    ){
        User         user         = null;
        EmailAddress emailAddress = null;

        String responseMsg = null;

        try{
            emailAddress = new EmailAddress( email );
        } catch ( IllegalArgumentException e ){
            responseMsg = new StringBuilder("Invalid email address: ").append(email).toString();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(TEXT_PLAIN);

            return new ResponseEntity<>( responseMsg, headers, HttpStatus.BAD_REQUEST );
        }

        //user creation here
        try {
            user = service.update( new User(emailAddress) );
        } catch ( DuplicateKeyException e1 ){
            responseMsg = new StringBuilder("Already exist: ").append(email).toString();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(TEXT_PLAIN);

            return new ResponseEntity<>(responseMsg, headers, HttpStatus.ALREADY_REPORTED );
        }

        //if any error
        if( user == null ) {
            responseMsg = new StringBuilder("Error while create user: ").append(email).toString();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(TEXT_PLAIN);

            return new ResponseEntity<>( responseMsg, headers, HttpStatus.INTERNAL_SERVER_ERROR );
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);

        return new ResponseEntity<>( user, headers, HttpStatus.CREATED );
    }

    /**
     * Remove item by id
     *
     * @return id of deleted item
     */
    @RequestMapping(
            value = "/remove/{id}",
            method = RequestMethod.DELETE
    )
    public @ResponseBody String deleteItem(
            @PathVariable( value = "id" ) String id
    ) throws UnknownIdentifierException {

        if( service.remove( id ) ) return id;

        return null;
    }
}

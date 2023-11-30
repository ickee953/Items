/**
 * © Panov Vitaly 2023 - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Panov Vitaly <vetalpanov@gmail.com>, September 2022
 */

package com.online.items.core.web.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.reactive.result.view.CsrfRequestDataValueProcessor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@RestController
public class LoginWebHandler {

    @Autowired
    public CookieCsrfTokenRepository csrfTokenRepository;

    @RequestMapping("/login")
    public ModelAndView loginForm(
            HttpServletRequest request,
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout
    ){
        ModelAndView view = new ModelAndView("login");

        CsrfToken csrfToken = csrfTokenRepository.loadToken( request );

        if (csrfToken != null) {
            view.addObject( CsrfRequestDataValueProcessor.DEFAULT_CSRF_ATTR_NAME, csrfToken );
        }

        if (error != null) {
            view.addObject("error", "Invalid username and password!");
        }

        if (logout != null) {
            view.addObject("msg", "You've been logged out successfully.");
        }

        return view;
    }

}

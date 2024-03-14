/**
 * © Panov Vitaly 2023 - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Panov Vitaly <vetalpanov@gmail.com>, September 2022
 */

package com.online.items.core.web.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping
public class MainWebHandler {
    private static Logger LOGGER = LoggerFactory.getLogger(MainWebHandler.class);

    @GetMapping
    public ModelAndView get(){
        return new ModelAndView("redirect:/items");
    }

    @RequestMapping( value = "/header", method = RequestMethod.GET)
    public ModelAndView getHeader(
            @RequestParam( value = "menu_selected_num" ) Integer num
    ){
        ModelAndView view = new ModelAndView("personal/header");
        view.addObject("menu_selected_num", num);

        return view;
    }

    @RequestMapping("/footer")
    public ModelAndView getFooter(){
        ModelAndView view = new ModelAndView("personal/footer");

        return view;
    }
}

/**
 * © Panov Vitaly 2023 - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Panov Vitaly <vetalpanov@gmail.com>, November 2021
 */

package com.online.items.core.web.exception;

public class ItemCreationException extends Exception {

    /**
     * Constructor for any item creation exception
     *
     * @param className name of class creation of throws exception
     */
    public ItemCreationException(String className) {
        super( className );
    }

    @Override
    public String getMessage() {
        return "Failed while creation new Item: " + getMessage();
    }
}

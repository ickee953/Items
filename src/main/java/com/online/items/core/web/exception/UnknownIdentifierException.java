/**
 * © Panov Vitaly 2023 - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Panov Vitaly <vetalpanov@gmail.com>, September 2022
 */

package com.online.items.core.web.exception;

public class UnknownIdentifierException extends Exception {
    protected String identifier;

    public static UnknownIdentifierException createWith(String identifier) {
        return new UnknownIdentifierException(identifier);
    }

    public UnknownIdentifierException(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public String getMessage() {
        return "Identifier '" + identifier + "' not found";
    }

}

/**
 * © Panov Vitaly 2023 - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Panov Vitaly <vetalpanov@gmail.com>, September 2022
 */

package com.online.items.core.domain;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.StringUtils;
import java.util.regex.Pattern;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;


/**
 * Value object to represent email addresses.
 *
 */

@Document
public final class EmailAddress {
    private static final String EMAIL_REGEX = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final Pattern PATTERN = Pattern.compile(EMAIL_REGEX);

    @Field("email")
    private final String value;

    /**
     * Creates a new {@link EmailAddress} from the given {@link String} representation.
     *
     * @param value must not be {@literal null} or empty.
     */
    public EmailAddress(String value) {
        Assert.isTrue(isValid(value), value+": invalid email address!");
        this.value = value;
    }

    /**
     * Returns whether the given {@link String} is a valid {@link EmailAddress} which means you can safely instantiate the
     * class.
     *
     * @param candidate
     * @return
     */
    public static boolean isValid(String candidate) {
        return candidate == null ? false : PATTERN.matcher(candidate).matches();
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return value;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (!(obj instanceof EmailAddress that)) {
            return false;
        }

        return this.value.equals(that.value);
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Component
    static class EmailAddressToStringConverter implements Converter<EmailAddress, String> {

        /*
         * (non-Javadoc)
         * @see org.springframework.core.convert.converter.Converter#convert(java.lang.Object)
         */
        @Override
        public String convert(EmailAddress source) {
            return source == null ? null : source.value;
        }
    }

    @Component
    static class StringToEmailAddressConverter implements Converter<String, EmailAddress> {

        /*
         * (non-Javadoc)
         * @see org.springframework.core.convert.converter.Converter#convert(java.lang.Object)
         */
        public EmailAddress convert(String source) {
            return StringUtils.hasText(source) ? new EmailAddress(source) : null;
        }
    }
}

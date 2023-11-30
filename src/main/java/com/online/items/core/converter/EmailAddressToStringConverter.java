/**
 * © Panov Vitaly 2023 - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Panov Vitaly <vetalpanov@gmail.com>, September 2022
 */

package com.online.items.core.converter;

import com.online.items.core.domain.EmailAddress;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class EmailAddressToStringConverter implements Converter<EmailAddress, String> {
    @Override
    public String convert(EmailAddress source) {
        return source == null ? null : source.toString();
    }
}

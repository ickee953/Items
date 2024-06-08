/**
 * © Panov Vitaly 2023 - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Panov Vitaly <vetalpanov@gmail.com>, September 2022
 */

package com.online.items.core.domain.converter;

import com.online.items.core.domain.EmailAddress;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class StringToEmailAddressConverter implements Converter<String, EmailAddress> {
    @Override
    public EmailAddress convert(String source) {
        return StringUtils.hasText(source) ? new EmailAddress(source) : null;
    }
}

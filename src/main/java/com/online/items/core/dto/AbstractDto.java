/**
 * © Panov Vitaly 2023 - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Panov Vitaly <vetalpanov@gmail.com>, November 2021
 */

package com.online.items.core.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.online.items.core.domain.AbstractDocument;

@Deprecated
public abstract class AbstractDto implements Comparable<AbstractDto> {

    protected String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        try{
            String json = mapper.writeValueAsString( this );

            return json;
        } catch ( JsonProcessingException e ){
            e.printStackTrace();
        }

        return super.toString();
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (this.id == null || obj == null || !(this.getClass().equals(obj.getClass()))) {
            return false;
        }

        AbstractDocument that = (AbstractDocument) obj;

        return this.id.equals(that.getId());
    }

    @Override
    public int compareTo( AbstractDto o) {
        return this.id.compareTo( o.getId() );
    }
}

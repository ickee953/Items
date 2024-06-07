/**
 * © Panov Vitaly 2023 - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Panov Vitaly <vetalpanov@gmail.com>, September 2022
 */

package com.online.items.core.domain;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

//@Document
public class Rating {

    @Min(0) @Max(5)
    private Integer oneStarCount;

    @Min(0) @Max(5)
    private Integer twoStarCount;

    @Min(0) @Max(5)
    private Integer threeStarCount;

    @Min(0) @Max(5)
    private Integer fourStarCount;

    @Min(0) @Max(5)
    private Integer fiveStarCount;

    public Rating(Integer oneStarCount, Integer twoStarCount, Integer threeStarCount, Integer fourStarCount, Integer fiveStarCount) {
        this.oneStarCount = oneStarCount;
        this.twoStarCount = twoStarCount;
        this.threeStarCount = threeStarCount;
        this.fourStarCount = fourStarCount;
        this.fiveStarCount = fiveStarCount;
    }

    public Integer getOneStarCount() {
        return oneStarCount;
    }

    public Integer getTwoStarCount() {
        return twoStarCount;
    }

    public Integer getThreeStarCount() {
        return threeStarCount;
    }

    public Integer getFourStarCount() {
        return fourStarCount;
    }

    public Integer getFiveStarCount() {
        return fiveStarCount;
    }

    public void rateUp( int stars ) throws IndexOutOfBoundsException {
        //check input value
        if(stars < 1 || stars > 5) throw new IndexOutOfBoundsException("Rating must be [0..5]");

        switch(stars){
            case 1: oneStarCount++;
            case 2: twoStarCount++;
            case 3: threeStarCount++;
            case 4: fourStarCount++;
            case 5: fiveStarCount++;
        }
    }
}

/*
 * Name:   QuoteHelper
 * Author: Bhaskar S
 * Date:   06/xx/2021
 * Blog:   https://www.polarsparc.com
 */

package com.polarsparc.fifth.util;

import com.polarsparc.fifth.model.Quote;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class QuoteHelper {
    private final static Random _rand = new Random();

    static {
        _rand.setSeed(System.currentTimeMillis());
    }

    public static Quote generateQuote(String supplier, String product, double price) {
        int factor = _rand.nextInt(10);
        if (factor % 2 == 0) {
            return new Quote(supplier, product, price + (price * factor/100.0));
        }
        return new Quote(supplier, product, price - (price * factor/100.0));
    }
}

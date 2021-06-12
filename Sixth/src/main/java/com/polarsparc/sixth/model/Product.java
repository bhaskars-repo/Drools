/*
 * Name:   Product
 * Author: Bhaskar S
 * Date:   06/12/2021
 * Blog:   https://www.polarsparc.com
 */

package com.polarsparc.sixth.model;

import lombok.*;

@RequiredArgsConstructor
@Getter
@ToString
public class Product {
    private final String id;
    private final String name;

    private long counter = 0;

    public void incCounter() {
        counter++;
    }
}

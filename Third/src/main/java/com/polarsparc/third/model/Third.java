/*
 * Name:   Third
 * Author: Bhaskar S
 * Date:   06/11/2021
 * Blog:   https://www.polarsparc.com
 */

package com.polarsparc.third.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class Third {
    private final String supplier;
    private final String product;
    private final double price;
}

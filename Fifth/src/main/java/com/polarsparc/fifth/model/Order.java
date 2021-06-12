/*
 * Name:   Order
 * Author: Bhaskar S
 * Date:   06/12/2021
 * Blog:   https://www.polarsparc.com
 */

package com.polarsparc.fifth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class Order {
    private final String supplier;
    private final String product;
    private final double price;
}

/*
 * Name:   Quote
 * Author: Bhaskar S
 * Date:   07/17/2021
 * Blog:   https://www.polarsparc.com
 */

package com.polarsparc.seventh.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Quote {
    private int day;
    private String supplier;
    private String product;
    private double price;
}

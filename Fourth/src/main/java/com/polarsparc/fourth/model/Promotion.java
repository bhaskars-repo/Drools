/*
 * Name:   Promotion
 * Author: Bhaskar S
 * Date:   06/11/2021
 * Blog:   https://www.polarsparc.com
 */

package com.polarsparc.fourth.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Promotion {
    private double computed = 0.0;
    private double discount = 0.0;
    private Supplier supplier;
}

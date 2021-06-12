/*
 * Name:   Second
 * Author: Bhaskar S
 * Date:   06/05/2021
 * Blog:   https://www.polarsparc.com
 */

package com.polarsparc.second.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Second {
    private String supplier;
    private String product;
    private double price;
}

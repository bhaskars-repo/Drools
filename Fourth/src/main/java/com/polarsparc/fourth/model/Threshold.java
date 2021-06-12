/*
 * Name:   Threshold
 * Author: Bhaskar S
 * Date:   06/11/2021
 * Blog:   https://www.polarsparc.com
 */

package com.polarsparc.fourth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class Threshold {
    private String product;
    private double threshold;
}

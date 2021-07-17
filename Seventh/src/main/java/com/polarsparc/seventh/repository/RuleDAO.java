/*
 * Name:   RuleDAO
 * Author: Bhaskar S
 * Date:   07/17/2021
 * Blog:   https://www.polarsparc.com
 */

package com.polarsparc.seventh.repository;

import com.polarsparc.seventh.model.Rule;

import java.util.List;

public interface RuleDAO {
    List<Rule> findRules(List<String> ids);
}

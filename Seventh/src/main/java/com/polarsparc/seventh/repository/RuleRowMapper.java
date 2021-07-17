/*
 * Name:   RuleRowMapper
 * Author: Bhaskar S
 * Date:   07/17/2021
 * Blog:   https://www.polarsparc.com
 */

package com.polarsparc.seventh.repository;

import com.polarsparc.seventh.model.Rule;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RuleRowMapper implements RowMapper<Rule> {
    @Override
    public Rule mapRow(ResultSet rs, int no) throws SQLException {
        Rule rule = new Rule();
        rule.setRuleId(rs.getInt("RULE_ID"));
        rule.setRuleTxt(rs.getString("RULE_TXT"));
        return rule;
    }
}

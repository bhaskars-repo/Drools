/*
 * Name:   RuleRepository
 * Author: Bhaskar S
 * Date:   07/17/2021
 * Blog:   https://www.polarsparc.com
 */

package com.polarsparc.seventh.repository;

import com.polarsparc.seventh.model.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RuleRepository implements RuleDAO {
    final String QUERY_BY_IDS = "SELECT rule_id, rule_txt FROM rules_tbl WHERE rule_id IN (%s) ORDER BY rule_id";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Rule> findRules(List<String> ids) {
        return jdbcTemplate.query(String.format(QUERY_BY_IDS, String.join(",", ids)),
                new RuleRowMapper());
    }
}

/*
 * Name:   SeventhApplication
 * Author: Bhaskar S
 * Date:   07/17/2021
 * Blog:   https://www.polarsparc.com
 */

package com.polarsparc.seventh;

import com.polarsparc.seventh.model.Quote;
import com.polarsparc.seventh.model.Rule;
import com.polarsparc.seventh.repository.RuleDAO;
import com.polarsparc.seventh.repository.RuleRepository;
import com.polarsparc.seventh.util.DroolsUtil;
import lombok.extern.slf4j.Slf4j;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@Slf4j
public class SeventhApplication implements ApplicationRunner {
    private RuleDAO repository;

    @Autowired
    public void setRepository(RuleRepository repository) {
        this.repository = repository;
    }

    public static void main(String[] args) {
        SpringApplication.run(SeventhApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        // Case - 1

        List<Rule> rules = repository.findRules(Arrays.asList("1", "2", "3"));

        KieContainer container = DroolsUtil.prepareKieContainer("1.0", rules);

        log.info("Part 1 - ReleaseId: {}", container.getReleaseId());

        KieSession session = container.newKieSession();
        session.setGlobal("log", log);
        session.insert(new Quote(0,"S1", "P1", 9.99));
        session.insert(new Quote(1,"S1", "P1", 10.99));
        session.fireAllRules();
        session.dispose();

        log.info("Part 1 --- Done !!!");

        // Case - 2

        rules = repository.findRules(Arrays.asList("1", "2", "4"));

        container = DroolsUtil.prepareKieContainer("1.1", rules);

        log.info("Part 2 - ReleaseId: {}", container.getReleaseId());

        session = container.newKieSession();
        session.setGlobal("log", log);
        session.insert(new Quote(0,"S1", "P1", 9.99));
        session.insert(new Quote(1,"S1", "P1", 10.99));
        session.fireAllRules();
        session.dispose();

        log.info("Part 2 --- Done !!!");
    }
}

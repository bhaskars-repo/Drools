/*
 * Name:   FirstApplication
 * Author: Bhaskar S
 * Date:   06/05/2021
 * Blog:   https://www.polarsparc.com
 */

package com.polarsparc.first;

import com.polarsparc.first.model.First;
import lombok.extern.slf4j.Slf4j;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class FirstApplication implements ApplicationRunner {
    private KieContainer container;

    @Autowired
    public void setKieContainer(KieContainer container) {
        this.container = container;
    }

    public static void main(String[] args) {
        SpringApplication.run(FirstApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        log.info("ReleaseId: {}", container.getReleaseId());
        log.info("KieBases: {}", container.getKieBaseNames());
        container.getKieBaseNames().forEach(name ->
                log.info("KieBase: {}, KieSessions: {}", name, container.getKieSessionNamesInKieBase(name)));

        // Test 1
        KieSession ks1 = container.newKieSession();
        ks1.setGlobal("log", log);
        First f1 = new First(12);
        ks1.insert(f1);
        ks1.fireAllRules();
        ks1.dispose();
        log.info("[1] First: f1 = {}", f1);

        // Test 2
        KieSession ks2 = container.newKieSession();
        ks2.setGlobal("log", log);
        First f2 = new First(23);
        ks2.insert(f2);
        ks2.fireAllRules();
        ks2.dispose();
        log.info("[2] First: f2 = {}", f2);

        // Test 3
        KieSession ks3 = container.newKieSession();
        ks3.setGlobal("log", log);
        First f3 = new First(36);
        ks3.insert(f3);
        ks3.fireAllRules();
        ks3.dispose();
        log.info("[3] First: f3 = {}", f3);

        log.info("Done !!!");
    }
}

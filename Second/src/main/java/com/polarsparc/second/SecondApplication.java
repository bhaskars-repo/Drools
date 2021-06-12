/*
 * Name:   SecondApplication
 * Author: Bhaskar S
 * Date:   06/05/2021
 * Blog:   https://www.polarsparc.com
 */

package com.polarsparc.second;

import com.polarsparc.second.model.Second;
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
public class SecondApplication implements ApplicationRunner {
    private KieContainer container;

    @Autowired
    public void setKieContainer(KieContainer container) {
        this.container = container;
    }

    public static void main(String[] args) {
        SpringApplication.run(SecondApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        log.info("ReleaseId: {}", container.getReleaseId());
        log.info("KieBases: {}", container.getKieBaseNames());
        container.getKieBaseNames().forEach(name ->
                log.info("KieBase: {}, KieSessions: {}", name, container.getKieSessionNamesInKieBase(name)));

        KieSession ks = container.newKieSession();
        ks.setGlobal("log", log);
        Second s1 = new Second("S1", "P1", 25.49);
        Second s2 = new Second("S2", "P1", 24.99);
        Second s3 = new Second("S3", "P2", 18.78);
        ks.insert(s1);
        ks.insert(s2);
        ks.insert(s3);
        ks.fireAllRules();
        ks.dispose();

        log.info("Done !!!");
    }
}

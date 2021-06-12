/*
 * Name:   ThirdApplication
 * Author: Bhaskar S
 * Date:   06/11/2021
 * Blog:   https://www.polarsparc.com
 */

package com.polarsparc.third;

import com.polarsparc.third.model.Third;
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
public class ThirdApplication implements ApplicationRunner {
    private KieContainer container;

    @Autowired
    public void setKieContainer(KieContainer container) {
        this.container = container;
    }

    public static void main(String[] args) {
        SpringApplication.run(ThirdApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        log.info("ReleaseId: {}", container.getReleaseId());
        log.info("KieBases: {}", container.getKieBaseNames());
        container.getKieBaseNames().forEach(name ->
                log.info("KieBase: {}, KieSessions: {}", name, container.getKieSessionNamesInKieBase(name)));

        Third t1 = new Third("S1", "P1", 19.99);
        Third t2 = new Third("S2", "P1", 19.79);

        // Default - third-r1-session
        KieSession ks1 = container.newKieSession();
        ks1.setGlobal("log", log);
        ks1.insert(t1);
        ks1.insert(t2);
        ks1.fireAllRules();
        ks1.dispose();

        log.info("ks1 - Done !!!");

        // Specific - third-r2-session
        KieSession ks2 = container.newKieSession("third-r2-session");
        ks2.setGlobal("log", log);
        ks2.insert(t1);
        ks2.insert(t2);
        ks2.fireAllRules();
        ks2.dispose();

        log.info("ks2 - Done !!!");
    }
}

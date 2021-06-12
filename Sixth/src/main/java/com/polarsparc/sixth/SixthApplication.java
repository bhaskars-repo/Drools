/*
 * Name:   SixthApplication
 * Author: Bhaskar S
 * Date:   06/12/2021
 * Blog:   https://www.polarsparc.com
 */

package com.polarsparc.sixth;

import com.polarsparc.sixth.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.kie.api.KieBase;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.CountDownLatch;

@SpringBootApplication
@Slf4j
public class SixthApplication implements ApplicationRunner {
    private ThreadPoolTaskExecutor executor;
    private KieContainer container;

    @Autowired
    public void setThreadPoolTaskExecutor(@Qualifier("sixthThreadPoolTaskExecutor") ThreadPoolTaskExecutor executor) {
        this.executor = executor;
    }

    @Autowired
    public void setKieContainer(KieContainer container) {
        this.container = container;
    }

    public static void main(String[] args) {
        SpringApplication.run(SixthApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws InterruptedException {
        final int COUNT = 3;

        KieBase kb = container.getKieBase();

        Product gp1 = new Product("GP1", "P1");
        Product gp2 = new Product("GP2", "P2");

        CountDownLatch latch = new CountDownLatch(COUNT);

        for (int i = 1; i <= COUNT; i++) {
            int finalI = i;
            executor.execute(() -> {
                Product lp1 = new Product("LP1-" + finalI, "P1");
                Product lp2 = new Product("LP2-" + finalI, "P2");

                KieSession ks = kb.newKieSession();
                ks.setGlobal("log", log);
                ks.insert(gp1);
                ks.insert(gp2);
                ks.insert(lp1);
                ks.insert(lp2);
                ks.fireAllRules();
                ks.dispose();

                log.info("{}: Product (lp1): {}", SixthApplication.class.getName(), lp1);
                log.info("{}: Product (lp2): {}", SixthApplication.class.getName(), lp2);

                latch.countDown();
            });
        }

        latch.await();

        log.info("{}: Product (gp1): {}", SixthApplication.class.getName(), gp1);
        log.info("{}: Product (gp2): {}", SixthApplication.class.getName(), gp2);

        executor.shutdown();
    }
}

/*
 * Name:   FourthApplication
 * Author: Bhaskar S
 * Date:   06/11/2021
 * Blog:   https://www.polarsparc.com
 */

package com.polarsparc.fourth;

import com.polarsparc.fourth.model.Inventory;
import com.polarsparc.fourth.model.Promotion;
import com.polarsparc.fourth.model.Supplier;
import com.polarsparc.fourth.model.Threshold;
import lombok.extern.slf4j.Slf4j;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
@Slf4j
public class FourthApplication implements ApplicationRunner {
    private KieContainer container;

    @Autowired
    public void setKieContainer(KieContainer container) {
        this.container = container;
    }

    public static void main(String[] args) {
        SpringApplication.run(FourthApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        // Test Case - 1
        {
            // Suppliers for Product P1
            Supplier s1 = new Supplier("S1", "P1", 18.99);
            Supplier s2 = new Supplier("S2", "P1", 18.59);
            Supplier s3 = new Supplier("S3", "P1", 18.79);

            // Current inventory
            Inventory in = new Inventory("P1", 18.99);

            // Price difference threshold
            Threshold th = new Threshold("P1", 0.05);

            // Promotion
            Promotion pm = new Promotion();

            KieSession ks = container.newKieSession();
            ks.setGlobal("log", log);
            ks.insert(Arrays.asList(s1, s2, s3));
            ks.insert(in);
            ks.insert(th);
            ks.insert(pm);
            ks.fireAllRules();
            ks.dispose();

            log.info("{}: [1] Supplier: {}, Product: {}, Discount: {}",
                    FourthApplication.class.getName(),
                    pm.getSupplier(),
                    pm.getSupplier().getProduct(),
                    String.format("%.3f", pm.getDiscount()));
        }

        // Test Case - 2
        {
            // Suppliers for Product P2
            Supplier s1 = new Supplier("S2", "P2", 18.99);
            Supplier s2 = new Supplier("S3", "P2", 17.49);
            Supplier s3 = new Supplier("S4", "P2", 16.99);

            // Current inventory
            Inventory in = new Inventory("P2", 19.99);

            // Price difference threshold
            Threshold th = new Threshold("P2", 0.10);

            // Promotion
            Promotion pm = new Promotion();

            KieSession ks = container.newKieSession();
            ks.setGlobal("log", log);
            ks.insert(Arrays.asList(s1, s2, s3));
            ks.insert(in);
            ks.insert(th);
            ks.insert(pm);
            ks.fireAllRules();
            ks.dispose();

            log.info("{}: [2] Supplier: {}, Product: {}, Discount: {}",
                    FourthApplication.class.getName(),
                    pm.getSupplier(),
                    pm.getSupplier().getProduct(),
                    String.format("%.3f", pm.getDiscount()));
        }
    }
}

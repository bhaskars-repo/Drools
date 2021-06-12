/*
 * Name:   FifthApplication
 * Author: Bhaskar S
 * Date:   06/12/2021
 * Blog:   https://www.polarsparc.com
 */

package com.polarsparc.fifth;

import com.polarsparc.fifth.model.Inventory;
import com.polarsparc.fifth.model.Order;
import com.polarsparc.fifth.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.kie.api.KieBase;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Optional;

@SpringBootApplication
@Slf4j
public class FifthApplication implements ApplicationRunner {
    private KieContainer container;

    @Autowired
    public void setKieContainer(KieContainer container) {
        this.container = container;
    }

    public static void main(String[] args) {
        SpringApplication.run(FifthApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        KieBase kb = container.getKieBase();

        // Test Case 1
        {
            // Product P1
            Product p1 = new Product("P1");

            // Current inventory of P1
            Inventory in = new Inventory("P1", 18.99);

            KieSession ks = kb.newKieSession();
            ks.setGlobal("log", log);
            ks.insert(p1);
            ks.insert(in);
            ks.fireAllRules();

            Optional<?> op = ks.getObjects().stream().filter(obj -> obj instanceof Order).findFirst();
            if (op.isPresent()) {
                Order order = (Order) op.get();
                log.info("{}: [1] Supplier: {}, Product: {}, Discount: {}",
                        FifthApplication.class.getName(),
                        order.getSupplier(),
                        order.getProduct(),
                        String.format("%.2f", order.getPrice()));
            }

            ks.dispose();
        }

        // Test Case 2
        {
            // Product P2
            Product p2 = new Product("P2");

            // Current inventory of P1
            Inventory in = new Inventory("P2", 21.99);

            KieSession ks = kb.newKieSession();
            ks.setGlobal("log", log);
            ks.insert(p2);
            ks.insert(in);
            ks.fireAllRules();

            Optional<?> op = ks.getObjects().stream().filter(obj -> obj instanceof Order).findFirst();
            if (op.isPresent()) {
                Order order = (Order) op.get();
                log.info("{}: [2] Supplier: {}, Product: {}, Discount: {}",
                        FifthApplication.class.getName(),
                        order.getSupplier(),
                        order.getProduct(),
                        String.format("%.2f", order.getPrice()));
            }

            ks.dispose();
        }
    }
}

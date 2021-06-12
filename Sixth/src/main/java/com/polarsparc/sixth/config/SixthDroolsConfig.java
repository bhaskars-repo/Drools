/*
 * Name:   SixthDroolsConfig
 * Author: Bhaskar S
 * Date:   06/12/2021
 * Blog:   https://www.polarsparc.com
 */

package com.polarsparc.sixth.config;

import org.kie.api.KieServices;
import org.kie.api.builder.*;
import org.kie.api.io.KieResources;
import org.kie.api.runtime.KieContainer;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class SixthDroolsConfig {
    private final static String SIXTH_DRL = "com/polarsparc/sixth/sixth.drl";

    @Bean
    public ThreadPoolTaskExecutor sixthThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(3);
        executor.setThreadNamePrefix("SixthThreadPoolTask-");
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.initialize();

        return executor;
    }

    @Bean
    public KieContainer sixthKieContainer() {
        KieServices services = KieServices.Factory.get();
        KieResources resources = services.getResources();

        KieFileSystem fileSystem = services.newKieFileSystem();
        fileSystem.write(resources.newClassPathResource(SIXTH_DRL));

        KieBuilder builder = services.newKieBuilder(fileSystem);
        Results results = builder.buildAll().getResults();
        if (results.hasMessages(Message.Level.ERROR)) {
            throw new BeanCreationException("Error building rules: " + results.getMessages());
        }

        KieModule module = builder.getKieModule();

        return services.newKieContainer(module.getReleaseId());
    }
}

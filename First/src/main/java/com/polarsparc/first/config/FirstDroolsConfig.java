/*
 * Name:   FirstDroolsConfig
 * Author: Bhaskar S
 * Date:   06/05/2021
 * Blog:   https://www.polarsparc.com
 */

package com.polarsparc.first.config;

import org.kie.api.KieServices;
import org.kie.api.builder.*;
import org.kie.api.io.KieResources;
import org.kie.api.runtime.KieContainer;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FirstDroolsConfig {
    private final static String FIRST_DRL = "com/polarsparc/first/first.drl";

    @Bean
    public KieContainer firstKieContainer() {
        KieServices services = KieServices.Factory.get();
        KieResources resources = services.getResources();

        KieFileSystem fileSystem = services.newKieFileSystem();
        fileSystem.write(resources.newClassPathResource(FIRST_DRL));

        KieBuilder builder = services.newKieBuilder(fileSystem);
        Results results = builder.buildAll().getResults();
        if (results.hasMessages(Message.Level.ERROR)) {
            throw new BeanCreationException("Error building rules: " + results.getMessages());
        }

        KieModule module = builder.getKieModule();

        return services.newKieContainer(module.getReleaseId());
    }
}

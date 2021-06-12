/*
 * Name:   FourthDroolsConfig
 * Author: Bhaskar S
 * Date:   06/11/2021
 * Blog:   https://www.polarsparc.com
 */

package com.polarsparc.fourth.config;

import org.kie.api.KieServices;
import org.kie.api.builder.*;
import org.kie.api.io.KieResources;
import org.kie.api.runtime.KieContainer;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FourthDroolsConfig {
    private final static String FOURTH_DRL = "com/polarsparc/fourth/fourth.drl";

    @Bean
    public KieContainer fourthKieContainer() {
        KieServices services = KieServices.Factory.get();
        KieResources resources = services.getResources();

        KieFileSystem fileSystem = services.newKieFileSystem();
        fileSystem.write(resources.newClassPathResource(FOURTH_DRL));

        KieBuilder builder = services.newKieBuilder(fileSystem);
        Results results = builder.buildAll().getResults();
        if (results.hasMessages(Message.Level.ERROR)) {
            throw new BeanCreationException("Error building rules: " + results.getMessages());
        }

        KieModule module = builder.getKieModule();

        return services.newKieContainer(module.getReleaseId());
    }
}

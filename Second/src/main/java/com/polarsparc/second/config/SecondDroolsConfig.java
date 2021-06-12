/*
 * Name:   SecondDroolsConfig
 * Author: Bhaskar S
 * Date:   06/05/2021
 * Blog:   https://www.polarsparc.com
 */

package com.polarsparc.second.config;

import org.kie.api.KieServices;
import org.kie.api.builder.*;
import org.kie.api.builder.model.KieBaseModel;
import org.kie.api.builder.model.KieModuleModel;
import org.kie.api.builder.model.KieSessionModel;
import org.kie.api.conf.EqualityBehaviorOption;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.io.KieResources;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.conf.ClockTypeOption;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecondDroolsConfig {
    private final static String SECOND_DRL = "src/main/resources/com/polarsparc/second/second.drl";

    @Bean
    public KieContainer secondKieContainer() {
        KieServices services = KieServices.Factory.get();

        ReleaseId releaseId = services.newReleaseId("com.polarsparc.second", "second", "1.0");

        KieFileSystem fileSystem = services.newKieFileSystem();

        KieResources resources = services.getResources();
        Resource drlResource = resources.newFileSystemResource(SECOND_DRL)
                .setResourceType(ResourceType.DRL);

        KieModuleModel model = services.newKieModuleModel();
        KieBaseModel base = model.newKieBaseModel("second-base")
                .setDefault(true)
                .addPackage("com.polarsparc.second")
                .setEqualsBehavior(EqualityBehaviorOption.EQUALITY)
                .setEventProcessingMode(EventProcessingOption.CLOUD);
        base.newKieSessionModel("second-session")
                .setDefault(true)
                .setType(KieSessionModel.KieSessionType.STATEFUL)
                .setClockType(ClockTypeOption.REALTIME);

        fileSystem.generateAndWritePomXML(releaseId)
                .write(drlResource)
                .writeKModuleXML(model.toXML());

        KieBuilder builder = services.newKieBuilder(fileSystem);
        Results results = builder.buildAll().getResults();
        if (results.hasMessages(Message.Level.ERROR)) {
            throw new BeanCreationException("Error building rules: " + results.getMessages());
        }

        KieModule module = builder.getKieModule();

        return services.newKieContainer(module.getReleaseId());
    }
}

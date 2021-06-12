/*
 * Name:   ThirdDroolsConfig
 * Author: Bhaskar S
 * Date:   06/11/2021
 * Blog:   https://www.polarsparc.com
 */

package com.polarsparc.third.config;

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
public class ThirdDroolsConfig {
    private final static String THIRD_R1_DRL = "src/main/resources/com/polarsparc/third/r1/third_r1.drl";
    private final static String THIRD_R2_DRL = "src/main/resources/com/polarsparc/third/r2/third_r2.drl";

    @Bean
    public KieContainer thirdKieContainer() {
        KieServices services = KieServices.Factory.get();

        ReleaseId releaseId = services.newReleaseId("com.polarsparc.third", "third", "1.0");

        KieFileSystem fileSystem = services.newKieFileSystem();

        KieResources resources = services.getResources();

        Resource drlResource_1 = resources.newFileSystemResource(THIRD_R1_DRL)
                .setResourceType(ResourceType.DRL);
        Resource drlResource_2 = resources.newFileSystemResource(THIRD_R2_DRL)
                .setResourceType(ResourceType.DRL);

        KieModuleModel model = services.newKieModuleModel();

        KieBaseModel base_1 = model.newKieBaseModel("third-r1-base")
                .setDefault(true)
                .addPackage("com.polarsparc.third.r1")
                .setEqualsBehavior(EqualityBehaviorOption.EQUALITY)
                .setEventProcessingMode(EventProcessingOption.CLOUD);
        base_1.newKieSessionModel("third-r1-session")
                .setDefault(true)
                .setType(KieSessionModel.KieSessionType.STATEFUL)
                .setClockType(ClockTypeOption.REALTIME);

        KieBaseModel base_2 = model.newKieBaseModel("third-r2-base")
                .addPackage("com.polarsparc.third.r2")
                .setEqualsBehavior(EqualityBehaviorOption.EQUALITY)
                .setEventProcessingMode(EventProcessingOption.CLOUD);
        base_2.newKieSessionModel("third-r2-session")
                .setType(KieSessionModel.KieSessionType.STATEFUL)
                .setClockType(ClockTypeOption.REALTIME);

        fileSystem.generateAndWritePomXML(releaseId)
                .write(drlResource_1)
                .write(drlResource_2)
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

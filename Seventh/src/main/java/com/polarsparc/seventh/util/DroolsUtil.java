/*
 * Name:   DroolsUtil
 * Author: Bhaskar S
 * Date:   07/17/2021
 * Blog:   https://www.polarsparc.com
 */

package com.polarsparc.seventh.util;

import com.polarsparc.seventh.model.Rule;
import lombok.extern.slf4j.Slf4j;
import org.kie.api.KieServices;
import org.kie.api.builder.*;
import org.kie.api.io.KieResources;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.springframework.beans.factory.BeanCreationException;

import java.io.ByteArrayInputStream;
import java.util.List;

@Slf4j
public final class DroolsUtil {
    private final static String VIRTUAL_DRL_FILE = "com/polarsparc/seventh/seventh.drl";

    private final static KieServices services = KieServices.Factory.get();

    private DroolsUtil() {}

    public static KieContainer prepareKieContainer(String tag, List<Rule> rules) {
        ReleaseId releaseId = services.newReleaseId("com.polarsparc.seventh", "seventh", tag);

        KieFileSystem fileSystem = services.newKieFileSystem();

        StringBuilder sb = new StringBuilder();

        rules.forEach(rule -> sb.append(rule.getRuleTxt()).append("\n\n"));

        log.info("---> Drools Rules Set:\n\n{}", sb);

        KieResources resources = services.getResources();

        Resource drlResource = resources.newInputStreamResource(new ByteArrayInputStream(sb.toString().getBytes()))
                .setResourceType(ResourceType.DRL);

        fileSystem.write(VIRTUAL_DRL_FILE, drlResource);
        fileSystem.write(drlResource);
        fileSystem.generateAndWritePomXML(releaseId);

        KieBuilder builder = services.newKieBuilder(fileSystem);
        Results results = builder.buildAll().getResults();
        if (results.hasMessages(Message.Level.ERROR)) {
            throw new BeanCreationException("Error building rules: " + results.getMessages());
        }

        KieModule module = builder.getKieModule();

        return services.newKieContainer(module.getReleaseId());
    }
}

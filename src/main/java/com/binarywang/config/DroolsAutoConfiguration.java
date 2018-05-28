package com.binarywang.config;

import java.io.IOException;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieRepository;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.kie.spring.KModuleBeanFactoryPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.binarywang.util.KieUtils;

/**
 * <pre>
 *
 * Created by Binary Wang on 2018/5/25.
 * </pre>
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@Configuration
public class DroolsAutoConfiguration {
  private static final String RULES_PATH = "rules/";
  private static final String LOCATION_PATTERN = "classpath*:" + RULES_PATH + "**/*.*";

  @Bean
  @ConditionalOnMissingBean(KieFileSystem.class)
  public KieFileSystem kieFileSystem() throws IOException {
    KieFileSystem kieFileSystem = KieServices.Factory.get().newKieFileSystem();
    for (Resource resource : new PathMatchingResourcePatternResolver().getResources(LOCATION_PATTERN)) {
      kieFileSystem.write(ResourceFactory.newClassPathResource(RULES_PATH + resource.getFilename(), "UTF-8"));
    }

    return kieFileSystem;
  }

  @Bean
  @ConditionalOnMissingBean(KieContainer.class)
  public KieContainer kieContainer() throws IOException {
    KieServices kieServices = KieServices.Factory.get();
    final KieRepository kieRepository = kieServices.getRepository();

    kieRepository.addKieModule(kieRepository::getDefaultReleaseId);

    KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem());
    kieBuilder.buildAll();

    KieContainer kieContainer = kieServices.newKieContainer(kieRepository.getDefaultReleaseId());
    KieUtils.setKieContainer(kieContainer);
    return kieServices.newKieContainer(kieRepository.getDefaultReleaseId());
  }

  @Bean
  @ConditionalOnMissingBean(KieBase.class)
  public KieBase kieBase() throws IOException {
    return kieContainer().getKieBase();
  }

  @Bean
  @ConditionalOnMissingBean(KieSession.class)
  public KieSession kieSession() throws IOException {
    KieSession kieSession = kieContainer().newKieSession();
    KieUtils.setKieSession(kieSession);
    return kieSession;
  }

  @Bean
  @ConditionalOnMissingBean(KModuleBeanFactoryPostProcessor.class)
  public KModuleBeanFactoryPostProcessor kiePostProcessor() {
    return new KModuleBeanFactoryPostProcessor();
  }
}

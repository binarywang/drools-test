package com.binarywang.util;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

/**
 * <pre>
 * Kie工具类
 * Created by Binary Wang on 2018/5/28.
 * </pre>
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
public class KieUtils {
  private static KieContainer kieContainer;

  private static KieSession kieSession;

  public static KieContainer getKieContainer() {
    return kieContainer;
  }

  public static void setKieContainer(KieContainer kieContainer) {
    KieUtils.kieContainer = kieContainer;
    kieSession = kieContainer.newKieSession();
  }

  public static KieSession getKieSession() {
    return kieSession;
  }

  public static void setKieSession(KieSession kieSession) {
    KieUtils.kieSession = kieSession;
  }
}

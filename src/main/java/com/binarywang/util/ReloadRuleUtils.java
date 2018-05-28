package com.binarywang.util;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * 重新加载规则的工具类
 * Created by Binary Wang on 2018/5/28.
 * </pre>
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@Slf4j
@Service
public class ReloadRuleUtils {

  public void reload() {
    KieServices kieServices = KieServices.Factory.get();
    KieFileSystem kfs = kieServices.newKieFileSystem();
    kfs.write("src/main/resources/rules/temp.drl", loadRules());
    KieBuilder kieBuilder = kieServices.newKieBuilder(kfs).buildAll();
    Results results = kieBuilder.getResults();
    if (results.hasMessages(Message.Level.ERROR)) {
      log.info(results.getMessages().toString());
      throw new IllegalStateException("### errors ###");
    }

    KieUtils.setKieContainer(kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId()));
    log.info("新规则重载成功");
  }

  private String loadRules() {
    // 从数据库加载的规则
    return "package address\n\n " +
        "import com.binarywang.model.Address;\n " +
        "import com.binarywang.model.fact.AddressCheckResult;\n" +
        "\n" +
        " rule \"Postcode 6 numbers\"\n\n" +
        "  when\n" +
        "  then\n" +
        "        System.out.println(\"规则2中打印日志：校验通过!\");\n" +
        " end";
  }

  private KieServices getKieServices() {
    return KieServices.Factory.get();
  }
}

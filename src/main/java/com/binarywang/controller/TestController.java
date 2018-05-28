package com.binarywang.controller;

import javax.annotation.Resource;

import org.kie.api.runtime.KieSession;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.binarywang.model.Address;
import com.binarywang.model.fact.AddressCheckResult;
import com.binarywang.util.KieUtils;
import com.binarywang.util.ReloadRuleUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 *
 * Created by Binary Wang on 2018/5/25.
 * </pre>
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {
  @Resource
  private ReloadRuleUtils reloadRuleUtils;

  @RequestMapping("/address")
  public String test() {
    KieSession kieSession = KieUtils.getKieSession();

    Address address = new Address();
    address.setPostcode("99425");

    AddressCheckResult result = new AddressCheckResult();
    kieSession.insert(address);
    kieSession.insert(result);
    int ruleFiredCount = kieSession.fireAllRules();
    log.info("触发了" + "条规则" + ruleFiredCount);

    if (result.isPostCodeResult()) {
      log.info("规则校验通过");
    }

    return "nice";
  }

  @RequestMapping("/reload")
  public String reload() {
    reloadRuleUtils.reload();
    return "ok";
  }
}
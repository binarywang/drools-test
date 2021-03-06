package com.binarywang.controller;

import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
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
  private ReloadRuleUtils reloadRuleUtils;

  @Autowired
  public TestController(ReloadRuleUtils reloadRuleUtils) {
    this.reloadRuleUtils = reloadRuleUtils;
  }

  @RequestMapping("/address")
  public String address() {
    KieSession kieSession = KieUtils.getKieSession();

    Address address = new Address();
    address.setPostcode("99425");

    AddressCheckResult result = new AddressCheckResult();
    kieSession.insert(address);
    kieSession.insert(result);
    int ruleFiredCount = kieSession.fireAllRules();
    log.info(Thread.currentThread()  + "== 触发了" + "条规则" + ruleFiredCount);

    if (result.isPostCodeResult()) {
      log.info(Thread.currentThread()  + "=== 规则校验通过");
    }

    return "nice";
  }

  @RequestMapping("/reload")
  public String reload() {
    reloadRuleUtils.reload();
    return "ok";
  }
}
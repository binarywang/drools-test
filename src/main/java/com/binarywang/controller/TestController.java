package com.binarywang.controller;

import javax.annotation.Resource;

import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.binarywang.model.Address;
import com.binarywang.model.fact.AddressCheckResult;

/**
 * <pre>
 *
 * Created by Binary Wang on 2018/5/25.
 * </pre>
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@RequestMapping("/test")
@Controller
public class TestController {

  @Resource
  private KieSession kieSession;

  @ResponseBody
  @RequestMapping("/address")
  public void test() {
    Address address = new Address();
    address.setPostcode("99425");

    AddressCheckResult result = new AddressCheckResult();
    kieSession.insert(address);
    kieSession.insert(result);
    int ruleFiredCount = kieSession.fireAllRules();
    System.out.println("触发了" + ruleFiredCount + "条规则");

    if (result.isPostCodeResult()) {
      System.out.println("规则校验通过");
    }

  }
}
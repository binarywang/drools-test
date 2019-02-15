package com.binarywang.controller;

import java.io.IOException;

import org.hamcrest.CoreMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.testng.annotations.*;

import com.binarywang.config.DroolsAutoConfiguration;
import com.binarywang.util.ReloadRuleUtils;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

/**
 * <pre>
 *
 * Created by Binary Wang on 2018/5/31.
 * </pre>
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
public class TestControllerTest {
  @BeforeClass
  public void init() throws IOException {
    new DroolsAutoConfiguration().kieContainer();

    RestAssuredMockMvc.standaloneSetup(new TestController(new ReloadRuleUtils()));
    RestAssuredMockMvc.resultHandlers(MockMvcResultHandlers.print());
  }

  @Test(invocationCount = 100, threadPoolSize = 5)
  public void testAddress() {
    System.out.println(Thread.currentThread()  + "------------------");
    RestAssuredMockMvc.given()
        .post("/test/address")
        .then().log().everything()
        .body(CoreMatchers.equalTo("nice"))
    ;
  }

  @Test
  public void testReload() {
  }
}
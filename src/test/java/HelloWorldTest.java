import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.apache.log4j.BasicConfigurator;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.testng.annotations.*;

public class HelloWorldTest {
  @Test
  public void testHelloWorld() {
    BasicConfigurator.configure();

    KieServices kieServices = KieServices.Factory.get();
    KieContainer kieContainer = kieServices.newKieClasspathContainer();
    KieSession kieSession = kieContainer.newKieSession("helloWorldSession");
    kieSession.fireAllRules();
    kieSession.dispose();
  }

  @Test
  public void test(){
    LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()), ZoneId.systemDefault());
    System.out.println(dateTime.toLocalDate());
  }
}

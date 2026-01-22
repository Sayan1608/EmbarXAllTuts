package ioc.challenge;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class LooseCouplingExample {

    public static void main(String[] args) {
        ApplicationContext context
                = new ClassPathXmlApplicationContext("applicationIocChallenge.xml");

        UserManager userManagerDb = (UserManager) context.getBean("userManagerDb");
        System.out.println(userManagerDb.getUserDetails());

        UserManager userManagerWs = (UserManager) context.getBean("userManagerWs");
        System.out.println(userManagerWs.getUserDetails());
    }
}

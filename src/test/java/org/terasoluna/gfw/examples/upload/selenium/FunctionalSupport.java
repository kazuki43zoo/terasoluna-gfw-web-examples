package org.terasoluna.gfw.examples.upload.selenium;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:META-INF/spring/seleniumContext.xml" })
public abstract class FunctionalSupport extends ApplicationObjectSupport {

    @Rule
    public TestName testName = new TestName();

    static WebDriver webDriver;

    @BeforeClass
    public static void setUpClass() {
        System.out.println("setUpClass");
    }

    @Before
    public void setUpWebDriver() {
        if (webDriver == null) {
            webDriver = getApplicationContext().getBean(WebDriver.class);
        }
        System.out.println("setUpWebDriver");
        webDriver.get("http://localhost:8080/terasoluna-gfw-web-examples");
    }

    @After
    public void tearDownWebDriver() {
        System.out.println("tearDownWebDriver");
    }

    @AfterClass
    public static void tearDownClass() {
        System.out.println("tearDownClass");
        try {
            webDriver.quit();
        } finally {
            webDriver = null;
        }
    }

}

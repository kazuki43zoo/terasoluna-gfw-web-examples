package org.terasoluna.gfw.examples.upload.selenium;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:META-INF/spring/seleniumContext.xml" })
public class UploadFunctionalTest2 extends FunctionalSupport {

    @Test
    public void test001() {
        System.out.println("test001");
        webDriver.findElement(By.linkText("Multiple Screen in use case")).click();
    }

    @Test
    public void test002() {
        System.out.println("test002");
        webDriver.findElement(By.linkText("Pagination Search")).click();
    }

    @Test
    public void test003() {
        System.out.println("test003");
        webDriver.findElement(By.linkText("Pagination Search")).click();
    }

    @Test
    public void test004() {
        System.out.println("test004");
        webDriver.findElement(By.linkText("Pagination Search")).click();
    }

    @Test
    public void test005() {
        System.out.println("test005");
        webDriver.findElement(By.linkText("Pagination Search")).click();
    }

    @Test
    public void test006() {
        System.out.println("test006");
        webDriver.findElement(By.linkText("Pagination Search")).click();
    }

    @Test
    public void test007() {
        System.out.println("test007");
        webDriver.findElement(By.linkText("Pagination Search")).click();
    }

    @Test
    public void test008() {
        System.out.println("test008");
        webDriver.findElement(By.linkText("Pagination Search")).click();
    }

}

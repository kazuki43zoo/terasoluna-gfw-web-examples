package org.terasoluna.gfw.examples.upload.selenium;

import org.junit.Test;
import org.openqa.selenium.By;

public class UploadFunctionalTest extends FunctionalSupport {

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

}

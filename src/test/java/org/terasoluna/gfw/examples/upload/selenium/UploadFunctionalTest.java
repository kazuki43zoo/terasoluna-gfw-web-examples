package org.terasoluna.gfw.examples.upload.selenium;

import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.core.har.HarEntry;

import org.junit.Test;
import org.openqa.selenium.By;

public class UploadFunctionalTest extends FunctionalSupport {

    @Test
    public void test001() {

        // create a new HAR with the label "yahoo.com"
        // har.getLog().getEntries().clear();

        System.out.println("test001");
        webDriver.findElement(By.linkText("Multiple Screen in use case")).click();
        webDriver.get("http://localhost:8080/terasoluna-gfw-web-examples/");
        webDriver.get("http://localhost:8080/terasoluna-gfw-web-examples/");

        Har har = proxyServer.getHar();
        System.out.println(har.getLog().getEntries().size());
        for (HarEntry he : har.getLog().getEntries()) {
            System.out.println(he.getRequest().getUrl());
            System.out.println(he.getRequest().getHeaders());
            System.out.println(he.getResponse().getStatus());
            System.out.println(he.getResponse().getStatusText());
            System.out.println(he.getResponse().getHeaders());
        }
    }

    @Test
    public void test002() {
        System.out.println("test002");
        webDriver.findElement(By.linkText("Pagination Search")).click();

        Har har = proxyServer.getHar();
        System.out.println(har.getLog().getEntries().size());
        System.out.println(har.getLog().getEntries().get(0).getResponse().getStatus());
    }

}

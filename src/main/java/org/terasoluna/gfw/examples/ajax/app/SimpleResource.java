package org.terasoluna.gfw.examples.ajax.app;

import java.io.Serializable;

public class SimpleResource implements Serializable {

    private static final long serialVersionUID = 1L;

    private String a;

    private String c;

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

}

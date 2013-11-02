package org.terasoluna.gfw.test.utilities.app;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class SubForm {

    private String stringItem;

    private Integer integerItem;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateItem;

    public String getStringItem() {
        return stringItem;
    }

    public void setStringItem(String stringItem) {
        this.stringItem = stringItem;
    }

    public Integer getIntegerItem() {
        return integerItem;
    }

    public void setIntegerItem(Integer integerItem) {
        this.integerItem = integerItem;
    }

    public Date getDateItem() {
        return dateItem;
    }

    public void setDateItem(Date dateItem) {
        this.dateItem = dateItem;
    }

}

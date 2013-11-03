package org.terasoluna.gfw.test.utilities.app;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;

public class RootForm implements Serializable {

    private static final long serialVersionUID = 1L;

    private String stringItem;

    private Integer integerItem;

    @DateTimeFormat(pattern = "yyyyMMdd")
    private Date dateItem;

    private SubForm nestedSubForm;

    private Map<String, String> simpleMap;

    private Map<String, SubForm> nestedSubFormMap;

    private List<String> simpleList;

    private List<SubForm> nestedSubForms;

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

    public SubForm getNestedSubForm() {
        return nestedSubForm;
    }

    public void setNestedSubForm(SubForm nestedSubForm) {
        this.nestedSubForm = nestedSubForm;
    }

    public Map<String, String> getSimpleMap() {
        return simpleMap;
    }

    public void setSimpleMap(Map<String, String> simpleMap) {
        this.simpleMap = simpleMap;
    }

    public List<String> getSimpleList() {
        return simpleList;
    }

    public void setSimpleList(List<String> simpleList) {
        this.simpleList = simpleList;
    }

    public List<SubForm> getNestedSubForms() {
        return nestedSubForms;
    }

    public void setNestedSubForms(List<SubForm> nestedSubForms) {
        this.nestedSubForms = nestedSubForms;
    }

    public Map<String, SubForm> getNestedSubFormMap() {
        return nestedSubFormMap;
    }

    public void setNestedSubFormMap(Map<String, SubForm> nestedSubFormMap) {
        this.nestedSubFormMap = nestedSubFormMap;
    }

}

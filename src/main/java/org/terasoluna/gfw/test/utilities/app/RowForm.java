package org.terasoluna.gfw.test.utilities.app;

import java.io.Serializable;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.springframework.format.annotation.DateTimeFormat;

public class RowForm implements Serializable {

    private static final long serialVersionUID = 1L;

    private int intItem;

    private long longItem;

    private double doubleItem;

    private char charItem;

    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss.SSS")
    private DateTime dateTimeItem;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime localTimeItem;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private DateMidnight dateMidnightItem;

    public int getIntItem() {
        return intItem;
    }

    public void setIntItem(int intItem) {
        this.intItem = intItem;
    }

    public long getLongItem() {
        return longItem;
    }

    public void setLongItem(long longItem) {
        this.longItem = longItem;
    }

    public double getDoubleItem() {
        return doubleItem;
    }

    public void setDoubleItem(double doubleItem) {
        this.doubleItem = doubleItem;
    }

    public char getCharItem() {
        return charItem;
    }

    public void setCharItem(char charItem) {
        this.charItem = charItem;
    }

    public DateTime getDateTimeItem() {
        return dateTimeItem;
    }

    public void setDateTimeItem(DateTime dateTimeItem) {
        this.dateTimeItem = dateTimeItem;
    }

    public LocalTime getLocalTimeItem() {
        return localTimeItem;
    }

    public void setLocalTimeItem(LocalTime localTimeItem) {
        this.localTimeItem = localTimeItem;
    }

    public DateMidnight getDateMidnightItem() {
        return dateMidnightItem;
    }

    public void setDateMidnightItem(DateMidnight dateMidnightItem) {
        this.dateMidnightItem = dateMidnightItem;
    }

}

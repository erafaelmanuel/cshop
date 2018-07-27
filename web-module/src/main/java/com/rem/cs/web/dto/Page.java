package com.rem.cs.web.dto;

public class Page {

    private long number;
    private String href;

    public Page() {}

    public Page(long number, String href) {
        this.number = number;
        this.href = href;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}

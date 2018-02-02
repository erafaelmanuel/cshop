package io.ermdev.cshop.business.register;

public class ConfirmSource {

    private String key;

    public ConfirmSource(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}

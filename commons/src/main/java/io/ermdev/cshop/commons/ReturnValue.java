package io.ermdev.cshop.commons;

@Deprecated
public class ReturnValue {

    private boolean hasError;

    public boolean hasError() {
        return hasError;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }
}

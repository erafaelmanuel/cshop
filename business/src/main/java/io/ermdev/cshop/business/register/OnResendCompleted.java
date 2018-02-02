package io.ermdev.cshop.business.register;

@FunctionalInterface
public interface OnResendCompleted {

    void onComplete(boolean hasError);
}

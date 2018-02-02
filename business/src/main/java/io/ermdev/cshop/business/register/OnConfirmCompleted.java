package io.ermdev.cshop.business.register;

@FunctionalInterface
public interface OnConfirmCompleted {

    void onComplete(boolean hasError);
}

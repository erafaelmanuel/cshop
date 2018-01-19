package io.ermdev.cshop.business.register;

@FunctionalInterface
public interface OnVerificationFailure {
    void call();
}

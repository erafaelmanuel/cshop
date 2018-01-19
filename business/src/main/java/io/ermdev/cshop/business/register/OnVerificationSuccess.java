package io.ermdev.cshop.business.register;

@FunctionalInterface
public interface OnVerificationSuccess {
    void call(VerificationSource verificationSource);
}

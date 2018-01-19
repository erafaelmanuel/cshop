package io.ermdev.cshop.business.verification;

@FunctionalInterface
public interface OnVerificationSuccess {
    void call(VerificationSource verificationSource);
}

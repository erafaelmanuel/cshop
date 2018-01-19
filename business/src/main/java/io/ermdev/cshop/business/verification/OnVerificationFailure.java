package io.ermdev.cshop.business.verification;

@FunctionalInterface
public interface OnVerificationFailure {
    void call(VerificationSource verificationSource);
}

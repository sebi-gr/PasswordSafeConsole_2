package com.passwordsafe.observer;

public class ResetPasswordSubscriber implements IPasswordSubscriber {
    @Override
    public void update(IPasswordSubscriber observer, String message) {
        if (observer instanceof ResetPasswordSubscriber)
            System.out.println(message);
    }
}

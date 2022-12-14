package com.passwordsafe.observer;

public class WrongPasswordSubscriber implements IPasswordSubscriber {

    @Override
    public void update(IPasswordSubscriber observer, String message) {
        if (observer instanceof WrongPasswordSubscriber)
            System.out.println(message);
    }
}

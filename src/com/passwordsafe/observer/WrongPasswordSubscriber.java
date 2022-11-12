package com.passwordsafe.observer;

public class WrongPasswordSubscriber implements IPasswordSubscriber {

    @Override
    public void update(String message) {
        System.out.println(message);
    }
}

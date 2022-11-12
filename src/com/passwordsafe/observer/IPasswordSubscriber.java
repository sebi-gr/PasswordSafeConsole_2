package com.passwordsafe.observer;

public interface IPasswordSubscriber {
    public void update(IPasswordSubscriber observer, String message);
}

package com.passwordsafe.observer;

import java.util.ArrayList;
import java.util.List;

public class PasswordPublisher {

    List<IPasswordSubscriber> subcribers = new ArrayList<>();

    public void addSubscriber(IPasswordSubscriber newSubscriber) {
        subcribers.add(newSubscriber);
    }

    public void deleteSubscriber(IPasswordSubscriber subscriber) {
        subcribers.remove(subscriber);
    }

    public void notify(IPasswordSubscriber observer, String message) {
        for (IPasswordSubscriber subscriber : subcribers) {
            subscriber.update(observer, message);
        }
    }
}

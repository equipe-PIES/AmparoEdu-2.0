package br.com.amparoedu.util;

import java.util.ArrayList;
import java.util.List;

public class SimpleObservable {
    private final List<SimpleObserver> observers = new ArrayList<>();

    public void addObserver(SimpleObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(SimpleObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(Object arg) {
        for (SimpleObserver observer : observers) {
            observer.update(arg);
        }
    }
}

package projectTP.weichi.client.observer;

public interface Observable {
    void addObserver(Observer observer);
    void removeObserver();
}

package by.bsu.onlinestore.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Store {
    private String name;
    private List<Item> items;
    private List<String> feedbackList;

    public Store() {
        this.name = null;
        this.items = null;
        this.feedbackList = null;
    }

    public Store(String name, List<String> feedbackList) {
        this.name = name;
        items = new ArrayList<Item>();
        this.feedbackList = feedbackList;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void setFeedbackList(List<String> feedbackList) {
        this.feedbackList = feedbackList;
    }

    public String getName() {
        return name;
    }

    public List<Item> getItems() {
        return items;
    }

    public List<String> getFeedbackList() {
        return feedbackList;
    }

    public List<String> getItemsNames() {
        List<String> names = new ArrayList<>();
        for (Item item : items) {
            names.add(item.getName());
        }
        return names;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Store store = (Store) o;
        return Objects.equals(name, store.name) &&
                Objects.equals(feedbackList, store.feedbackList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, feedbackList);
    }

    @Override
    public String toString() {
        return "Store{" +
                "name='" + name + '\'' +
                ", itemsNames=" + getItemsNames() +
                ", feedbackList=" + feedbackList +
                '}';
    }
}

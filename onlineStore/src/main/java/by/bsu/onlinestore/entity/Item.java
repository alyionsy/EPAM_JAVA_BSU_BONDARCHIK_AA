package by.bsu.onlinestore.entity;

import by.bsu.onlinestore.exception.ItemException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Item {
    private String name;
    private int price;
    private int count;
    private List<Store> stores;

    public Item() {
        this.name = null;
        this.price = 0;
        this.count = 0;
        this.stores = null;
    }

    public Item(String name, int price, int count) {
        this.name = name;
        this.price = price;
        this.count = count;
        stores = new ArrayList<Store>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) throws ItemException {
        this.price = price;

        checkPrice(price);
    }

    public void setCount(int count) throws ItemException {
        this.count = count;

        checkCount(count);
    }

    public void setStores(List<Store> stores) {
        this.stores = stores;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getCount() {
        return count;
    }

    public List<Store> getStores() {
        return stores;
    }

    public List<String> getStoresNames() {
        List<String> names = new ArrayList<>();
        for (Store store : stores) {
            names.add(store.getName());
        }
        return names;
    }

    private void checkPrice(int price) throws ItemException {
        if (price <= 0) {
            throw new ItemException("Price must be a positive number!");
        }
    }

    private void checkCount(int count) throws ItemException {
        if (count <= 0) {
            throw new ItemException("Count must be a positive number!");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return price == item.price &&
                count == item.count &&
                Objects.equals(name, item.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, count);
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", count=" + count +
                ", stores=" + getStoresNames() +
                '}';
    }
}

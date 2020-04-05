package by.bsu.onlinestore;

import by.bsu.onlinestore.entity.Item;
import by.bsu.onlinestore.entity.Store;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

public class TestItemsStream {
    private List<Item> items;
    private List<Store> stores;

    private final static Logger logger = LogManager.getLogger(TestItemsStream.class.getName());

    public TestItemsStream() {
        List<String> goodFeedback = Arrays.asList("excellent", "fine", "well");
        List<String> badFeedback = Arrays.asList("bad", "terrible", "awful");
        //        items initialization
        Item item1 = new Item("Nike Air Force", 160, 50);
        Item item2 = new Item("Balenciaga Triple-S", 740, 10);
        Item item3 = new Item("Fila Disruptor", 70, 100);
        Item item4 = new Item("Vans Oldskool Replica", 35, 120);
        Item item5 = new Item("New Balance 998 Replica", 40, 150);
        //        stores initialization
        Store store1 = new Store("Farfetch", goodFeedback);
        Store store2 = new Store("AliExpress", badFeedback);
        //        setting missing lists
        item1.setStores(Arrays.asList(store1, store2));
        item2.setStores(Collections.singletonList(store1));
        item3.setStores(Arrays.asList(store1, store2));
        item4.setStores(Collections.singletonList(store2));
        item5.setStores(Collections.singletonList(store2));

        store1.setItems(Arrays.asList(item1, item2, item3));
        store2.setItems(Arrays.asList(item1, item3, item4, item5));

        items = Arrays.asList(item1, item2, item3, item4, item5);
        stores = Arrays.asList(store1, store2);
    }

    @Test
    public void checkItemsMoreExpensiveThan500Test() {
        logger.info("Checking if there items more expensive than 500.");
        List<Item> checkedItems = items.stream()
                .filter(items -> items.getPrice() > 500)
                .peek(item -> logger.debug("Found item: " + item))
                .collect(Collectors.toCollection(ArrayList::new));

        int expected = 1;
        int actual = checkedItems.size();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void findMaxCountTest() {
        logger.info("Finding items with max count.");
        Optional<Item> maxCountItemOptional = items.stream().max(Comparator.comparing(Item::getCount));

        Item result  = maxCountItemOptional.isPresent() ? maxCountItemOptional.get() : new Item();
        logger.debug("Found item: " + result);

        int actual = result.getCount();
        int expected = 150;

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void findMinCountTest() {
        logger.info("Finding items with min count.");
        Optional<Item> optionalItemMinCount = items.stream().min(Comparator.comparing(Item::getCount));

        Item result = optionalItemMinCount.orElse(new Item());
        logger.debug("Found item: " + result);

        int actual = result.getCount();
        int expected = 10;

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void filterItemsWithSingleStoreTest() {
        logger.info("Filtering items with a single store.");
        List<Item> filteredItems = items.stream()
                .filter(item -> item.getStores().size() == 1)
                .peek(item -> logger.debug("Found item: " + item))
                .collect((Collectors.toCollection(ArrayList::new)));

        int expected = 3;
        int actual = filteredItems.size();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void sortItemsByPriceTest() {
        logger.info("Sorting items by its' price.");
        List<Item> expected = Arrays.asList(items.get(3), items.get(4), items.get(2),
                items.get(0), items.get(1));
        List<Item> actual = items.stream()
                .sorted(Comparator.comparing(Item::getPrice))
                .peek(item -> logger.debug("Sorted item: " + item))
                .collect(Collectors.toList());

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void sortItemsByCountTest() {
        logger.info("Sorting items by its' count.");
        List<Item> expected = Arrays.asList(items.get(1), items.get(0), items.get(2),
                items.get(3), items.get(4));
        List<Item> actual = items.stream()
                .sorted(Comparator.comparing(Item::getCount))
                .peek(item -> logger.debug("Sorted item: " + item))
                .collect(Collectors.toList());

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getStoresListTest() {
        logger.info("Getting stores list.");
        List<Store> expected = Arrays.asList(stores.get(0), stores.get(1),
                stores.get(0), stores.get(0), stores.get(1), stores.get(1), stores.get(1));

        List<Store> actual = new ArrayList<>();
        items.forEach(item -> actual.addAll(item.getStores()));
        actual.forEach(logger::debug);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getStoresListWithoutDuplicatesTest() {
        logger.info("Getting stores list without duplicates.");

        List<Store> result = new ArrayList<>();
        items.forEach(item -> result.addAll(item.getStores()));

        List<Store> actual = result.stream()
                .distinct()
                .peek(logger::debug)
                .collect(Collectors.toList());

        Assertions.assertEquals(stores, actual);
    }

    @Test
    public void printStoresInfoByItems() {
        logger.info("Printing stores information.");

        List<Store> result = new ArrayList<>();
        items.forEach(item -> result.addAll(item.getStores()));

        List<Store> actual = result.stream()
                .distinct()
                .collect(Collectors.toList());

        actual.forEach(System.out::println);
    }

    @Test
    public void printStoresInfoByStores() {
        logger.info("Printing stores information (using stores list).");

        stores.forEach(System.out::println);
    }

    @Test
    public void usingParallelStream(){
        long start;

        start = System.nanoTime();
        List<Item> temp2 = items.stream()
                .filter(item -> item.getStores().size() == 1)
                .peek(item -> logger.debug("Found item: " + item))
                .collect((Collectors.toCollection(ArrayList::new)));
        double timeNotParallel = (double)(System.nanoTime() - start) / 1000000;

        start = System.nanoTime();
        List<Item> temp1 = items.parallelStream()
                .filter(item -> item.getStores().size() == 1)
                .peek(item -> logger.debug("Found item: " + item))
                .collect((Collectors.toCollection(ArrayList::new)));
        double timeParallel = (double)(System.nanoTime() - start) / 1000000;

        logger.info("\nPerformance using parallelStream(): " + timeParallel + " ms" +
                "\nPerformance without using parallelStream(): " + timeNotParallel + " ms" +
                "\nDifference (times): " + timeNotParallel/timeParallel);
    }

}

package com.example.mock;

import com.example.Product;

import java.util.*;
import java.util.stream.Collectors;

public class DAOMock implements IDAOMock {

    private static DAOMock instance = new DAOMock();
    private Map<Integer, Product> productMap;

    private DAOMock() {
        productMap = new HashMap<>();

        Product p1 = new Product();
        p1.setName("孤独のグルメ1");
        p1.setPrice(1234);
        insert(p1);

        Product p2 = new Product();
        p2.setName("孤独のグルメ2");
        p2.setPrice(994);
        insert(p2);
    }

    public static DAOMock getInstance() {
        return instance;
    }

    @Override
    public List<Product> select() {
        List<Product> p = productMap.entrySet()
                .stream()
                .map(Map.Entry::getValue)
                .sorted(Comparator.comparing(Product::getId))
                .collect(Collectors.toList());
        return p;
    }

    @Override
    public Product select(int id) {
        Optional<Product> p = productMap.entrySet()
                .stream()
                .filter(e -> Objects.equals(e.getKey(), id))
                .map(e -> e.getValue())
                .findFirst();
        return p.orElseThrow(() -> new IllegalArgumentException("検索しようとしたidが存在しません:" + id));
    }

    @Override
    public synchronized void insert(Product product) {
        if (Objects.isNull(product)) {
            throw new NullPointerException("登録データが存在しません");
        }
        int maxId = productMap.entrySet()
                .stream()
                .map(Map.Entry::getKey)
                .max((a, b) -> a.compareTo(b)).orElse(0);
        int newId = maxId + 1;
        product.setId(newId);
        productMap.put(newId, product);
    }

    @Override
    public synchronized void update(int id, Product product) {
        if (Objects.isNull(product)) {
            throw new NullPointerException("更新データが存在しません");
        }
        product.setId(id);
        if (count(id) > 0) {
            productMap.replace(id, product);
            return;
        }
        throw new IllegalArgumentException("更新しようとしたidが存在しません:" + id);
    }

    @Override
    public synchronized void delete(int id) {
        if (count(id) > 0) {
            productMap.remove(id);
            return;
        }
        throw new IllegalArgumentException("削除しようとしたidが存在しません:" + id);
    }

    protected long count(int id) {
        return productMap.entrySet()
                .stream()
                .filter(e -> Objects.equals(e.getKey(), id))
                .count();
    }
}

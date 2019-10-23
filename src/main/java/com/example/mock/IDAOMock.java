package com.example.mock;

import com.example.Product;

import java.util.List;
import java.util.Optional;

public interface IDAOMock {

    List<Product> select();

    Product select(int id);

    void insert(Product product);

    void update(int id, Product product);

    void delete(int id);
}

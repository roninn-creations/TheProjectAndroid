package com.roninn_creations.theproject.services;

import java.util.List;
import java.util.function.Consumer;

public interface IService<T> {

    void readAll(Consumer<List<T>> onResponse, String tag);
    void read(String id, Consumer<T> onResponse, String tag);
    void create(T model, Consumer<T> onResponse, String tag);
    void update(T model, Consumer<T> onResponse, String tag);
    void delete(String id, Consumer<T> onResponse, String tag);
}

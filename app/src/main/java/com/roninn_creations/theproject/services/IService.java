package com.roninn_creations.theproject.services;

import java.util.List;
import java.util.function.Consumer;

public interface IService<T> {

    void create(T model, Consumer<T> onResponse,
                Consumer<String> onError, String tag);
    void readMany(String params, Consumer<List<T>> onResponse,
                  Consumer<String> onError, String tag);
    void read(String id, Consumer<T> onResponse,
              Consumer<String> onError, String tag);
    void update(T model, Consumer<T> onResponse,
                Consumer<String> onError, String tag);
    void delete(String id, Consumer<T> onResponse,
                Consumer<String> onError, String tag);
}

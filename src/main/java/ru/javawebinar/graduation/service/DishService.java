package ru.javawebinar.graduation.service;

import ru.javawebinar.graduation.model.Dish;
import ru.javawebinar.graduation.util.exception.NotFoundException;

import java.util.List;

public interface DishService {

    Dish create(Dish dish, int restaurantId);

    void delete(int id) throws NotFoundException;

    Dish get(int id) throws NotFoundException;

    Dish update(Dish dish, int restaurantId);

    List<Dish> getAll(int restId);

}

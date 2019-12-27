package ru.javawebinar.graduation.service;

import ru.javawebinar.graduation.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

public interface RestaurantService {

    List<Restaurant> getAll(LocalDate date);

    List<Restaurant> getAllForId(LocalDate date, int id);

    List<Restaurant> getAllWithoutDish();

    Restaurant get(int id);

    void delete(int id);

    Restaurant create(Restaurant restaurant);

    Restaurant update(Restaurant restaurant);

}

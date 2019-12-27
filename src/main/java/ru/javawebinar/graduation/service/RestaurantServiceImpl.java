package ru.javawebinar.graduation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.graduation.model.Restaurant;
import ru.javawebinar.graduation.repository.RestaurantRepository;
import ru.javawebinar.graduation.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import static ru.javawebinar.graduation.util.ValidationUtil.checkNotFoundWithId;

@Service("restaurantService")
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override
    public List<Restaurant> getAll(LocalDate date) {
        return restaurantRepository.findAllWithDish(date);
    }

    @Override
    public List<Restaurant> getAllForId(LocalDate date, int id) {
        return checkNotFoundWithId(restaurantRepository.findAllWithDishForId(date, id), id);
    }

    @Override
    public List<Restaurant> getAllWithoutDish() {
        return restaurantRepository.findAll();
    }

    @Override
    public Restaurant get(int id) {
        Restaurant restaurant = restaurantRepository.findById(id).orElse(null);
        checkNotFoundWithId(Restaurant.class, id);
        return restaurant;
    }

    @Override
    @Transactional
    public void delete(int id) {
        restaurantRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Restaurant create(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @Override
    @Transactional
    public Restaurant update(Restaurant restaurant) {
        try {
            restaurantRepository.findById(restaurant.getId()).get();
        } catch (NoSuchElementException e) {
            throw new NotFoundException("not found restaurant with id:=" + restaurant.getId());
        }
        return checkNotFoundWithId(restaurantRepository.save(restaurant), restaurant.getId());
    }
}

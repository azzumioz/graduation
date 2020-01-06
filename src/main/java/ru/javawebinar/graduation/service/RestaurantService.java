package ru.javawebinar.graduation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.graduation.model.Restaurant;
import ru.javawebinar.graduation.repository.RestaurantRepository;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;

import static ru.javawebinar.graduation.util.ValidationUtil.checkNotFoundWithId;

@Service("restaurantService")
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Cacheable("restaurants")
    public List<Restaurant> getAll(LocalDate date) {
        return restaurantRepository.findAllWithDish(date);
    }

    public List<Restaurant> getAllForId(LocalDate date, int id) {
        return checkNotFoundWithId(restaurantRepository.findAllWithDishForId(date, id), id);
    }

    public List<Restaurant> getAllWithoutDish() {
        return restaurantRepository.findAll();
    }

    public Restaurant get(int id) {
        return restaurantRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Restaurant not found by id " + id));
    }

    @Transactional
    @CacheEvict(value = "restaurants", allEntries = true)
    public void delete(int id) {
        restaurantRepository.deleteById(id);
    }

    @Transactional
    @CacheEvict(value = "restaurants", allEntries = true)
    public Restaurant create(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @Transactional
    @CacheEvict(value = "restaurants", allEntries = true)
    public Restaurant update(Restaurant restaurant) {
        restaurantRepository.findById(restaurant.getId()).orElseThrow(() -> new EntityNotFoundException("Restaurant not found by id " + restaurant.getId()));
        return checkNotFoundWithId(restaurantRepository.save(restaurant), restaurant.getId());
    }

}

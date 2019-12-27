package ru.javawebinar.graduation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.javawebinar.graduation.model.Dish;
import ru.javawebinar.graduation.model.Restaurant;
import ru.javawebinar.graduation.repository.DishRepository;
import ru.javawebinar.graduation.repository.RestaurantRepository;

import java.time.LocalDateTime;
import java.util.List;

import static ru.javawebinar.graduation.util.ValidationUtil.checkNotFoundWithId;

@Service("dishService")
public class DishServiceImpl implements DishService {

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override
    public Dish create(Dish dish, int restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElse(null);

        checkNotFoundWithId(restaurant, restaurantId, Restaurant.class);
        dish.setRestaurant(restaurant);
        if (dish.getDate() == null) {
            dish.setDate(LocalDateTime.now());
        }
        return dishRepository.save(dish);
    }

    @Override
    public void delete(int id) {
        dishRepository.deleteById(id);
    }

    @Override
    public Dish get(int id) {
        Dish dish = dishRepository.findById(id).orElse(null);
        checkNotFoundWithId(Dish.class, id);
        return dish;
    }

    @Override
    @Transactional
    public Dish update(Dish dish, int restaurantId) {
        Assert.notNull(dish, "dish must not be null");
        dish.setRestaurant(restaurantRepository.getOne(restaurantId));
        checkNotFoundWithId(dishRepository.save(dish), dish.getId());
        return dish;

    }

    @Override
    public List<Dish> getAll(int restId) {
        List<Dish> dishes = dishRepository.getAll(restId).orElse(null);
        Assert.notNull(dishes, "not found dishes with restaurant id: " + restId);
        return dishes;
    }

}

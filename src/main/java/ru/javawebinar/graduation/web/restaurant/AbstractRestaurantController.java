package ru.javawebinar.graduation.web.restaurant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.graduation.model.Restaurant;
import ru.javawebinar.graduation.service.RestaurantService;

import java.time.LocalDate;
import java.util.List;

import static ru.javawebinar.graduation.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.graduation.util.ValidationUtil.checkNew;

public class AbstractRestaurantController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RestaurantService service;


    public List<Restaurant> getAll(LocalDate date) {
        log.info("get all restaurants");
        return service.getAll(date);
    }

    public List<Restaurant> get(LocalDate date, int id) {
        log.info("getAll for date {} from restaurant {}", date, id);
        return service.getAllForId(date, id);
    }

    public Restaurant getById(int id) {
        log.info("get restaurant by Id {}", id);
        return service.get(id);
    }

    List<Restaurant> getAllWithoutDish() {
        log.info("getAll restaurant without dish");
        return service.getAllWithoutDish();
    }

    public void delete(int id) {
        log.info("delete restaurant {}", id);
        service.delete(id);
    }

    public Restaurant update(Restaurant restaurant, int id) {
        assureIdConsistent(restaurant, id);
        log.info("update restaurant {}", id);
        return service.update(restaurant);
    }

    public Restaurant create(Restaurant restaurant) {
        checkNew(restaurant);
        log.info("create restaurant {}", restaurant.getId());
        return service.create(restaurant);
    }

}

package ru.javawebinar.graduation.web.dish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.graduation.model.Dish;
import ru.javawebinar.graduation.service.DishService;

import java.util.List;

import static ru.javawebinar.graduation.util.ValidationUtil.assureIdConsistent;

@RestController
@RequestMapping(value = AdminDishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminDishController {
    static final String REST_URL = "/rest/admin/dishes";

    private final DishService dishService;

    @Autowired
    public AdminDishController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping(value = "/restaurants/{restId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public List<Dish> getAllByRest(@PathVariable("restId") int restId) {
        return dishService.getAll(restId);
    }

    @GetMapping(value = "/{id}")
    //@ResponseStatus(value = HttpStatus.OK)
    public Dish get(@PathVariable("id") int id) {
        return dishService.get(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        dishService.delete(id);
    }

    @PutMapping(value = "/restaurants/{restId}/dishes/{dishId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public Dish update(@RequestBody Dish dish, @PathVariable("restId") int restId, @PathVariable("dishId") int dishId) {
        assureIdConsistent(dish, dishId);
        return dishService.update(dish, restId);
    }

    @PostMapping(value = "/restaurants/{restId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Dish save(@RequestBody Dish dish, @PathVariable("restId") int restId) {
        return dishService.create(dish, restId);
    }

}

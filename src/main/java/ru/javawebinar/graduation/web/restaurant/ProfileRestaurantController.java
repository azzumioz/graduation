package ru.javawebinar.graduation.web.restaurant;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.graduation.model.Restaurant;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ru.javawebinar.graduation.util.ValidationUtil.checkNew;

@RestController

@RequestMapping(value = ProfileRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileRestaurantController extends AbstractRestaurantController {

    static final String REST_URL = "/rest/profile/restaurants";

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurant> getAllForId(@PathVariable("id") int id,
                                        @RequestParam(value = "date", required = false) LocalDate date) {
        return super.get(getDate(date), id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurant> getAll(@RequestParam(value = "date", required = false) LocalDate date) {
        return super.getAll(getDate(date));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> saveRestaurant(@RequestBody Restaurant restaurant) {
        checkNew(restaurant);
        Restaurant created = super.create(restaurant);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/restaurants")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    private static LocalDate getDate(LocalDate date) {
        return date == null ? LocalDate.now() : date;
    }

}

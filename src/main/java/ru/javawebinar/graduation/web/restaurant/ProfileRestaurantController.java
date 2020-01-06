package ru.javawebinar.graduation.web.restaurant;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.graduation.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

@RestController

@RequestMapping(value = ProfileRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileRestaurantController extends AbstractRestaurantController {

    static final String REST_URL = "/rest/restaurants";

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurant> getAllForId(@PathVariable("id") int id,
                                        @RequestParam(value = "date", required = false) LocalDate date) {
        return super.get(getDate(date), id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurant> getAll(@RequestParam(value = "date", required = false) LocalDate date) {
        return super.getAll(getDate(date));
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

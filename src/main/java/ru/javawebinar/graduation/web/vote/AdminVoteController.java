package ru.javawebinar.graduation.web.vote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.graduation.service.VoteService;
import ru.javawebinar.graduation.to.VoteTo;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = AdminVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminVoteController {
    static final String REST_URL = "/rest/admin/votes";

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private VoteService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<VoteTo> getToday() {
        log.info("get today Vote for all users");
        return service.getBetweenDates(LocalDate.now(), LocalDate.now());
    }

    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<VoteTo> getBetween(
            @RequestParam(value = "startDate", required = false) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) LocalDate endDate) {
        log.info("Votes getBetween dates({} - {})) for all users", startDate, endDate);
        return service.getBetweenDates(startDate, endDate);
    }

    @GetMapping(value = "/filter/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<VoteTo> getBetweenByUser(@PathVariable("userId") int userId,
                                         @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                         @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        log.info("Votes getBetween dates({} - {}) for user {}", startDate, endDate, userId);
        return service.getBetweenDatesWithUser(userId, startDate, endDate);
    }

}

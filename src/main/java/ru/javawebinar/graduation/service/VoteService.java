package ru.javawebinar.graduation.service;

import ru.javawebinar.graduation.model.Vote;
import ru.javawebinar.graduation.to.VoteTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface VoteService {

    List<VoteTo> getBetweenDates(LocalDate startDate, LocalDate endDate);

    List<VoteTo> getBetweenDatesWithUser(int userId, LocalDate startDate, LocalDate endDate);

    List<Vote> getAll(int userId);

    VoteTo save(int userId, LocalDateTime dateTime, VoteTo voteTo);


}

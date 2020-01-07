package ru.javawebinar.graduation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.graduation.model.Vote;
import ru.javawebinar.graduation.repository.RestaurantRepository;
import ru.javawebinar.graduation.repository.UserRepository;
import ru.javawebinar.graduation.repository.VoteRepository;
import ru.javawebinar.graduation.to.VoteTo;
import ru.javawebinar.graduation.util.ValidationUtil;
import ru.javawebinar.graduation.util.VoteUtil;
import ru.javawebinar.graduation.util.exception.DoubleViolationException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.graduation.util.DateTimeUtil.adjustEndDateTime;
import static ru.javawebinar.graduation.util.DateTimeUtil.adjustStartDateTime;

@Service("voteService")
public class VoteService {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private UserRepository userRepository;

    public VoteTo getVote(int userId, LocalDateTime dateTime) {
        return VoteUtil.asTo(voteRepository.getMyVote(dateTime.with(LocalTime.MIN), dateTime.with(LocalTime.MAX), userId));
    }

    public List<Vote> getAll(int userId) {
        return voteRepository.findByUserIdOrderByDateTimeDesc(userId);
    }

    public List<VoteTo> getBetweenDates(@Nullable LocalDate startDate, @Nullable LocalDate endDate) {
        return VoteUtil.asTo(voteRepository.getAllBetweenDate(adjustStartDateTime(startDate), adjustEndDateTime(endDate)).orElseThrow(() -> new IllegalArgumentException("not found votes for period from [ " + startDate + " ] to [ " + endDate + " ]")));
    }

    public List<VoteTo> getBetweenDatesWithUser(int userId, LocalDate startDate, LocalDate endDate) {
        return VoteUtil.asTo(voteRepository.getAllBetweenDateWithUserId(userId, adjustStartDateTime(startDate), adjustEndDateTime(endDate)).orElseThrow(() -> new IllegalArgumentException("not found votes for period from [ " + startDate + " ] to [ " + endDate + " ] and user id : \" + userId")));
    }

    @Transactional
    public VoteTo save(int userId, LocalDateTime dateTime, VoteTo voteTo) {
        Vote vote = voteRepository.getMyVote(dateTime.with(LocalTime.MIN), dateTime.with(LocalTime.MAX), userId);

        if (voteTo.isNew() && vote != null) {
            throw new DoubleViolationException("Have you already voted today");
        }
        if (!voteTo.isNew() && vote != null) {
            ValidationUtil.checkTimeForOperations(dateTime.toLocalTime());
        }
        if (voteTo.isNew() && vote == null) {
            vote = new Vote();
            vote.setUser(userRepository.getOne(userId));
        }

        vote.setRestaurant(restaurantRepository.getOne(voteTo.getRestaurantId()));
        vote.setDateTime(dateTime);

        return VoteUtil.asTo(voteRepository.save(vote));
    }


}

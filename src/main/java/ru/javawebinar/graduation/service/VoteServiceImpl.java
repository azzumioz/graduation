package ru.javawebinar.graduation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.javawebinar.graduation.model.Vote;
import ru.javawebinar.graduation.repository.RestaurantRepository;
import ru.javawebinar.graduation.repository.UserRepository;
import ru.javawebinar.graduation.repository.VoteRepository;
import ru.javawebinar.graduation.to.VoteTo;
import ru.javawebinar.graduation.util.ValidationUtil;
import ru.javawebinar.graduation.util.VoteUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.graduation.util.DateTimeUtil.adjustEndDateTime;
import static ru.javawebinar.graduation.util.DateTimeUtil.adjustStartDateTime;

@Service("voteService")
public class VoteServiceImpl implements VoteService {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override
    public List<Vote> getAll(int userId) {
        return voteRepository.findByUserIdOrderByDateTimeDesc(userId);
    }

    @Override
    public List<VoteTo> getBetweenDates(@Nullable LocalDate startDate, @Nullable LocalDate endDate) {
        List<Vote> votes = voteRepository.getAllBetweenDate(adjustStartDateTime(startDate), adjustEndDateTime(endDate)).orElse(null);
        Assert.notNull(votes, "not found votes for period from [ " + startDate + " ] to [ " + endDate + " ]");
        return VoteUtil.asTo(votes);
    }

    @Override
    public List<VoteTo> getBetweenDatesWithUser(int userId, LocalDate startDate, LocalDate endDate) {
        List<Vote> votes = voteRepository.getAllBetweenDateWithUserId(userId, adjustStartDateTime(startDate), adjustEndDateTime(endDate)).orElse(null);
        Assert.notNull(votes, "not found votes for period from [ " + startDate + " ] to [ " + endDate + " ] and user id : " + userId);
        return VoteUtil.asTo(votes);
    }

    @Override
    @Transactional
    public VoteTo save(int userId, LocalDateTime dateTime, VoteTo voteTo) {

        ValidationUtil.checkTimeForOperations(dateTime.toLocalTime());

        Vote vote = voteRepository.getMyVote(dateTime.with(LocalTime.MIN), dateTime.with(LocalTime.MAX), userId);
        if (voteTo.isNew() && vote == null) {
            vote = new Vote();
        }
        vote.setRestaurant(restaurantRepository.getOne(voteTo.getRestaurantId()));
        vote.setDateTime(dateTime);
        vote.setUser(userRepository.getOne(userId));

        return VoteUtil.asTo(voteRepository.save(vote));
    }

}

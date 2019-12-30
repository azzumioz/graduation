package ru.javawebinar.graduation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.graduation.model.Vote;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends JpaRepository<Vote, Integer> {

    @Override
    @Transactional
    Vote save(Vote vote);

    List<Vote> findByUserIdOrderByDateTimeDesc(int userId);

    @Query("SELECT v FROM Vote v WHERE v.dateTime BETWEEN :startDate AND :endDate AND v.user.id=:userId")
    Vote getMyVote(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("userId") int userId);

    @Modifying
    @Query("SELECT v from Vote v WHERE v.dateTime BETWEEN :startDate AND :endDate")
    Optional<List<Vote>> getAllBetweenDate(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Modifying
    @Query("SELECT v FROM Vote v WHERE v.user.id=:userId AND v.dateTime BETWEEN :startDate AND :endDate")
    Optional<List<Vote>> getAllBetweenDateWithUserId(@Param("userId") int userId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

}

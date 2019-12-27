package ru.javawebinar.graduation.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.graduation.model.Restaurant;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    @EntityGraph(attributePaths = {"dishes"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT r FROM Restaurant r JOIN FETCH r.dishes d WHERE d.date =?1 ORDER BY r.name ASC ")
    List<Restaurant> findAllWithDish(LocalDate date);

    @EntityGraph(attributePaths = {"dishes"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT r FROM Restaurant r JOIN FETCH r.dishes d WHERE d.date = ?1 AND r.id =?2 ORDER BY r.name ASC ")
    List<Restaurant> findAllWithDishForId(LocalDate date, int id);

    @Override
    Optional<Restaurant> findById(Integer id);

}

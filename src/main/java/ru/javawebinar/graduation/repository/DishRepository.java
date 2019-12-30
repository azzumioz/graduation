package ru.javawebinar.graduation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.graduation.model.Dish;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface DishRepository extends JpaRepository<Dish, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Dish d WHERE d.id=:id AND d.restaurant.id=:restaurantId")
    int delete(@Param("id") int id, @Param("restaurantId") int restaurantId);

    @Modifying
    @Query("SELECT d FROM Dish d where d.restaurant.id=:restId ")
    Optional<List<Dish>> getAll(@Param("restId")int restId);

    @Override
    @Transactional
    Dish save(Dish entity);

    @Override
    @Transactional
    void deleteById(Integer id);

    @Override
    Optional<Dish> findById(Integer id);

}


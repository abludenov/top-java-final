package com.abdddev.restaurantvoiting.repository;

import com.abdddev.restaurantvoiting.error.IllegalRequestDataException;
import com.abdddev.restaurantvoiting.model.Restaurant;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {
    @Query("SELECT r FROM Restaurant r ORDER BY r.id ASC")
    List<Restaurant> getAll();

    @EntityGraph(attributePaths = {"dishes"}, type = EntityGraph.EntityGraphType.FETCH)
    @Query("SELECT r FROM Restaurant r WHERE r.id = :id")
    Optional<Restaurant> getWithDishes(int id);

    default Restaurant getExistedWithDishes(int id) {
        return getWithDishes(id).orElseThrow(
                () -> new IllegalRequestDataException("Restaurant id=" + id + " not found"));
    }
    @Query("SELECT r FROM Restaurant r JOIN FETCH r.dishes  d WHERE r.id=:id AND d.date = :date")
    Restaurant getWithDishesOnDate(int id, LocalDate date);

    @Query("SELECT r FROM Restaurant r JOIN r.dishes  d WHERE d.date  = :date ORDER BY r.id ASC")
    List<Restaurant> getRestaurantsOnDate(LocalDate date);
}
package com.abdddev.restaurantvoiting.repository;


import com.abdddev.restaurantvoiting.error.IllegalRequestDataException;
import com.abdddev.restaurantvoiting.model.Dish;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {
    @Query("SELECT d FROM Dish d WHERE d.restaurant.id=:restaurantId and d.id=:id ")
    Optional<Dish> get(int restaurantId, int id);

    @Query("SELECT d FROM Dish d WHERE d.restaurant.id=:restaurantId")
    List<Dish> getAllByRestaurant(int restaurantId);

    @Query("SELECT d FROM Dish d WHERE d.restaurant.id=:restaurantId AND d.date=:date")
    List<Dish> getAllByRestaurantAndDate(int restaurantId, LocalDate date);

    default Dish checkBelong(int restaurantId, int id) {
        return get(restaurantId, id).orElseThrow(
                () -> new IllegalRequestDataException("Dish id=" + id + " doesn't belong to Restaurant id=" + restaurantId));
    }
}
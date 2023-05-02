package com.abdddev.restaurantvoiting.util;

import com.abdddev.restaurantvoiting.model.Restaurant;
import com.abdddev.restaurantvoiting.to.RestaurantTo;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class RestaurantUtil {
    public static List<RestaurantTo> convertToTo(List<Restaurant> restaurants) {
        return restaurants.stream()
                .map(RestaurantTo::new)
                .toList();
    }

    public static RestaurantTo convertToTo(Restaurant restaurant) {
        return new RestaurantTo(restaurant);
    }
}
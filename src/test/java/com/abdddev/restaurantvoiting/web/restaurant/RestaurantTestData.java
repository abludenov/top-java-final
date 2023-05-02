package com.abdddev.restaurantvoiting.web.restaurant;

import com.abdddev.restaurantvoiting.model.Restaurant;
import com.abdddev.restaurantvoiting.to.RestaurantTo;
import com.abdddev.restaurantvoiting.web.MatcherFactory;

import java.util.List;

import static com.abdddev.restaurantvoiting.web.dish.DishTestData.*;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "votes");
    public static final MatcherFactory.Matcher<RestaurantTo> RESTAURANT_TO_MATCHER = MatcherFactory.usingEqualsComparator(RestaurantTo.class);

    public static final int RESTAURANT1_ID = 1;
    public static final String NOTFOUND_ID = "100";

    public static final Restaurant RESTAURANT_1 = new Restaurant(RESTAURANT1_ID, "Astoria", "Russian kitchen.", DISHES);
    public static final Restaurant RESTAURANT_2 = new Restaurant(RESTAURANT1_ID + 1, "Prival", "Asia kitchen.", DISHES2);
    public static final Restaurant RESTAURANT_3 = new Restaurant(RESTAURANT1_ID + 2, "Ohotnik", "European kitchen.", DISHES3);

    public static final RestaurantTo RESTAURANT_TO_1 = new RestaurantTo(RESTAURANT1_ID, "Astoria", "Russian kitchen.");
    public static final RestaurantTo RESTAURANT_TO_2 = new RestaurantTo(RESTAURANT1_ID + 1, "Prival", "Asia kitchen.");
    public static final RestaurantTo RESTAURANT_TO_3 = new RestaurantTo(RESTAURANT1_ID + 2, "Ohotnik", "European kitchen.");

    public static final List<RestaurantTo> RESTAURANTS_TO = List.of(RESTAURANT_TO_1, RESTAURANT_TO_2, RESTAURANT_TO_3);

    public static Restaurant getNew() {
        return new Restaurant(null, "Новый ресторан", "description", null);
    }

    public static Restaurant getUpdated() {
        return new Restaurant(RESTAURANT1_ID, "Updated", "description", null);
    }
}

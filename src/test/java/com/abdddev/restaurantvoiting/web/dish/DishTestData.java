package com.abdddev.restaurantvoiting.web.dish;

import com.abdddev.restaurantvoiting.model.Dish;
import com.abdddev.restaurantvoiting.web.MatcherFactory;

import java.time.LocalDate;
import java.util.List;

import static com.abdddev.restaurantvoiting.web.restaurant.RestaurantTestData.RESTAURANT_1;

public class DishTestData {
    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Dish.class, "restaurant");

    public static final int DISH1_ID = 1;

    public static final Dish DISH_1 = new Dish(DISH1_ID, "Борщ Русский", LocalDate.now(), 100.0);
    public static final Dish DISH_2 = new Dish(DISH1_ID + 1, "Суп Харчо", LocalDate.now(), 120.0);
    public static final Dish DISH_3 = new Dish(DISH1_ID + 2, "Рис отварной", LocalDate.now(), 50.0);
    public static final Dish DISH_4 = new Dish(DISH1_ID + 3, "Спагетти", LocalDate.now(), 130.0);
    public static final Dish DISH_5 = new Dish(DISH1_ID + 4, "Гуляш говяжий", LocalDate.now(), 200.0);
    public static final Dish DISH_6 = new Dish(DISH1_ID + 5, "Хлеб", LocalDate.now(), 15.0);
    public static final Dish DISH_7 = new Dish(DISH1_ID + 6, "Чай с лимоном", LocalDate.now(), 60.0);
    public static final Dish DISH_20 = new Dish(DISH1_ID + 19, "Макароны \"по флотски\"", LocalDate.of(2023, 1, 22), 130.0);

    public static final Dish DISH_8 = new Dish(DISH1_ID + 7, "Суп гороховый", LocalDate.now(), 100.0);
    public static final Dish DISH_9 = new Dish(DISH1_ID + 8, "Рис отварной", LocalDate.now(), 50.0);
    public static final Dish DISH_10 = new Dish(DISH1_ID + 9, "Спагетти", LocalDate.now(), 130.0);
    public static final Dish DISH_11 = new Dish(DISH1_ID + 10, "Гуляш Свиной", LocalDate.now(), 180.0);
    public static final Dish DISH_12 = new Dish(DISH1_ID + 11, "Хлеб", LocalDate.now(), 15.0);
    public static final Dish DISH_13 = new Dish(DISH1_ID + 12, "Капучино", LocalDate.now(), 100.0);

    public static final Dish DISH_14 = new Dish(DISH1_ID + 13, "Суп \"Весенний\"", LocalDate.now(), 140.0);
    public static final Dish DISH_15 = new Dish(DISH1_ID + 14, "Гречка", LocalDate.now(), 50.0);
    public static final Dish DISH_16 = new Dish(DISH1_ID + 15, "Салат \"Цезарь\"", LocalDate.now(), 130.0);
    public static final Dish DISH_17 = new Dish(DISH1_ID + 16, "Гуляш Свиной", LocalDate.now(), 180.0);
    public static final Dish DISH_18 = new Dish(DISH1_ID + 17, "Хлеб", LocalDate.now(), 15.0);
    public static final Dish DISH_19 = new Dish(DISH1_ID + 18, "Компот", LocalDate.now(), 60.0);

    public static final List<Dish> DISHES = List.of(DISH_1, DISH_2, DISH_3, DISH_4, DISH_5, DISH_6, DISH_7, DISH_20);
    public static final List<Dish> DISHES2 = List.of(DISH_8, DISH_9, DISH_10, DISH_11, DISH_12, DISH_13);
    public static final List<Dish> DISHES3 = List.of(DISH_14, DISH_15, DISH_16, DISH_17, DISH_18, DISH_19);

    public static Dish getNew() {
        return new Dish(null, "Новое блюдо", LocalDate.now(), 1.0);
    }

    public static Dish getUpdated() {
        return new Dish(DISH1_ID, "Новый борщ", LocalDate.now(), 120.0, RESTAURANT_1);
    }
}

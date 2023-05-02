package com.abdddev.restaurantvoiting.web.dish;

import com.abdddev.restaurantvoiting.model.Dish;
import com.abdddev.restaurantvoiting.repository.DishRepository;
import com.abdddev.restaurantvoiting.repository.RestaurantRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static com.abdddev.restaurantvoiting.util.validation.ValidationUtil.*;

@Tag(name = "Admin Dishes Controller", description = "Admin management of restaurant dishes")
@RestController
@RequestMapping(value = DishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class DishController {

    private final RestaurantRepository restaurantRepository;
    private final DishRepository dishRepository;
    static final String REST_URL = "/api/admin/restaurants/{restaurantId}/dishes";

    @Operation(summary = "Get Dish by id")
    @GetMapping("/{id}")
    public Dish get(@PathVariable int restaurantId, @PathVariable int id) {
        return dishRepository.checkBelong(restaurantId, id);
    }

    @Transactional
    @Operation(summary = "Delete Dish by id")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int restaurantId, @PathVariable int id) {
        dishRepository.delete(dishRepository.checkBelong(restaurantId, id).id());
    }

    @Operation(summary = "Get all Dishes for Restaurant")
    @GetMapping
    public List<Dish> getAllByRestaurant(@PathVariable int restaurantId) {
        return dishRepository.getAllByRestaurant(restaurantId);
    }

    @Operation(summary = "Get all Dishes for Restaurant on date")
    @GetMapping("/filter")
    public List<Dish> getAllByRestaurantAndDate(@PathVariable int restaurantId,
                                                @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return dishRepository.getAllByRestaurantAndDate(restaurantId, date);
    }

    @Transactional
    @Operation(summary = "Update Dish by id")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Dish dish, @PathVariable int restaurantId, @PathVariable int id) {
        assureIdConsistent(dish, id);
        dishRepository.checkBelong(restaurantId, id);
        dish.setRestaurant(restaurantRepository.getReferenceById(restaurantId));
        dishRepository.save(dish);
    }

    @Transactional
    @Operation(summary = "Create new Dish for Restaurant")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> createWithLocation(@Valid @RequestBody Dish dish, @PathVariable int restaurantId) {
        checkNew(dish);
        dish.setRestaurant(restaurantRepository.get(restaurantId));
        Dish created = dishRepository.save(dish);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL.replace("{restaurantId}", String.valueOf(restaurantId))).build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}
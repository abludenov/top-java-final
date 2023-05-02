package com.abdddev.restaurantvoiting.web.vote;

import com.abdddev.restaurantvoiting.model.Restaurant;
import com.abdddev.restaurantvoiting.model.Vote;
import com.abdddev.restaurantvoiting.repository.RestaurantRepository;
import com.abdddev.restaurantvoiting.repository.VoteRepository;
import com.abdddev.restaurantvoiting.service.VoteService;
import com.abdddev.restaurantvoiting.to.RestaurantTo;
import com.abdddev.restaurantvoiting.web.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static com.abdddev.restaurantvoiting.util.RestaurantUtil.convertToTo;
import static com.abdddev.restaurantvoiting.util.validation.ValidationUtil.assureIdConsistent;
import static com.abdddev.restaurantvoiting.web.vote.VoteController.REST_URL;

@Tag(
        name = "Vote Controller",
        description = "Allows authenticated users to view restaurants with menus and vote counts, and cast votes")
@Slf4j
@RestController
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class VoteController {
    static final String REST_URL = "/api/voting";
    private final VoteRepository voteRepository;
    private final VoteService voteService;
    private final RestaurantRepository restaurantRepository;

    @Operation(
            summary = "Get all Restaurants with today's menu",
            description = "Returns all Restaurants with today's menu")
    @GetMapping
    public List<RestaurantTo> getRestaurants() {
        log.info("Get restaurants for voting");
        return convertToTo(restaurantRepository.getRestaurantsOnDate(LocalDate.now()));
    }

    @Operation(
            summary = "Get Restaurant by id",
            description = "Returns Restaurant with dishes")
    @GetMapping("/restaurants/{restaurantId}")
    public Restaurant getWithDishes(@PathVariable int restaurantId) {
        log.info("Get restaurant with dishes{}", restaurantId);
        return restaurantRepository.getWithDishesOnDate(restaurantId, LocalDate.now());
    }

    @Operation(
            summary = "Cast first Vote",
            description = "Returns Vote with location")
    @PostMapping(value = "/restaurants/{restaurantId}")
    @Transactional
    public ResponseEntity<Vote> create(@PathVariable int restaurantId, @AuthenticationPrincipal AuthUser authUser) {
        log.info("Cast Vote");
        Vote created = voteRepository.save(new Vote(LocalDate.now(), restaurantId, authUser.id()));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/votes-today/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Operation(
            summary = "Get today's votes",
            description = "Returns today's votes")
    @GetMapping("/votes-today")
    public List<Vote> getAllToday() {
        log.info("Get today's votes");
        return voteRepository.findAllByDate(LocalDate.now());
    }

    @Operation(
            summary = "Get vote by id",
            description = "Returns vote by id")
    @GetMapping("/votes-today/{id}")
    public Vote get(@PathVariable int id) {
        log.info("Get vote by id: {}", id);
        return voteRepository.get(id);
    }

    @Operation(
            summary = "Change vote for authenticated user",
            description = "Returns status 204")
    @PutMapping(value = "/votes-today/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void change(@Valid @RequestBody Vote vote, @PathVariable int id, @AuthenticationPrincipal AuthUser authUser) {
        log.info("Attempt to change vote by user id: {}", authUser.id());
        assureIdConsistent(vote, id);
        voteRepository.checkBelong(vote.id(), authUser.id());
        voteService.update(vote);
    }
}
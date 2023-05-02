package com.abdddev.restaurantvoiting.web.vote;

import com.abdddev.restaurantvoiting.model.Vote;
import com.abdddev.restaurantvoiting.repository.VoteRepository;
import com.abdddev.restaurantvoiting.service.VoteService;
import com.abdddev.restaurantvoiting.util.JsonUtil;
import com.abdddev.restaurantvoiting.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalTime;

import static com.abdddev.restaurantvoiting.web.restaurant.RestaurantTestData.*;
import static com.abdddev.restaurantvoiting.web.user.UserTestData.USER2_MAIL;
import static com.abdddev.restaurantvoiting.web.user.UserTestData.USER_MAIL;
import static com.abdddev.restaurantvoiting.web.vote.VoteController.REST_URL;
import static com.abdddev.restaurantvoiting.web.vote.VoteTestData.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VoteControllerTest extends AbstractControllerTest {

    private static final String REST_URL_SLASH = REST_URL + '/';

    @Autowired
    private VoteRepository voteRepository;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getRestaurants() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TO_MATCHER.contentJson(RESTAURANT_TO_1, RESTAURANT_TO_2, RESTAURANT_TO_3));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getWithDishes() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + "restaurants/" + RESTAURANT_2.id()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(RESTAURANT_2));
    }

    @Test
    @WithUserDetails(value = USER2_MAIL)
    void create() throws Exception {
        Vote newVote = USER2_VOTE;
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL_SLASH + "restaurants/" + RESTAURANT1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(null)));

        Vote created = VOTE_MATCHER.readFromJson(action);
        int newId = created.id();
        newVote.setId(newId);
        VOTE_MATCHER.assertMatch(created, newVote);
        VOTE_MATCHER.assertMatch(voteRepository.getExisted(newId), newVote);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAllToday() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + "votes-today"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(VOTE_1, VOTE_2, VOTE_3));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + "votes-today/" + VOTE_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(VOTE_1));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void change() throws Exception {
        VoteService.votingStopTime = LocalTime.now().plusMinutes(10);
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + "votes-today/" + VOTE_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(VoteTestData.getUpdated())))
                .andExpect(status().isNoContent());

        VOTE_MATCHER.assertMatch(voteRepository.get(VOTE_ID), VoteTestData.getUpdated());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void voteAfterStop() throws Exception {
        VoteService.votingStopTime = LocalTime.now().minusMinutes(10);
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + "votes-today/" + VOTE_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(VoteTestData.getUpdated())))
                .andExpect(status().isConflict());
    }
}
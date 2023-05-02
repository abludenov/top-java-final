package com.abdddev.restaurantvoiting.service;

import com.abdddev.restaurantvoiting.error.DataConflictException;
import com.abdddev.restaurantvoiting.model.Vote;
import com.abdddev.restaurantvoiting.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
@AllArgsConstructor
public class VoteService {
    public static LocalTime votingStopTime = LocalTime.of(11, 0);// 11:00 The time of stopping voting today
    private final VoteRepository voteRepository;

    public void update(Vote vote) {
        if (LocalTime.now().isAfter(votingStopTime)) {
            throw new DataConflictException("You can`t change your vote after 11:00");
        }
        voteRepository.save(vote);
    }
}
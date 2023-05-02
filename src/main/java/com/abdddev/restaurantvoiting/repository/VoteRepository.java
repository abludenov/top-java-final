package com.abdddev.restaurantvoiting.repository;

import com.abdddev.restaurantvoiting.error.IllegalRequestDataException;
import com.abdddev.restaurantvoiting.model.Vote;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {
    @Query("SELECT v FROM Vote v WHERE v.userId=:userId and v.id=:id ")
    Optional<Vote> get(int id, int userId);

    @Query("SELECT v FROM Vote v WHERE v.date = :date")
    List<Vote> findAllByDate(LocalDate date);

    default Vote checkBelong(int id, int userId) {
        return get(id, userId).orElseThrow(
                () -> new IllegalRequestDataException("Vote id=" + id + " doesn't belong to User id=" + userId));
    }
}
package com.abdddev.restaurantvoiting.model;

import com.abdddev.restaurantvoiting.model.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "vote", uniqueConstraints = {@UniqueConstraint(columnNames = {"vote_date", "user_id"}, name = "vote_unique_idx")})
@Getter
@Setter
@NoArgsConstructor
public class Vote extends BaseEntity {
    @Column(name = "vote_date", nullable = false)
    @NotNull
    private LocalDate date;

    @Column(name = "restaurant_id", nullable = false)
    private Integer restaurantId;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    public Vote(Integer id, LocalDate date, Integer restaurantId, Integer userId) {
        super(id);
        this.date = date;
        this.restaurantId = restaurantId;
        this.userId = userId;
    }

    public Vote(LocalDate date, Integer restaurantId, Integer userId) {
        this.date = date;
        this.restaurantId = restaurantId;
        this.userId = userId;
    }
}
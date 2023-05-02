package com.abdddev.restaurantvoiting.model;

import com.abdddev.restaurantvoiting.model.base.NamedEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
//TODO дописать
@Table(name = "dish", uniqueConstraints = {
        @UniqueConstraint(name = "dish_unique_idx", columnNames = {"name", "dish_date", "restaurant_id"})
})
@Getter
@Setter
@NoArgsConstructor
public class Dish extends NamedEntity {
    @Column(name = "dish_date", nullable = false)
    @NotNull
    private LocalDate date;

    @Column(name = "price", nullable = false)
    @NotNull
    @Min(value = 1)
    private double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @JsonIgnore
    private Restaurant restaurant;

    public Dish(Integer id, String name, LocalDate date, Double price) {
        super(id, name);
        this.date = date;
        this.price = price;
    }

    public Dish(Integer id, String name, LocalDate date, double price, Restaurant restaurant) {
        super(id, name);
        this.date = date;
        this.price = price;
        this.restaurant = restaurant;
    }
}
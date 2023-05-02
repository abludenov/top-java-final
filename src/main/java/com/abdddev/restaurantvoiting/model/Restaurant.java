package com.abdddev.restaurantvoiting.model;

import com.abdddev.restaurantvoiting.model.base.NamedEntity;
import com.abdddev.restaurantvoiting.util.validation.NoHtml;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Entity
@Table(name = "restaurant", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"}, name = "restaurant_unique_idx")})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Restaurant extends NamedEntity {
    @NotBlank
    @Size(min = 2, max = 128)
    @Column(name = "description", nullable = false)
    @NoHtml
    protected String description;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @Schema(hidden = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Dish> dishes;

    public Restaurant(Integer id, String name, String description, List<Dish> dishes) {
        super(id, name);
        this.description = description;
        this.dishes = dishes;
    }
}
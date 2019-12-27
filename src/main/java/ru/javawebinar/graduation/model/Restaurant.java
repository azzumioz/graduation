package ru.javawebinar.graduation.model;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import java.util.List;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@NamedEntityGraph(name = Restaurant.GRAPH_WITH_DISHES, attributeNodes =
        {
                @NamedAttributeNode("dishes")
        })
@Table(name = "restaurant")
public class Restaurant extends AbstractNamedEntity {

    public static final String GRAPH_WITH_DISHES = "Restaurant.withDishes";

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    private List<Dish> dishes;

    public Restaurant() {
    }

    public Restaurant(String name) {
        this(null, name);
    }

    public Restaurant(Integer id, String name) {
        super(id, name);
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name +
                '}';
    }
}

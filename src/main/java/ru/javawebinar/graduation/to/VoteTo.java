package ru.javawebinar.graduation.to;

import ru.javawebinar.graduation.model.Vote;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class VoteTo extends BaseTo {

    private LocalDateTime dateTime;

    @NotNull
    private Integer restaurantId;

    public VoteTo() {
    }

    public VoteTo(LocalDateTime dateTime, Integer restaurantId) {
        this(null, dateTime, restaurantId);
    }

    public VoteTo(Integer id, LocalDateTime dateTime, Integer restaurantId) {
        super(id);
        this.dateTime = dateTime;
        this.restaurantId = restaurantId;
    }

    public VoteTo(Vote vote) {
        super(vote.getId());
        this.dateTime = (vote.getDateTime());
        this.restaurantId = vote.getRestaurant().getId();
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VoteTo voteTo = (VoteTo) o;

        if (!dateTime.equals(voteTo.dateTime)) return false;
        if (!restaurantId.equals(voteTo.restaurantId)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = dateTime.hashCode();
        result = 31 * result + restaurantId.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "VoteTo{" +
                "dateTime=" + dateTime +
                ", restaurantId=" + restaurantId +
                '}';
    }
}

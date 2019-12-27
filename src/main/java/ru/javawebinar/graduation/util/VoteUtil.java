package ru.javawebinar.graduation.util;

import ru.javawebinar.graduation.model.Vote;
import ru.javawebinar.graduation.to.VoteTo;

import java.util.List;
import java.util.stream.Collectors;

public class VoteUtil {

    public static VoteTo asTo(Vote vote) {
        return new VoteTo(vote);
    }

    public static List<VoteTo> asTo(List<Vote> votes) {
        return votes.stream().map(VoteTo::new).collect(Collectors.toList());
    }
}

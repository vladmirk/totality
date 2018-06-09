package com.vk.totality.game;

import com.vk.totality.user.User;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface UserTournamentRepository extends PagingAndSortingRepository<UserTournament, Long> {
    List<UserTournament> findUserTournamentByUser(User user);
}

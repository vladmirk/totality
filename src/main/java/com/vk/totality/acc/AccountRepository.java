package com.vk.totality.acc;

import com.vk.totality.game.UserTournament;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface AccountRepository extends PagingAndSortingRepository<Account, Long> {

    List<Account> findAccountByUserTournamentOrderByEventDateDesc(UserTournament ut);

}

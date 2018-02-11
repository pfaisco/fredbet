package de.fred4jupiter.fredbet.web.bet;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.fred4jupiter.fredbet.domain.Bet;
import de.fred4jupiter.fredbet.domain.Match;
import de.fred4jupiter.fredbet.service.BettingService;
import de.fred4jupiter.fredbet.service.MatchService;
import de.fred4jupiter.fredbet.web.WebMessageUtil;

@Component
public class AllBetsCommandMapper {

	private static final Logger LOG = LoggerFactory.getLogger(AllBetsCommandMapper.class);

	@Autowired
	private MatchService matchService;

	@Autowired
	private BettingService bettingService;

	@Autowired
	private WebMessageUtil messageUtil;

	public AllBetsCommand findAllBetsForMatchId(final Long matchId) {
		if (matchId == null) {
			return null;
		}
		Match match = matchService.findMatchById(matchId);
		if (match == null) {
			LOG.warn("Match with matchId={} could not be found!", matchId);
			return null;
		}

		List<Bet> filtered = bettingService.findAllBetsForMatchId(matchId);
		return new AllBetsCommand(filtered, match, messageUtil);
	}

}

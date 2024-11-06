package service;

import customExceptions.InsufficientCashException;
import customExceptions.InsufficientQuantityException;
import customExceptions.InvalidTradeException;
import enums.Status;
import model.Response;
import model.Trade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import portfolio.Portfolio;
import repo.TradeRepository;
import utils.TradeValidator;

import java.util.Optional;

@Service
public class TradeService {

    @Autowired
    TradeRepository tradeRepository;

    @Autowired
    Portfolio portfolio;

    public Response createOrUpdateTrade(Trade trade) throws InvalidTradeException, InsufficientCashException, InsufficientQuantityException {

        if(!TradeValidator.isTradeValid(trade))
            throw new InvalidTradeException("Invalid Trade");

        Integer tradeId;
        if(trade.getTradeId()==null) {
            tradeId = createNewTrade(trade);

        }
        else {
            tradeId = updateTrade(trade);
        }

        return new Response(tradeId, Status.ACCEPTED);
    }

    private Integer updateTrade(Trade trade) throws InvalidTradeException {
        Trade oldTrade = tradeRepository.findById(trade.getTradeId()).orElseThrow(() -> new InvalidTradeException(" "));
        trade = tradeRepository.save(trade);
       return trade.getTradeId();
    }

    private Integer createNewTrade(Trade trade) throws InsufficientQuantityException, InsufficientCashException {


        portfolio.updatePortfolio(trade);
        trade = tradeRepository.save(trade);

        return trade.getTradeId();
    }
}

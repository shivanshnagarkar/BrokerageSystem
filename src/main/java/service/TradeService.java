package service;

import customExceptions.InsufficientCashException;
import customExceptions.InsufficientQuantityException;
import customExceptions.InvalidTradeException;
import enums.Status;
import enums.Type;
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

    private Integer updateTrade(Trade trade) throws InvalidTradeException, InsufficientCashException, InsufficientQuantityException {
        Trade oldTrade = tradeRepository.findById(trade.getTradeId()).orElseThrow(() -> new InvalidTradeException("Trade not present"));
        oldTrade.setSharePrice(trade.getSharePrice());
        oldTrade.setShareQuantity(trade.getShareQuantity());
        oldTrade.setTradeType(Type.UPDATE);
        int delta = oldTrade.getShareQuantity() - trade.getShareQuantity();
        portfolio.updatePortfolio(oldTrade, delta);
        trade = tradeRepository.save(oldTrade);
       return trade.getTradeId();
    }

    private Integer createNewTrade(Trade trade) throws InsufficientQuantityException, InsufficientCashException {


        portfolio.updatePortfolio(trade ,0);
        trade = tradeRepository.save(trade);

        return trade.getTradeId();
    }
}

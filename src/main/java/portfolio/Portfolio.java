package portfolio;

import customExceptions.InsufficientCashException;
import customExceptions.InsufficientQuantityException;
import model.Trade;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Portfolio {

    private volatile double cash;
    ConcurrentHashMap<String , Integer> holdings;

    public Portfolio(int cash) {
        this.cash = cash;
        this.holdings = new ConcurrentHashMap<>();
    }

    public synchronized void buy(String shareName, int quantity, Double price) throws InsufficientCashException {
        double requiredCash = price*quantity;
        if(cash>=requiredCash) {
            holdings.put(shareName, holdings.getOrDefault(shareName, 0) + quantity);
            cash = cash = cash - requiredCash;
        }
        else
            throw new InsufficientCashException("Insufficient share quantity in portfolio");

    }

    public synchronized void sell(String shareName, int quantity, Double price) throws InsufficientQuantityException {
        if (holdings.containsKey(shareName)) {
            int current = holdings.get(shareName);
            if (current > quantity) {
                holdings.put(shareName, (current-quantity));
                cash = cash + (price*quantity);
            } else if (current == quantity) {
                holdings.remove(shareName);
                cash = cash + (price*quantity);
            } else {
                throw new InsufficientQuantityException("Insufficient share quantity in portfolio");
            }
        } else {
            throw new InsufficientQuantityException("Share not found in portfolio");
        }
    }
    public Map<String, Integer> getHoldings() {
        return holdings;
    }

    public synchronized void updatePortfolio(Trade trade) throws InsufficientQuantityException, InsufficientCashException {

        if(trade.getBuyOrSell()== 'B')
            buy(trade.getShareName(),trade.getShareQuantity(), trade.getSharePrice());
        else
            if(trade.getBuyOrSell()== 'S')
            sell(trade.getShareName(),trade.getShareQuantity(), trade.getSharePrice());



    }
}

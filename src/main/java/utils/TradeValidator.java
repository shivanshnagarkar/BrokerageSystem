package utils;

import model.Trade;

public class TradeValidator {

    public static boolean isTradeValid(Trade trade)
    {
        if(trade==null)
            return false;
        if(trade.getBuyOrSell()==(char) 0)
            return false;
        if(trade.getSharePrice()==null&&trade.getShareQuantity()==null)
            return false;

        return true;
    }
}

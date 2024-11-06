package controller;


import customExceptions.InsufficientCashException;
import customExceptions.InsufficientQuantityException;
import customExceptions.InvalidTradeException;
import enums.Status;
import model.Response;
import model.Trade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import service.TradeService;

@RestController()
public class TradeController {

    @Autowired
    TradeService tradeService;

    @PostMapping("/create")
    public ResponseEntity<Response> createOrUpdateTrade(@RequestBody Trade trade)  {
        Response response = null;
        try {
             response = tradeService.createOrUpdateTrade(trade);
        } catch (InvalidTradeException e) {
             response = new Response(trade.getTradeId()==null?-1:trade.getTradeId(), Status.REJECTED);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        } catch (InsufficientCashException e) {
            e.printStackTrace();
        } catch (InsufficientQuantityException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }



}

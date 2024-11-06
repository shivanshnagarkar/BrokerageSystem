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
import org.springframework.web.bind.annotation.*;
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
            response = new Response(trade.getTradeId()==null?-1:trade.getTradeId(), Status.REJECTED);
            return new ResponseEntity<Response>(response, HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (InsufficientQuantityException e) {
            response = new Response(trade.getTradeId()==null?-1:trade.getTradeId(), Status.REJECTED);
            return new ResponseEntity<Response>(response, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }

    @PutMapping("/create/{id}")
    public ResponseEntity<Response> createOrUpdateTrade(@RequestBody Trade trade, @PathVariable Integer tradeId)  {
        Response response = null;
        try {
            response = tradeService.createOrUpdateTrade(trade);
        } catch (InvalidTradeException e) {
            response = new Response(tradeId, Status.REJECTED);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        } catch (InsufficientCashException e) {
            response = new Response(tradeId, Status.REJECTED);
            return new ResponseEntity<Response>(response, HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (InsufficientQuantityException e) {
            response = new Response(tradeId, Status.REJECTED);
            return new ResponseEntity<Response>(response, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }



}

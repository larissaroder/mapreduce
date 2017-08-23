package br.com.db1.controller;


import br.com.db1.model.Money;
import br.com.db1.services.MoneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/application")
public class ApplicationController {

    @Autowired
    private MoneyService moneyService;

    @RequestMapping(value = "/max-value", method = RequestMethod.GET)
    public ResponseEntity<?> getMaxValue() {
        try {
            List<Money> moneys = moneyService.getMoneys();
            Money maxValue = moneyService.getMaxValue(moneys);
            return new ResponseEntity<>(maxValue, HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }

    }

    @RequestMapping(value = "/specific-value", method = RequestMethod.GET)
    public ResponseEntity<?> getMinValue() {
        try {
            List<Money> moneys = moneyService.getMoneys();
            List<Money> specificValue = moneyService.getValueBiggerThreeHundred(moneys);
            return new ResponseEntity<>(specificValue, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }


}

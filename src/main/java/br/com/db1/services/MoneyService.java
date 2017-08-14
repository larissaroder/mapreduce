package br.com.db1.services;

import br.com.db1.infrastructure.Calculate;
import br.com.db1.model.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

@Service
public class MoneyService {

    @Autowired
    private Calculate calculate;

    private static final String URL = "http://localhost:8080/application";

    public List<Money> getMoneys() {

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Money>> moneyResponse =
                restTemplate.exchange(URL,
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Money>>() {
                        });
        return moneyResponse.getBody();
    }

    public BigDecimal getTotalValue (List<Money> moneys) {
        return calculate.totalValue(moneys);
    }

    public String getAvarage (List<Money> moneys) {
        return  calculate.avarage(moneys);
    }

    public String getMaxValue (List<Money> moneys) {
        return  calculate.maxValue(moneys);
    }

    public String getMinValue (List<Money> moneys) {
        return  calculate.minValue(moneys);
    }
}

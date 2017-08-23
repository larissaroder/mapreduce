package br.com.db1.infrastructure;

import br.com.db1.model.Money;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;

@Component
public class Calculate {

    private static final String NULL_VALUES = "Existe valores nulos";
    public static final int DECIMAL_SCALE = 2;
    private static DecimalFormat decimalFormat = new DecimalFormat("0.##");

    protected Calculate() {
        // To Framework
    }

    public Money maxValue(List<Money> moneys) {
        return moneys.stream()
                .parallel()
                .reduce(getAccumulator()).orElseThrow(getIllegalArgumentException());
    }

    public List<Money> getValueBiggerThreeHundred(List<Money> moneys) {

        List<Money> valorInicial = new ArrayList<>();
        return moneys.stream()
                .parallel()
                .reduce(valorInicial, this::getMoniesValueBiggerThreeHundred
                        , this::addMoniesValueBiggerThreeHundred);
    }

    private List<Money> addMoniesValueBiggerThreeHundred(List<Money> monies1, List<Money> monies2) {
        monies1.addAll(monies2);
        return monies1;
    }

    private List<Money> getMoniesValueBiggerThreeHundred(List<Money> monies1, Money money) {
        if (money.getValue().doubleValue() > 300.00) {
            monies1.add(money);
        }
        return monies1;
    }

    private BinaryOperator<Money> getAccumulator() {
        return (money, money2) -> {
            if (money.getValue().compareTo(money2.getValue()) == 1) {
                return money;
            }
            return money2;
        };
    }

    private Supplier<IllegalArgumentException> getIllegalArgumentException() {
        return () -> new IllegalArgumentException(NULL_VALUES);
    }

}

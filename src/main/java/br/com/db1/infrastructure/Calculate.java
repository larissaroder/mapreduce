package br.com.db1.infrastructure;

import br.com.db1.model.Money;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;

@Component
public class Calculate {

    private static final String NULL_VALUES = "Existe valores nulos";
    public static final int DECIMAL_SCALE = 2;
    private static DecimalFormat decimalFormat = new DecimalFormat("0.##");

    protected Calculate () {
        // To Framework
    }

    public BigDecimal totalValue(List<Money> moneys) {
        return moneys.stream()
                .parallel()
                .map(Money::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(DECIMAL_SCALE);
    }

    public String avarage(List<Money> moneys) {
        double value = moneys.stream()
                .parallel()
                .map(Money::getValue)
                .mapToDouble(BigDecimal::doubleValue)
                .average().orElseThrow(getIllegalArgumentException());
        return decimalFormat.format(value);
    }

    public String maxValue(List<Money> moneys) {
        BigDecimal value = moneys.stream()
                .parallel()
                .map(Money::getValue)
                .max(Comparator.naturalOrder())
                .orElseThrow(getIllegalArgumentException());
        return decimalFormat.format(value);
    }

    public String minValue(List<Money> moneys) {
        BigDecimal value = moneys.stream()
                .parallel()
                .map(Money::getValue)
                .min(Comparator.naturalOrder())
                .orElseThrow(getIllegalArgumentException());
        return decimalFormat.format(value);
    }

    private Supplier<IllegalArgumentException> getIllegalArgumentException() {
        return () -> new IllegalArgumentException(NULL_VALUES);
    }

}

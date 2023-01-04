package com.example.cardealer.constatnt;

import java.math.BigDecimal;

public enum Discounts {
    ZERO(BigDecimal.valueOf(0)),
    FIVE(BigDecimal.valueOf(5)),
    TEN(BigDecimal.valueOf(10)),
    FIFTEEN(BigDecimal.valueOf(15)),
    TWENTY(BigDecimal.valueOf(20)),
    THIRTY(BigDecimal.valueOf(30)),
    FORTY(BigDecimal.valueOf(40)),
    FIFTY(BigDecimal.valueOf(50));

    private final BigDecimal decimal;

    Discounts(BigDecimal decimal) {
        this.decimal = decimal;
    }

    public static int getSize() {
        return values().length;
    }

    public BigDecimal getDecimal() {
        return this.decimal;
    }
}

package com.pfe.pfekacemjwt.entitiy;

import java.time.YearMonth;

public class RevenueCount {
    private YearMonth yearMonth;
    private Double count;

    public RevenueCount(YearMonth yearMonth, Double count) {
        this.yearMonth = yearMonth;
        this.count = count;
    }

    public YearMonth getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(YearMonth yearMonth) {
        this.yearMonth = yearMonth;
    }

    public Double getCount() {
        return count;
    }

    public void setCount(Double count) {
        this.count = count;
    }
}

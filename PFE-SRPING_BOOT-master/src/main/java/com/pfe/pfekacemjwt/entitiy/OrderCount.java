package com.pfe.pfekacemjwt.entitiy;

import java.time.YearMonth;

public class OrderCount {

    private YearMonth yearMonth;
    private Long count;

    public OrderCount(YearMonth yearMonth, Long count) {
        this.yearMonth = yearMonth;
        this.count = count;
    }

    public YearMonth getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(YearMonth yearMonth) {
        this.yearMonth = yearMonth;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}

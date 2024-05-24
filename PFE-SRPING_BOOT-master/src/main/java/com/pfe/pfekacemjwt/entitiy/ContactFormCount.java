package com.pfe.pfekacemjwt.entitiy;

import java.time.YearMonth;

public class ContactFormCount {
    private YearMonth month;
    private Long count;

    public ContactFormCount(YearMonth month, Long count) {
        this.month = month;
        this.count = count;
    }

    // Getters and Setters
    public YearMonth getMonth() {
        return month;
    }

    public void setMonth(YearMonth month) {
        this.month = month;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

}

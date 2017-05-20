package com.study.java.design.test;

/**
 * @author Jeffrey
 * @since 2017/03/24 10:39
 */
public class LoanApplication {

    private final Criteria identity;
    private final Criteria creditHistory;
    private final Criteria incomeHistory;

    public LoanApplication(Criteria identity, Criteria creditHistory, Criteria incomeHistory) {
        this.identity = identity;
        this.creditHistory = creditHistory;
        this.incomeHistory = incomeHistory;
    }

    public void check() {
        identity.check();
        creditHistory.check();
        incomeHistory.check();
    }
}

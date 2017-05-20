package com.study.java.design.test;

/**
 * @author Jeffrey
 * @since 2017/03/24 10:43
 */
public class CompanyLoanApplication extends LoanApplication {

    public CompanyLoanApplication(Company company) {
        super(company::checkIdentity, company::checkHistoricalDebt, company::checkProfitAndLoss);
    }

    public static void main(String[] args) {
        LoanApplication application = new CompanyLoanApplication(new Company());
        application.check();
    }
}

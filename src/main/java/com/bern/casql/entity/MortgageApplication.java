/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bern.casql.entity;

import java.math.BigInteger;
import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 *
 * @author unknown
 */
public class MortgageApplication extends BaseApplicationModel {

    @NotNull
    private String mortgageType;

    @Min(15)
    private int lengthOfMortgageYears;

    @NotNull
    private BigInteger loanAmount;

    public MortgageApplication() {
    }

    public MortgageApplication(String mortgageType, int lengthOfMortgageYears, BigInteger loanAmount, List<Borrower> borrowers) {
        this.mortgageType = mortgageType;
        this.lengthOfMortgageYears = lengthOfMortgageYears;
        this.loanAmount = loanAmount;
        this.borrowers = borrowers;
    }

    public String getMortgageType() {
        return mortgageType;
    }

    public void setMortgageType(String mortgageType) {
        this.mortgageType = mortgageType;
    }

    public int getLengthOfMortgageYears() {
        return lengthOfMortgageYears;
    }

    public void setLengthOfMortgageYears(int lengthOfMortgageYears) {
        this.lengthOfMortgageYears = lengthOfMortgageYears;
    }

    public BigInteger getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(BigInteger loanAmount) {
        this.loanAmount = loanAmount;
    }

    public List<Borrower> getBorrowers() {
        return borrowers;
    }

    public void setBorrowers(List<Borrower> borrowers) {
        this.borrowers = borrowers;
    }

}

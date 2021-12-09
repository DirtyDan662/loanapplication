/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bern.casql.entity;

import java.math.BigInteger;
import java.util.List;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

/**
 *
 * @author unknown
 */
public class CreditCardApplication extends BaseApplicationModel {

    private static final BigInteger MAX_CREDIT_LIMIT = BigInteger.valueOf(10000);

    private long id;

    @Max(10000)
    @NotNull
    private BigInteger requestedCreditLimit;

    @NotNull
    private String cardType;

    public CreditCardApplication(BigInteger requestedCreditLimit, String cardType, List<Borrower> borrowers) {
        this.requestedCreditLimit = requestedCreditLimit;
        this.cardType = cardType;
        this.borrowers = borrowers;
    }

    public CreditCardApplication() {
    }

    public BigInteger getRequestedCreditLimit() {
        return requestedCreditLimit;
    }

    public String getCardType() {
        return cardType;
    }

    public void setRequestedCreditLimit(BigInteger requestedCreditLimit) {
        this.requestedCreditLimit = requestedCreditLimit;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

}

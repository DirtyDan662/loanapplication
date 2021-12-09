/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bern.casql.entity;

import java.time.LocalDate;
import javax.validation.constraints.NotNull;

/**
 *
 * @author unknown
 */

public class Employment {

    private int id;
    @NotNull
    private LocalDate startDate, endDate;

   
    @NotNull
    private String employerName;
    @NotNull
    private long employerPhone;

    public Employment(LocalDate startDate, LocalDate endDate, String employerName, long employerPhone) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.employerName = employerName;
        this.employerPhone = employerPhone;
    }

    public Employment() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getEmployerName() {
        return employerName;
    }

    public void setEmployerName(String employerName) {
        this.employerName = employerName;
    }

    public long getEmployerPhone() {
        return employerPhone;
    }

    public void setEmployerPhone(long employerPhone) {
        this.employerPhone = employerPhone;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bern.casql.entity;

import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 *
 * @author unknown
 */

public class Borrower{

    private static final int AGE_OF_MATURITY = 18;

    private Long id;

    @NotNull
    private String firstName, lastName, address, city, state, relationship;

    
    @Min(Borrower.AGE_OF_MATURITY)
    private int age;
 
    private int zip, ssn;

    public Borrower(Long id, String firstName, String lastName, String address, String city, String state, String relationship, int age, int zip, int ssn, List<Employment> employments) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.state = state;
        this.relationship = relationship;
        this.age = age;
        this.zip = zip;
        this.ssn = ssn;
        this.employments = employments;
    }
    

    List<Employment> employments;

    public Borrower() {
    }

    public Borrower(String firstName, String lastName, String address, String city, String state, String relationship, int age, int zip, int ssn, List<Employment> employments) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.state = state;
        this.relationship = relationship;
        this.age = age;
        this.zip = zip;
        this.ssn = ssn;
        this.employments = employments;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getRelationship() {
        return relationship;
    }

    public int getAge() {
        return age;
    }

    public int getZip() {
        return zip;
    }

    public int getSsn() {
        return ssn;
    }

    public List<Employment> getEmployment() {
        return employments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setSsn(int ssn) {
        this.ssn = ssn;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public void setEmployments(List<Employment> employments) {
        this.employments = employments;
    }


   
}

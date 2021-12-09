/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bern.casql.boundary;

/**
 *
 * @author unknown
 */
public class ResponseBody {
    String message;
    String[] errorMessages;

    public ResponseBody(String message, String[] errorMessages) {
        this.message = message;
        this.errorMessages = errorMessages;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String[] getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(String[] errorMessages) {
        this.errorMessages = errorMessages;
    }
    
    public static ResponseBody successMessage(){
        return new ResponseBody("submission succeeded", null);
    }
    
    public static ResponseBody errorMessage(String[] messages){
        return new ResponseBody("submission failed", messages);
    }
    
    public static ResponseBody errorMessage(String mainMessage,String[] messages){
        return new ResponseBody(mainMessage, messages);
    }
    
}

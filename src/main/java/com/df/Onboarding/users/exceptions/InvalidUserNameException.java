package com.df.Onboarding.users.exceptions;

import org.springframework.stereotype.Controller;


public class InvalidUserNameException extends RuntimeException{
    String message;
    int id=1;
    public InvalidUserNameException(String message){
        super(message);
        this.message=message;
    }
    public InvalidUserNameException(){

    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString(){
        return "{" +
                "message:"+message+
                "}";
    }

}

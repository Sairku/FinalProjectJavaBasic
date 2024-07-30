package com.exeption;

public class MainMenuExeption extends RuntimeException{
    public MainMenuExeption (String message){
        super(message);
    }
}

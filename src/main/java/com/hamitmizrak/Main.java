package com.hamitmizrak;
import com.hamitmizrak.javase.service.RegisterLoginServices;

public class Main {
    public static void main(String[] args) {
        RegisterLoginServices services = new RegisterLoginServices();
        //services.register();
        services.login();
    }
}
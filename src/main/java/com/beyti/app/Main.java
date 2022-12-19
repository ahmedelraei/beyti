package com.beyti.app;

import com.beyti.utils.DB;

import javax.swing.*;

public class Main {
    public static void main(String[] args){
        DB.connect();
        JFrame frame = new LoginFrame("Login");
    }
}

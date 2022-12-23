package com.beyti.app;

import com.beyti.utils.DB;
import javax.swing.*;
import java.sql.Connection;

// @author: Ahmed Hatem Mohamed Elraei
// Class: 17
// ID: 12200207

// @author: Ahmed Abduljalil
// Class: 17
// ID: 12200550

// @author: Peter Halim
// Class: 17
// ID:

// @author: Ahmed Bahgat
// Class: 17
// ID:

// @author: Taha Mohsen
// Class: 17
// ID:

public class Main {
    public static void main(String[] args) {
        Connection conn = DB.connect();
        JFrame frame = new LoginFrame(conn, "Login");

    }
}

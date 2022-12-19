package com.beyti.utils;

import java.io.*;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DB {
    public static void connect() {
        Connection conn;
        Statement stmt;
        try{
            DriverManager.getConnection("jdbc:sqlserver://DESKTOP-RNQD9CI;Database=beytiDB;" +
                    "IntegratedSecurity=true;encrypt=true;trustServerCertificate=true;");

        } catch (SQLException e){
            if(e.getErrorCode() == 4060){
                try {
                    conn = DriverManager.getConnection("jdbc:sqlserver://DESKTOP-RNQD9CI;" +
                            "IntegratedSecurity=true;encrypt=true;trustServerCertificate=true;");
                    stmt = conn.createStatement();
                    stmt.execute("CREATE DATABASE beytiDB");
                    migrate(conn);
                    conn.commit();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            } else
                e.printStackTrace();
        }
        System.out.println("Connection Established!");
    }
    public static void executeSqlScript(Connection conn, File inputFile) throws SQLException {

        // Delimiter
        String delimiter = ";";

        // Create scanner
        Scanner scanner;
        try {
            scanner = new Scanner(inputFile).useDelimiter(delimiter);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
            return;
        }

        // Loop through the SQL file statements
        Statement currentStatement = null;
        while(scanner.hasNext()) {

            // Get statement
            String rawStatement = scanner.next();
            System.out.println(rawStatement);
            try {
                // Execute statement
                currentStatement = conn.createStatement();
                currentStatement.execute(rawStatement);
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                // Release resources
                if (currentStatement != null) {
                    try {
                        currentStatement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                currentStatement = null;
            }
        }
        scanner.close();
    }
    private static void migrate(Connection conn){
        try {

            File sqlScript = new File("sql/structure.sql").getAbsoluteFile();
            if(sqlScript.exists()){
//                executeSqlScript(conn, sqlScript);
//                conn.commit();
                SQLScriptRunner runner = new SQLScriptRunner(conn, true, true);
                try {
                    runner.runScript(new BufferedReader(new FileReader("sql/structure.sql")));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else throw new FileNotFoundException("Database structure SQL script not found");
        } catch (SQLException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

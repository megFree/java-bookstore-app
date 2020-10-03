package com.bookstore.ui.menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandListener {

    public int startListening() {
        while (true) {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                return Integer.parseInt(in.readLine());
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}

package com.booking.utils;

import com.booking.exception.MenuException;

import java.util.List;
import java.util.Scanner;

public class Menu {
    public static int chooseNumericMenu(List<String> menu, boolean withExit, String msg) throws MenuException {
        if (menu.isEmpty()) {
            throw new MenuException("Empty menu!");
        }

        Scanner input = new Scanner(System.in);

        System.out.print(msg);

        if (withExit) {
            System.out.println("0) Exit");
        }

        int start = withExit ? 0 : 1;
        int num = 1;

        for (String item : menu) {
            System.out.println(num + ") " + item);
            num++;
        }

        try {
            num = Integer.parseInt(input.nextLine());

            if (num < start || num > menu.size()) {
                throw new MenuException("You chose wrong menu item. Please try again.");
            } else {
                return num;
            }
        } catch (NumberFormatException e) {
            throw new MenuException("You chose wrong menu item. Please try again.");
        }
    }
}

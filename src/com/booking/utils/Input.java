package com.booking.utils;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Input {
    public static LocalDate enterDate(String msg, DateTimeFormatter dateFormat) {
        Scanner input = new Scanner(System.in);
        LocalDate date;

        while (true) {
            try {
                System.out.print(msg);
                String dateStr = input.nextLine();
                date = LocalDate.parse(dateStr, dateFormat);

                break;
            } catch (DateTimeException e) {
                System.out.println("You entered wrong format date. Try again.");
            }
        }

        return date;
    }

    public static int enterIntNumber(String msg, int min) {
        Scanner input = new Scanner(System.in);
        int number;

        while (true) {
            try {
                System.out.print(msg);
                number = Integer.parseInt(input.nextLine());

                if (number < min) {
                    System.out.println("Number is smaller than " + min + ". Enter a bigger number");
                    continue;
                }

                break;
            } catch (NumberFormatException e) {
                System.out.println("You entered not a number. Please, try again."); // System.out.println("Ви ввели не число. Спроуйте ще.");
            }
        }

        return number;
    }

    public static long enterLongNumber(String msg, int min) {
        Scanner input = new Scanner(System.in);
        long number;

        while (true) {
            try {
                System.out.print(msg);
                number = Long.parseLong(input.nextLine());

                if (number < min) {
                    System.out.println("Number is smaller than " + min + ". Enter a bigger number");
                    continue;
                }

                break;
            } catch (NumberFormatException e) {
                System.out.println("You entered not a number. Please, try again."); // System.out.println("Ви ввели не число. Спроуйте ще.");
            }
        }

        return number;
    }
}

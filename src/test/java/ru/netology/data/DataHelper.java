package ru.netology.data;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataHelper {

    private DataHelper() {
    }

    private static Faker faker = new Faker(new Locale("en"));

    public static String generateDate(int month, String pattern) {
        return LocalDate.now().plusMonths(month).format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String approvedCardNumber() {
        return "4444444444444441";
    }

    public static String declinedCardNumber() {
        return "4444444444444442";
    }

    public static String shortCardNumber() {
        return String.valueOf(faker.number().randomNumber(15, true));
    }

    public static String validYear() {
        return generateDate(15, "YY");
    }

    public static String validMonth() {
        return generateDate(6, "MM");
    }

    public static String invalidMonth() {
        return LocalDate.now().minusMonths(faker.number().numberBetween(1, 5)).format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String yearLessThanCurrent() {
        return LocalDate.now().minusYears(faker.number().numberBetween(1, 5)).format(DateTimeFormatter.ofPattern("YY"));
    }

    public static String nowYear() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("YY"));
    }

    public static String validCardOwner() {
        return faker.name().firstName() + " " + faker.name().lastName();
    }

    public static String generateCvcCvv() {
        Random rnd = new Random();
        int cvc = rnd.nextInt(900) + 100;
        return String.valueOf(cvc);
    }

    public static String invalidCvcCvv() {
        return String.valueOf(faker.number().numberBetween(0, 99));
    }

    public static String invalidCardNumber() {
        return String.valueOf(faker.number().randomNumber(16, true));
    }

    public static CardInfo getApprovedCardInfo() {
        return new CardInfo(approvedCardNumber(),
                validMonth(),
                validYear(),
                validCardOwner(),
                generateCvcCvv());
    }

    public static CardInfo getDeclinedCardInfo() {
        return new CardInfo(declinedCardNumber(),
                validMonth(),
                validYear(),
                validCardOwner(),
                generateCvcCvv());
    }

    public static CardInfo getAllEmptyFields() {
        return new CardInfo("", "", "", "", "");
    }

    public static CardInfo getShortCardNumber() {
        return new CardInfo(shortCardNumber(),
                validMonth(),
                validYear(),
                validCardOwner(),
                generateCvcCvv());
    }

    public static CardInfo getInvalidNumber() {
        return new CardInfo(invalidCardNumber(),
                validMonth(),
                validYear(),
                validCardOwner(),
                generateCvcCvv());
    }

    public static CardInfo getInvalidMonth() {
        return new CardInfo(approvedCardNumber(),
                invalidMonth(),
                nowYear(),
                validCardOwner(),
                generateCvcCvv());
    }

    public static CardInfo getZeroMonth() {
        return new CardInfo(approvedCardNumber(),
                "00",
                validYear(),
                validCardOwner(),
                generateCvcCvv());
    }

    public static CardInfo getYearLessThanCurrent() {
        return new CardInfo(approvedCardNumber(),
                validMonth(),
                yearLessThanCurrent(),
                validCardOwner(),
                generateCvcCvv());
    }

    public static CardInfo getSymbolsCardOwner() {
        return new CardInfo(approvedCardNumber(),
                validMonth(),
                validYear(),
                "!@#$%^&*()",
                generateCvcCvv());
    }

    public static CardInfo getTwoDigitCvcCvv() {
        return new CardInfo(approvedCardNumber(),
                validMonth(),
                validYear(),
                validCardOwner(),
                invalidCvcCvv());
    }
}

package com.mindhub.homebanking.utils;

public final class Utility {
    private Utility() { }

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public static String getCardNumber() {
        return getRandomNumber(1000, 9999) + " " + getRandomNumber(1000, 9999) + " " + getRandomNumber(1000, 9999) + " " + getRandomNumber(1000, 9999);
    }

    public static int getCVV() {
        return getRandomNumber(100, 999);
    }

    public static String getNumberAccount() {
        return "VIN" + Utility.getRandomNumber(1000, 9999);
    }
}

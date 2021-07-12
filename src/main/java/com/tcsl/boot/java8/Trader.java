package com.tcsl.boot.java8;

public class Trader {
    private final String name;
    private final String city;

    public Trader(String n, String c) {
        this.name = n;
        this.city = c;
    }

    public String getName() {
        return this.name;
    }

    public String getCity() {
        return this.city;
    }

    public String toString() {
        return "com.tcsl.boot.java8.Trader:" + this.name + " in " + this.city;
    }
}

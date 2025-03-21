package com.ravemaster.fuelsave.models;

import java.io.Serializable;

public class Cash implements Serializable {
    public String name, amount, accumulated;

    public Cash(String name, String amount, String accumulated) {
        this.name = name;
        this.amount = amount;
        this.accumulated = accumulated;
    }
}

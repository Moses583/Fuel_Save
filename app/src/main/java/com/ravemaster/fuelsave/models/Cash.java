package com.ravemaster.fuelsave.models;

import java.io.Serializable;

public class Cash implements Serializable {
    public String name, amount, party, accumulated;

    public Cash(String name, String amount, String party, String accumulated) {
        this.name = name;
        this.amount = amount;
        this.party = party;
        this.accumulated = accumulated;
    }
}

package com.ravemaster.fuelsave.models;

import java.io.Serializable;

public class Fuel implements Serializable {
    public String fuelName,initialStock, currentStock, addedStock;
    public int image;

    public Fuel(String fuelName, String initialStock, String currentStock, String addedStock, int image) {
        this.fuelName = fuelName;
        this.initialStock = initialStock;
        this.currentStock = currentStock;
        this.addedStock = addedStock;
        this.image = image;
    }
}

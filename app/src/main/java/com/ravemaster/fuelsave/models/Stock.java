package com.ravemaster.fuelsave.models;

import java.io.Serializable;

public class Stock implements Serializable {
    public String stockName,initialStock, currentStock, addedStock;

    public Stock(String stockName, String initialStock, String currentStock, String addedStock) {
        this.stockName = stockName;
        this.initialStock = initialStock;
        this.currentStock = currentStock;
        this.addedStock = addedStock;
    }
}

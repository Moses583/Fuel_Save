package com.ravemaster.fuelsave.models;

import java.io.Serializable;

public class Pump implements Serializable {
    private String name, prevLitre, prevSale, totalLitre, totalSale;
    private int image;

    public Pump(String name, String prevLitre, String prevSale, String totalLitre, String totalSale, int image) {
        this.name = name;
        this.prevLitre = prevLitre;
        this.prevSale = prevSale;
        this.totalLitre = totalLitre;
        this.totalSale = totalSale;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getPrevLitre() {
        return prevLitre;
    }

    public String getPrevSale() {
        return prevSale;
    }

    public String getTotalLitre() {
        return totalLitre;
    }

    public String getTotalSale() {
        return totalSale;
    }

    public int getImage() {
        return image;
    }
}

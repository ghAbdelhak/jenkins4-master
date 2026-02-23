package com.example.livapp.model.pack;

import java.util.List;

public class PackHolder {

    private Pack pack;

    private List<PackProducts> packProductsList;

    public Pack getPack() {
        return pack;
    }

    public void setPack(Pack pack) {
        this.pack = pack;
    }

    public List<PackProducts> getPackProductsList() {
        return packProductsList;
    }

    public void setPackProductsList(List<PackProducts> packProductsList) {
        this.packProductsList = packProductsList;
    }
}

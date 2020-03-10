package com.example.instruments.model;

public class InstrumentModel {

    private String instrumentName;
    private String instrumentSymbol;
    private String id;
    private String assetClass;
    private String quantityIncrement;
    private String priceIncrement;
    private String bid;
    private String ask;

    public InstrumentModel(String instrumentName, String instrumentSymbol, String id, String assetClass, String quantityIncrement, String priceIncrement, String bid, String ask) {
        this.instrumentName = instrumentName;
        this.instrumentSymbol = instrumentSymbol;
        this.id = id;
        this.assetClass = assetClass;
        this.quantityIncrement = quantityIncrement;
        this.priceIncrement = priceIncrement;
        this.bid = bid;
        this.ask = ask;
    }

    public String getInstrumentName() {
        return instrumentName;
    }

    public void setInstrumentName(String instrumentName) {
        this.instrumentName = instrumentName;
    }

    public String getInstrumentSymbol() {
        return instrumentSymbol;
    }

    public void setInstrumentSymbol(String instrumentSymbol) {
        this.instrumentSymbol = instrumentSymbol;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAssetClass() {
        return assetClass;
    }

    public void setAssetClass(String assetClass) {
        this.assetClass = assetClass;
    }

    public String getQuantityIncrement() {
        return quantityIncrement;
    }

    public void setQuantityIncrement(String quantityIncrement) {
        this.quantityIncrement = quantityIncrement;
    }

    public String getPriceIncrement() {
        return priceIncrement;
    }

    public void setPriceIncrement(String priceIncrement) {
        this.priceIncrement = priceIncrement;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getAsk() {
        return ask;
    }

    public void setAsk(String ask) {
        this.ask = ask;
    }
}

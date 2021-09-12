package com.secretdevbd.xian.pigeoninfinityapp.Models;

import java.util.ArrayList;

public class AuctionObject {
    AuctionEvent AuctionEvent;
    ArrayList<Pigeon> Pigeons;

    public AuctionObject() {
    }

    public AuctionObject(com.secretdevbd.xian.pigeoninfinityapp.Models.AuctionEvent auctionEvent, ArrayList<Pigeon> pigeons) {
        AuctionEvent = auctionEvent;
        Pigeons = pigeons;
    }

    public com.secretdevbd.xian.pigeoninfinityapp.Models.AuctionEvent getAuctionEvent() {
        return AuctionEvent;
    }

    public void setAuctionEvent(com.secretdevbd.xian.pigeoninfinityapp.Models.AuctionEvent auctionEvent) {
        AuctionEvent = auctionEvent;
    }

    public ArrayList<Pigeon> getPigeons() {
        return Pigeons;
    }

    public void setPigeons(ArrayList<Pigeon> pigeons) {
        Pigeons = pigeons;
    }
}

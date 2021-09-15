package com.secretdevbd.xian.pigeoninfinityapp.Models;

import java.util.Date;

public class AuctionEvent {
    int AuctionID;
    String AuctionName;
    String AuctionDetails;
    int TotalPigeon;
    String AuctionStart;
    String AuctionEnd;
    String MainPicture;
    String Currency;

    public AuctionEvent() {
    }

    public AuctionEvent(int auctionID, String auctionName, String auctionDetails, int totalPigeon, String auctionStart, String auctionEnd, String mainPicture, String currency) {
        AuctionID = auctionID;
        AuctionName = auctionName;
        AuctionDetails = auctionDetails;
        TotalPigeon = totalPigeon;
        AuctionStart = auctionStart;
        AuctionEnd = auctionEnd;
        MainPicture = mainPicture;
        Currency = currency;
    }

    public int getAuctionID() {
        return AuctionID;
    }

    public void setAuctionID(int auctionID) {
        AuctionID = auctionID;
    }

    public String getAuctionName() {
        return AuctionName;
    }

    public void setAuctionName(String auctionName) {
        AuctionName = auctionName;
    }

    public String getAuctionDetails() {
        return AuctionDetails;
    }

    public void setAuctionDetails(String auctionDetails) {
        AuctionDetails = auctionDetails;
    }

    public int getTotalPigeon() {
        return TotalPigeon;
    }

    public void setTotalPigeon(int totalPigeon) {
        TotalPigeon = totalPigeon;
    }

    public String getAuctionStart() {
        return AuctionStart;
    }

    public void setAuctionStart(String auctionStart) {
        AuctionStart = auctionStart;
    }

    public String getAuctionEnd() {
        return AuctionEnd;
    }

    public void setAuctionEnd(String auctionEnd) {
        AuctionEnd = auctionEnd;
    }

    public String getMainPicture() {
        return MainPicture;
    }

    public void setMainPicture(String mainPicture) {
        MainPicture = mainPicture;
    }

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }
}

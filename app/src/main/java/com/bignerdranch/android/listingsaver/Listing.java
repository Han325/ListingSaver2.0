// Name: Eu Yu Han
// Student ID: 18123547

package com.bignerdranch.android.listingsaver;

import java.util.Date;
import java.util.UUID;

public class Listing {

    private UUID listID;
    private String listTitle;
    private Date listDate;
    private Date listTime;
    private String listLocation;
    private String listCompany;
    private String listLink;
    private String listSalary;
    private boolean isFullTime;
    private String listRecruiter;

    public Listing() {
        this(UUID.randomUUID());
    }

    public Listing(UUID id) {
        listID = id;
        listDate = new Date();
        listTime = new Date();
    }

    public UUID getListID() {
        return listID;
    }

    public void setListID(UUID listID) {
        this.listID = listID;
    }

    public String getListTitle() {
        return listTitle;
    }

    public void setListTitle(String listTitle) {
        this.listTitle = listTitle;
    }

    public Date getListDate() {
        return listDate;
    }

    public void setListDate(Date listDate) {
        this.listDate = listDate;
    }

    public Date getListTime() {
        return listTime;
    }

    public void setListTime(Date listTime) {
        this.listTime = listTime;
    }

    public String getListLocation() {
        return listLocation;
    }

    public void setListLocation(String listLocation) {
        this.listLocation = listLocation;
    }

    public String getListCompany() {
        return listCompany;
    }

    public void setListCompany(String listCompany) {
        this.listCompany = listCompany;
    }

    public String getListLink() {
        return listLink;
    }

    public void setListLink(String listLink) {
        this.listLink = listLink;
    }

    public String getListSalary() {
        return listSalary;
    }

    public void setListSalary(String listSalary) {
        this.listSalary = listSalary;
    }

    public boolean isFullTime() {
        return isFullTime;
    }

    public void setFullTime(boolean fullTime) {
        isFullTime = fullTime;
    }

    public String getListRecruiter() {
        return listRecruiter;
    }

    public void setListRecruiter(String listRecruiter) {
        this.listRecruiter = listRecruiter;
    }

    public String getPhotoFileName() {
        return "IMG_" + getListID().toString() + ".jpg";
    }
}

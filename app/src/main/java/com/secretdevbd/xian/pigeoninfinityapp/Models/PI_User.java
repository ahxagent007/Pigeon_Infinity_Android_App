package com.secretdevbd.xian.pigeoninfinityapp.Models;

public class PI_User {
    long userID;
    String firebase_uid;
    String name;
    String phone;
    String email;
    String password;
    String address;
    int bid_limit;
    int bid_point;
    int total_bid_amount;
    String facebook;
    String last_login;
    String register_date;
    String dob;
    String status = "UNVERIFIED";
    String nid;
    String ip_address;
    String pro_pic;
    String reference;
    String approvedBy;
    String fb_id;
    String fb_link;
    String fb_gender;
    String fb_locale;
    String fb_picture;
    String fb_email;

    public PI_User() {
    }

    public PI_User(long userID, String firebase_uid, String name, String phone, String email, String password, String address, int bid_limit, int bid_point, int total_bid_amount, String facebook, String last_login, String register_date, String dob, String status, String nid, String ip_address, String pro_pic, String reference, String approvedBy, String fb_id, String fb_link, String fb_gender, String fb_locale, String fb_picture, String fb_email) {
        this.userID = userID;
        this.firebase_uid = firebase_uid;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.address = address;
        this.bid_limit = bid_limit;
        this.bid_point = bid_point;
        this.total_bid_amount = total_bid_amount;
        this.facebook = facebook;
        this.last_login = last_login;
        this.register_date = register_date;
        this.dob = dob;
        this.status = status;
        this.nid = nid;
        this.ip_address = ip_address;
        this.pro_pic = pro_pic;
        this.reference = reference;
        this.approvedBy = approvedBy;
        this.fb_id = fb_id;
        this.fb_link = fb_link;
        this.fb_gender = fb_gender;
        this.fb_locale = fb_locale;
        this.fb_picture = fb_picture;
        this.fb_email = fb_email;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public String getFirebase_uid() {
        return firebase_uid;
    }

    public void setFirebase_uid(String firebase_uid) {
        this.firebase_uid = firebase_uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getBid_limit() {
        return bid_limit;
    }

    public void setBid_limit(int bid_limit) {
        this.bid_limit = bid_limit;
    }

    public int getBid_point() {
        return bid_point;
    }

    public void setBid_point(int bid_point) {
        this.bid_point = bid_point;
    }

    public int getTotal_bid_amount() {
        return total_bid_amount;
    }

    public void setTotal_bid_amount(int total_bid_amount) {
        this.total_bid_amount = total_bid_amount;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getLast_login() {
        return last_login;
    }

    public void setLast_login(String last_login) {
        this.last_login = last_login;
    }

    public String getRegister_date() {
        return register_date;
    }

    public void setRegister_date(String register_date) {
        this.register_date = register_date;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getIp_address() {
        return ip_address;
    }

    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }

    public String getPro_pic() {
        return pro_pic;
    }

    public void setPro_pic(String pro_pic) {
        this.pro_pic = pro_pic;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getFb_id() {
        return fb_id;
    }

    public void setFb_id(String fb_id) {
        this.fb_id = fb_id;
    }

    public String getFb_link() {
        return fb_link;
    }

    public void setFb_link(String fb_link) {
        this.fb_link = fb_link;
    }

    public String getFb_gender() {
        return fb_gender;
    }

    public void setFb_gender(String fb_gender) {
        this.fb_gender = fb_gender;
    }

    public String getFb_locale() {
        return fb_locale;
    }

    public void setFb_locale(String fb_locale) {
        this.fb_locale = fb_locale;
    }

    public String getFb_picture() {
        return fb_picture;
    }

    public void setFb_picture(String fb_picture) {
        this.fb_picture = fb_picture;
    }

    public String getFb_email() {
        return fb_email;
    }

    public void setFb_email(String fb_email) {
        this.fb_email = fb_email;
    }
}

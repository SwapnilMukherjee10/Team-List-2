package com.mustang.teamlistjava.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "team_table")
public class TeamMember {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "memberName")
    private String memberName;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "address")
    private String address;

    @ColumnInfo(name = "contact")
    private String contact;

    public TeamMember(int id, String memberName, String email, String address, String contact){
        this.id = id;
        this.memberName = memberName;
        this.email = email;
        this.address = address;
        this.contact = contact;
    }

    @Ignore
    public TeamMember(String memberName, String email, String address, String contact){
        this.memberName = memberName;
        this.email = email;
        this.address = address;
        this.contact = contact;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}

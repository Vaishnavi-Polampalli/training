package com.df.Onboarding.users.model;

import java.util.ArrayList;
import java.util.List;

public class ResultList {
    ArrayList<Users> users;

    public ResultList() {
        users = new ArrayList<>();
    }

    public ResultList(ArrayList<Users> users) {
        this.users = users;
    }

    public ArrayList<Users> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<Users> users) {
        this.users = users;
    }
    public void add(Users u){
        users.add(u);
    }

    @Override
    public String toString() {
        return "ResultList{" +
                "users=" + users +
                '}';
    }

    public void addAll(List<Users> all) {
        users.addAll(all);
    }
}

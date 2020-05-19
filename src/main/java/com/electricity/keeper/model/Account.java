package com.electricity.keeper.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long number;

    @JsonManagedReference
    @OneToMany(
            mappedBy = "account",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    private List<History> histories = new ArrayList<>();

    @JsonBackReference
    @ManyToOne
    private User user;

    public Account() {
    }

    public Account(long number) {
        this.number = number;
    }

    public long getId() {
        return id;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<History> getHistories() {
        return histories;
    }

    public void setHistory(List<History> histories) {
        this.histories = histories;
    }

    public void addHistory(History history) {
        this.histories.add(history);
        history.setAccount(this);
    }

    public void removeHistory(History history) {
        this.histories.remove(history);
        history.setAccount(null);
    }

    public void removeAllHistory() {
        this.histories.clear();
    }
}

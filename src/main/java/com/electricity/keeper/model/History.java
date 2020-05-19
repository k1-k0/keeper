package com.electricity.keeper.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonBackReference
    @ManyToOne
    private Account account;

    @NotNull
    private Long value;

    @DateTimeFormat
    private LocalDateTime timestamp = LocalDateTime.now();

    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL)
    private Photo photo;

    public History() {
    }

    public History(Long value) {
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof History)) return false;
        History history = (History) o;
        return getId() == history.getId() &&
                getAccount().equals(history.getAccount()) &&
                getValue().equals(history.getValue()) &&
                getTimestamp().equals(history.getTimestamp()) &&
                getPhoto().equals(history.getPhoto());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAccount(), getValue(), getTimestamp(), getPhoto());
    }
}

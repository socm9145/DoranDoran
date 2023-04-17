package com.purple.hello.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long roomId;
    String roomCode;
    String roomQuery;
    String roomPassword;
    int beginTime;
    @Temporal(TemporalType.TIMESTAMP)
    Date createAt;
    // mapping
    @OneToMany(mappedBy = "alarm", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    List<Alarm> alarm = new ArrayList<>();
    @OneToMany(mappedBy = "userRoom", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    List<UserRoom> userRoom = new ArrayList<>();
}
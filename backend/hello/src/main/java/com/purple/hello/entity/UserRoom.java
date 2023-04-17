package com.purple.hello.entity;

import com.purple.hello.enu.BoolAlarm;
import com.purple.hello.enu.UserRoomRole;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "user_rooms")
public class UserRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long userRoomId;
    @Enumerated(EnumType.STRING)
    UserRoomRole userRoomRole;
    String userName;
    String roomName;
    @Enumerated(EnumType.STRING)
    BoolAlarm safeAlarm;
    @Enumerated(EnumType.STRING)
    BoolAlarm moveAlarm;
    @Enumerated(EnumType.STRING)
    BoolAlarm dayAlarm;
    @Temporal(TemporalType.TIMESTAMP)
    Date createAt;
    // mapping
    @OneToMany(mappedBy = "userRoom", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    List<Feed> feed = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    User user;
    @ManyToOne(fetch = FetchType.LAZY)
    Room room;
}
package com.purple.hello.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long userId;
    String email;
    String name;
    String userProfileUrl;
    @Temporal(TemporalType.TIMESTAMP)
    Date createAt;
    @Temporal(TemporalType.TIMESTAMP)
    Date birth;
    String accessToken;
    String refreshToken;
    // mapping
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    List<UserRoom> userRoom = new ArrayList<>();
    @OneToMany(mappedBy = "srcUser", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    List<Alarm> srcAlarm = new ArrayList<>();
    @OneToMany(mappedBy = "dstUser", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    List<Alarm> dstAlarm = new ArrayList<>();
}

package com.purple.hello.entity;

import com.purple.hello.enu.AlarmType;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "alarms")
public class Alarm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long alarmId;
    @Enumerated(EnumType.STRING)
    AlarmType alarmType;
    String content;
    @Temporal(TemporalType.TIMESTAMP)
    Date createAt;
    // mapping
    @ManyToOne(fetch = FetchType.LAZY)
    User srcUser;
    @ManyToOne(fetch = FetchType.LAZY)
    User dstUser;
}

package com.purple.hello.entity;

import com.purple.hello.enu.FeedType;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "feeds")
public class Feed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long feedId;
    String feedUrl;
    String content;
    @Temporal(TemporalType.TIMESTAMP)
    Date createAt;
    @Enumerated(EnumType.STRING)
    FeedType feedType;
    // mapping
    @ManyToOne(fetch = FetchType.LAZY)
    UserRoom userRoom;
}

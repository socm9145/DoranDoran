package com.purple.hello.entity;

import com.purple.hello.enu.FeedType;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    @JoinColumn(name = "user_room_id")
    @ManyToOne(fetch = FetchType.LAZY)
    UserRoom userRoom;
}

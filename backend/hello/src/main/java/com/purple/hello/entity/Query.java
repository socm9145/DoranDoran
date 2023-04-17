package com.purple.hello.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "queries")
public class Query {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long queryId;
    @Column(nullable = false)
    String content;
}

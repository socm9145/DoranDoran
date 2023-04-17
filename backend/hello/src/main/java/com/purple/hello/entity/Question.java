package com.purple.hello.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long questionId;
    @Column(nullable = false)
    String content;
}

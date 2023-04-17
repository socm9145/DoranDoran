package com.purple.hello.repo;

import com.purple.hello.entity.Feed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedRepo extends JpaRepository<Long, Feed> {
}

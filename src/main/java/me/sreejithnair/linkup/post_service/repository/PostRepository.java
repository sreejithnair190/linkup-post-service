package me.sreejithnair.linkup.post_service.repository;

import me.sreejithnair.linkup.post_service.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}

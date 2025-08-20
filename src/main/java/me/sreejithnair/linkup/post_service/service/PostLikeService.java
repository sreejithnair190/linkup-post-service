package me.sreejithnair.linkup.post_service.service;

public interface PostLikeService {
    String togglePostLike(Long postId, Long userId);
}

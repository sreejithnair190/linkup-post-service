package me.sreejithnair.linkup.post_service.service.impl;

import lombok.RequiredArgsConstructor;
import me.sreejithnair.linkup.post_service.constants.Events;
import me.sreejithnair.linkup.post_service.entity.Post;
import me.sreejithnair.linkup.post_service.entity.PostLike;
import me.sreejithnair.linkup.post_service.event.PostLikedEvent;
import me.sreejithnair.linkup.post_service.exception.ResourceNotFoundException;
import me.sreejithnair.linkup.post_service.repository.PostLikeRepository;
import me.sreejithnair.linkup.post_service.repository.PostRepository;
import me.sreejithnair.linkup.post_service.service.PostLikeService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static me.sreejithnair.linkup.post_service.constants.Events.POST_LIKED_EVENT;

@Service
@RequiredArgsConstructor
public class PostLikeServiceImpl implements PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final KafkaTemplate<Long, PostLikedEvent> kafkaTemplate;

    @Override
    public String togglePostLike(Long postId, Long userId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post Not Found!"));

        Optional<PostLike> postLike = postLikeRepository.findByPostIdAndUserId(postId, userId);
        if (postLike.isEmpty()) {
            PostLike newPostLike = PostLike
                    .builder()
                    .postId(postId)
                    .userId(userId)
                    .build();

            PostLike savedPostLike = postLikeRepository.save(newPostLike);

            PostLikedEvent postLikedEvent = PostLikedEvent.builder()
                    .postId(postId)
                    .likedByUserId(userId)
                    .creatorId(post.getUserId())
                    .build();

            kafkaTemplate.send(POST_LIKED_EVENT, postId, postLikedEvent);

            return "Post has been liked!";
        }

        postLikeRepository.delete(postLike.get());

        return "Post has been disliked!";
    }
}

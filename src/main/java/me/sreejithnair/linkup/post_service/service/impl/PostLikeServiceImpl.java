package me.sreejithnair.linkup.post_service.service.impl;

import lombok.RequiredArgsConstructor;
import me.sreejithnair.linkup.post_service.entity.PostLike;
import me.sreejithnair.linkup.post_service.exception.ResourceNotFoundException;
import me.sreejithnair.linkup.post_service.repository.PostLikeRepository;
import me.sreejithnair.linkup.post_service.repository.PostRepository;
import me.sreejithnair.linkup.post_service.service.PostLikeService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostLikeServiceImpl implements PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;

    @Override
    public String togglePostLike(Long postId, Long userId) {

        postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post Not Found!"));

        Optional<PostLike> postLike = postLikeRepository.findByPostIdAndUserId(postId, userId);
        if (postLike.isEmpty()) {
            PostLike newPostLike = PostLike
                    .builder()
                    .postId(postId)
                    .userId(userId)
                    .build();

            postLikeRepository.save(newPostLike);

            return "Post has been liked!";
        }

        postLikeRepository.delete(postLike.get());

        return "Post has been disliked!";
    }
}

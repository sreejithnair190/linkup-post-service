package me.sreejithnair.linkup.post_service.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import me.sreejithnair.linkup.post_service.auth.UserContextHolder;
import me.sreejithnair.linkup.post_service.common.ApiResponse;
import me.sreejithnair.linkup.post_service.service.PostLikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static me.sreejithnair.linkup.post_service.auth.UserContextHolder.getCurrentUserId;

@RestController
@RequestMapping("/post-likes")
@RequiredArgsConstructor
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping("{postId}")
    public ResponseEntity<ApiResponse<String>> togglePostLike(@PathVariable Long postId) {
        Long userId = getCurrentUserId();
        String message = postLikeService.togglePostLike(postId, userId);
        ApiResponse<String> apiResponse = new ApiResponse<>(message);
        return ResponseEntity.ok(apiResponse);
    }
}

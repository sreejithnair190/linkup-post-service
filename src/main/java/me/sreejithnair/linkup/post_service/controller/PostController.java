package me.sreejithnair.linkup.post_service.controller;

import lombok.RequiredArgsConstructor;
import me.sreejithnair.linkup.post_service.dto.request.PostRequestDto;
import me.sreejithnair.linkup.post_service.dto.response.PostResponseDto;
import me.sreejithnair.linkup.post_service.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static me.sreejithnair.linkup.post_service.auth.UserContextHolder.getCurrentUserId;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(@RequestBody PostRequestDto postRequestDto) {
        Long userId = getCurrentUserId();
        PostResponseDto postResponseDto = postService.createPost(postRequestDto, userId);
        return new ResponseEntity<>(postResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("{postId}")
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable Long postId) {
        PostResponseDto postResponseDto = postService.getPostById(postId);
        return ResponseEntity.ok(postResponseDto);
    }

    @GetMapping("/user")
    public ResponseEntity<Page<PostResponseDto>> getUserPosts(
            @RequestParam(defaultValue = "0") Long userId,
            @RequestParam(defaultValue = "1") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir,
            @RequestParam(defaultValue = "") String search
    ) {
        if (userId == 0L) {
            userId = getCurrentUserId();
        }

        Page<PostResponseDto> userPosts = postService.getUserPosts(
                userId,
                pageNumber,
                size,
                sortBy,
                sortDir
        );

        return ResponseEntity.ok(userPosts);
    }
}

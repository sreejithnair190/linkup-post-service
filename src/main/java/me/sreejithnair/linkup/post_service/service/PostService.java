package me.sreejithnair.linkup.post_service.service;

import me.sreejithnair.linkup.post_service.dto.request.PostRequestDto;
import me.sreejithnair.linkup.post_service.dto.response.PostResponseDto;
import org.springframework.data.domain.Page;

public interface PostService {
    PostResponseDto createPost(PostRequestDto postRequestDto, Long userId);
    Page<PostResponseDto> getPosts(Integer pageNumber, Integer size, String sortBy, String sortDir);
    Page<PostResponseDto> getUserPosts(Long userId, Integer pageNumber, Integer size, String sortBy, String sortDir);
    PostResponseDto getPostById(Long postId);
    PostResponseDto updatePost(PostRequestDto postRequestDto, Long postId, Long userId);
    String deletePost(Long postId, Long userId);
}

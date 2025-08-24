package me.sreejithnair.linkup.post_service.service.impl;

import lombok.RequiredArgsConstructor;
import me.sreejithnair.linkup.post_service.client.ConnectionsClient;
import me.sreejithnair.linkup.post_service.dto.request.PostRequestDto;
import me.sreejithnair.linkup.post_service.dto.response.PersonResponseDto;
import me.sreejithnair.linkup.post_service.dto.response.PostResponseDto;
import me.sreejithnair.linkup.post_service.entity.Post;
import me.sreejithnair.linkup.post_service.exception.ResourceNotFoundException;
import me.sreejithnair.linkup.post_service.repository.PostRepository;
import me.sreejithnair.linkup.post_service.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static me.sreejithnair.linkup.post_service.util.Helper.generatePageable;

@Service
@RequiredArgsConstructor
public class        PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final ConnectionsClient connectionsClient;

    @Override
    public PostResponseDto createPost(PostRequestDto postRequestDto, Long userId) {
        Post newPost = modelMapper.map(postRequestDto, Post.class);
        newPost.setUserId(userId);

        Post savedPost = postRepository.save(newPost);

        return modelMapper.map(savedPost, PostResponseDto.class);
    }

    @Override
    public Page<PostResponseDto> getPosts(Integer pageNumber, Integer size, String sortBy, String sortDir) {
        return null;
    }

    @Override
    public Page<PostResponseDto> getUserPosts(Long userId, Integer pageNumber, Integer size, String sortBy, String sortDir) {
        String[] sortable = {"id", "content", "createdAt"};
        Pageable pageable = generatePageable(pageNumber, size, sortBy, sortDir, sortable, "createdAt");
        Page<Post> posts = postRepository.findAll(pageable);
        return posts.map(post -> modelMapper.map(post, PostResponseDto.class));
    }

    @Override
    public PostResponseDto getPostById(Long postId) {
        Post post = postRepository
                        .findById(postId)
                        .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        List<PersonResponseDto> firstConnections = connectionsClient.getFirstConnections(post.getUserId());

        return modelMapper.map(post, PostResponseDto.class);
    }

    @Override
    public PostResponseDto updatePost(PostRequestDto postRequestDto, Long postId, Long userId) {
        return null;
    }

    @Override
    public String deletePost(Long postId, Long userId) {
        return "";
    }
}

package ru.netology.repository;

import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

// Stub
public class PostRepository {
    private final Map<Long, Post> posts = new ConcurrentHashMap<>();
    private final AtomicLong id = new AtomicLong(1L);

    public List<Post> all() {
        return new ArrayList<>(posts.values());
    }

    public Optional<Post> getById(long id) {
        return Optional.ofNullable(posts.get(id));
    }

    public Post save(Post post) {
        if (post.getId() == 0L) {
            post.setId(id.getAndIncrement());
            posts.put(id.get(), new Post(id.get(), post.getContent()));
            return post;
        } else {
            final Post oldPost = getById(post.getId()).orElseThrow(NotFoundException::new);
            oldPost.setContent(post.getContent());
            return oldPost;
        }
    }

    public void removeById(long id) {
        posts.remove(id);
    }
}

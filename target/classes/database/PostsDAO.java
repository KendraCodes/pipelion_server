package database;

import model.JsonUtils;
import model.Post;
import model.PostFilters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Kendra on 10/18/2018.
 */
public class PostsDAO {

    public List<Post> getAllPosts() {
        return JsonUtils.loadPosts();
    }

    private List<Post> getFilteredPosts(PostFilters filters) {
        List<Post> allPosts = JsonUtils.loadPosts();
        List<Post> posts = new ArrayList<>();

        for (Post p : allPosts) {
            if (filters.postMatches(p)) {
                posts.add(p);
            }
        }
        Collections.sort(posts);
        return posts;
    }

    public List<Post> getPosts(int howMany, PostFilters filters) {
        List<Post> posts = getFilteredPosts(filters);
        int endIdx = howMany;
        endIdx = endIdx > posts.size() ? posts.size() : endIdx;
        return posts.subList(0, endIdx);
    }

    public List<Post> getPosts(int howMany, PostFilters filters, String startingID) {
        List<Post> posts = getFilteredPosts(filters);
        HashMap<String, Post> postsMap = new HashMap<>();
        for (Post p : posts) {
            postsMap.put(p.getId(), p);
        }
        int startIdx = posts.indexOf(postsMap.get(startingID)) + 1;
        int endIdx = startIdx + howMany;
        endIdx = endIdx > posts.size() ? posts.size() : endIdx;
        return posts.subList(startIdx, endIdx);
    }

}

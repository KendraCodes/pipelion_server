package database;

import com.google.gson.JsonObject;
import model.Asset;
import model.JsonUtils;
import model.Post;
import model.PostFilters;

import java.util.*;

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

    public Post getPostById(String postID) {
        List<Post> posts = getAllPosts();
        HashMap<String, Post> postsMap = new HashMap<>();
        for (Post a : posts) {
            postsMap.put(a.getId(), a);
        }
        return postsMap.get(postID);
    }

    private List<Post> getIdSublist(List<Post> posts, int howMany, String startingID) {
        HashMap<String, Post> postsMap = new HashMap<>();
        for (Post a : posts) {
            postsMap.put(a.getId(), a);
        }
        int startIdx = posts.indexOf(postsMap.get(startingID)) + 1;
        int endIdx = startIdx + howMany;
        endIdx = endIdx > posts.size() ? posts.size() : endIdx;
        return posts.subList(startIdx, endIdx);
    }

    private List<Post> getSublist(List<Post> posts, int howMany) {
        int endIdx = howMany;
        endIdx = endIdx > posts.size() ? posts.size() : endIdx;
        return posts.subList(0, endIdx);
    }

    public List<Post> getPosts(int howMany, PostFilters filters) {
        return getSublist(getFilteredPosts(filters), howMany);
    }

    public List<Post> getPosts(int howMany, PostFilters filters, String startingID) {
        return getIdSublist(getFilteredPosts(filters), howMany, startingID);
    }

    private List<Post> searchPosts(List<Post> original, String searchTerm) {
        HashSet<Post> matchesSearch = new HashSet<>();
        String search = searchTerm.toLowerCase();
        for (Post p : original) {
            if (p.getArtistName().toLowerCase().contains(search)) {
                matchesSearch.add(p);
            }
            if (p.getAssetName().toLowerCase().contains(search)) {
                matchesSearch.add(p);
            }
        }
        List<Post> matchingPosts = new ArrayList<>();
        matchingPosts.addAll(matchesSearch);
        return matchingPosts;
    }

    public List<Post> searchPosts(PostFilters filters, int howMany, String searchTerm) {
        List<Post> posts = searchPosts(getFilteredPosts(filters), searchTerm);
        return getSublist(posts, howMany);
    }

    public List<Post> searchPosts(PostFilters filters, int howMany, String searchTerm, String startingID) {
        List<Post> posts = searchPosts(getFilteredPosts(filters), searchTerm);
        return getIdSublist(posts, howMany, startingID);
    }

}

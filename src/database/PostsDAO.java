package database;

import com.google.gson.JsonObject;
import model.Asset;
import model.JsonUtils;
import model.Post;
import model.PostFilters;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by Kendra on 10/18/2018.
 */
public class PostsDAO {

    public static void main(String[] args) {
        List<Post> allPosts = new PostsDAO().getAllPosts();
        new PostsDAO().addPost(allPosts);
    }

    public void addPost(Post p) {
        List<Post> posts = new ArrayList<>();
        posts.add(p);
        addPost(posts);
    }

    public void addPost(Collection<Post> posts) {
        try {
            String sql = "insert into Posts values (?,?,?,?,?,?,?,?,?,?,?)";
            Connection connection = ConnectionFactory.openConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            for (Post p : posts) {
                stmt.setString(1, p.getId());
                stmt.setString(2, p.getArtistID());
                stmt.setString(3, p.getArtistName());
                stmt.setString(4, p.getAssetID());
                stmt.setString(5, p.getAssetName());
                stmt.setString(6, p.getContentAPI().toString());
                stmt.setString(7, p.getContentID());
                stmt.setString(8, p.getDepartment());
                stmt.setString(9, p.getTimestampString());
                stmt.setString(10, p.getSlackLink());
                stmt.setString(11, p.getSlackMessage());

                stmt.execute();
            }
            ConnectionFactory.closeConnection(connection, true);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

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

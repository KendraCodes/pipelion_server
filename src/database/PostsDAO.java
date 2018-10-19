package database;

import model.JsonUtils;
import model.Post;

import java.util.List;

/**
 * Created by Kendra on 10/18/2018.
 */
public class PostsDAO {

    public List<Post> getAllPosts() {
        return JsonUtils.loadPosts();
    }

}

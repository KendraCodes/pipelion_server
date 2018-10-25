package model;

import com.google.gson.*;

import java.io.*;
import java.time.ZonedDateTime;
import java.util.*;

/**
 * Created by Kendra on 10/18/2018.
 */
public class JsonUtils {

    public static Reader getFileReader(String filepath) throws FileNotFoundException {
        return new BufferedReader(new FileReader(filepath));
    }

    public static String prettyPrintJson(Object obj) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(obj).toString();
    }

    public static void prettyPrintJson(Object obj, String filepath) {
        try {
            FileOutputStream out = new FileOutputStream(new File(filepath));
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            out.write(gson.toJson(obj).getBytes());
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Scanner scan = new Scanner(System.in);

    private static JsonObject getBaseOBJ() {
        try {
            return new Gson().fromJson(getFileReader("mockData.json"), JsonObject.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        List<Asset> assets = loadAssets();
        List<Post> posts = loadPosts();
        Map<String, Set<Post>> postsByAsset = new HashMap<>();
        for (Post p : posts) {
            if (!postsByAsset.containsKey(p.getAssetID())) {
                postsByAsset.put(p.getAssetID(), new HashSet<>());
            }
            postsByAsset.get(p.getAssetID()).add(p);
        }
        for (Asset a : assets) {
            if (postsByAsset.containsKey(a.getId())) {
                ZonedDateTime mostRecent = null;
                for (Post p : postsByAsset.get(a.getId())) {
                    if (mostRecent == null) {
                        mostRecent = p.getTimestamp();
                    } else {
                        if (mostRecent.isAfter(p.getTimestamp())) {
                            mostRecent = p.getTimestamp();
                        }
                    }
                }
                a.setTimestamp(DartUtils.convertToDartDate(mostRecent));
            }
        }
        prettyPrintJson(assets, "timestampAssets.json");
    }

    public static List<Asset> loadAssets() {
        JsonArray jsonArr = getBaseOBJ().getAsJsonArray("rawAssetsList");
        List<Asset> assets = new ArrayList<>();
        Gson gson = new Gson();
        for (JsonElement elem : jsonArr) {
            Asset a = gson.fromJson(elem, Asset.class);
            assets.add(a);
        }
        Collections.sort(assets);
        return assets;
    }

    public static List<Post> loadPosts() {
        JsonArray jsonArr = getBaseOBJ().getAsJsonArray("rawPostsList");
        List<Post> posts = new ArrayList<>();
        Gson gson = new Gson();
        for (JsonElement elem : jsonArr) {
            Post p = gson.fromJson(elem, Post.class);
            posts.add(p);
        }
        Collections.sort(posts);
        return posts;
    }

    public static List<String> loadDepartments() {
        String[] departments = new Gson().fromJson(getBaseOBJ().get("departments"), String[].class);
        Arrays.sort(departments);
        return Arrays.asList(departments);
    }

    public static List<Notification> loadNotifications() {
        JsonArray jsonArr = getBaseOBJ().getAsJsonArray("rawNotificationsList");
        List<Notification> notifications = new ArrayList<>();
        Gson gson = new Gson();
        for (JsonElement elem : jsonArr) {
            Notification n = gson.fromJson(elem, Notification.class);
            notifications.add(n);
        }
        Collections.sort(notifications);
        return notifications;
    }

}

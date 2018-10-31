package database;

import model.Asset;
import model.JsonUtils;
import model.Notification;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Kendra on 10/18/2018.
 */
public class NotificationsDAO {

    public List<Notification> getAllNotifications() {
        return JsonUtils.loadNotifications();
    }

    private List<Notification> getIdSublist(List<Notification> notifications, int howMany, String startingID) {
        HashMap<String, Notification> notificationsMap = new HashMap<>();
        for (Notification a : notifications) {
            notificationsMap.put(a.getId(), a);
        }
        int startIdx = notifications.indexOf(notificationsMap.get(startingID)) + 1;
        int endIdx = startIdx + howMany;
        endIdx = endIdx > notifications.size() ? notifications.size() : endIdx;
        return notifications.subList(startIdx, endIdx);
    }

    private List<Notification> getSublist(List<Notification> notifications, int howMany) {
        int endIdx = howMany;
        endIdx = endIdx > notifications.size() ? notifications.size() : endIdx;
        return notifications.subList(0, endIdx);
    }

    public List<Notification> getNotifications(int howMany, String id) {
        return getIdSublist(getAllNotifications(), howMany, id);
    }

    public List<Notification> getNotifications(int howMany) {
        return getSublist(getAllNotifications(), howMany);
    }
}

package database;

import model.JsonUtils;
import model.Notification;

import java.util.List;

/**
 * Created by Kendra on 10/18/2018.
 */
public class NotificationsDAO {

    public List<Notification> getAllNotifications() {
        return JsonUtils.loadNotifications();
    }
}

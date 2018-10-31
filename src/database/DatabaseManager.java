package database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Kendra on 10/23/2018.
 */
public class DatabaseManager {

    public static void main(String[] args) {
        new DatabaseManager().createTables();
    }


    public void createTables() {
        try {
            String assetsTable = "create table if not exists Assets(" +
                    "id text not null primary key," +
                    "thumbnail text not null," +
                    "name text not null," +
                    "timestamp text not null" +
                    ");";
            String postsTable = "create table if not exists Posts(" +
                    "id text not null primary key," +
                    "artistID text not null," +
                    "artistName text not null," +
                    "assetID text not null," +
                    "assetName text not null," +
                    "contentAPI text not null," +
                    "contentID text not null," +
                    "department text not null," +
                    "timestamp text not null," +
                    "slackLink text not null," +
                    "slackMessage text not null" +
                    ");";
            String artistsTable = "create table if not exists Artists(" +
                    "artistID text not null primary key," +
                    "artistName text not null" +
                    ");";
            //todo add the artists watching things table
            Connection conn = ConnectionFactory.openConnection();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(assetsTable);
            stmt.executeUpdate(postsTable);
            stmt.executeUpdate(artistsTable);
            ConnectionFactory.closeConnection(conn, true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

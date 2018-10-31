package database;

import model.Artist;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Kendra on 10/30/2018.
 */
public class ArtistsDAO {

    public String getArtistName(String artistID) {
        //todo
        return artistID;
    }

    public void addArtist(Artist a) {
        List<Artist> artists = new ArrayList<>();
        artists.add(a);
        addArtist(artists);
    }

    public void addArtist(Collection<Artist> artists) {
        try {
            Connection connection = ConnectionFactory.openConnection();

            String sql = "insert into Artists values (?, ?);";
            PreparedStatement stmt = connection.prepareStatement(sql);
            for (Artist a : artists) {
                stmt.setString(1, a.getArtistID());
                stmt.setString(2, a.getArtistName());

                stmt.executeUpdate();
            }
            ConnectionFactory.closeConnection(connection, true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setWatching(String artistID, String assetID, boolean isWatching) {
        Connection connection = null;
        try {
            connection = ConnectionFactory.openConnection();

            if (isWatching) {
                String sql = "insert into ArtistsWatching values (?, ?, ?);";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1, artistID + assetID);
                stmt.setString(2, artistID);
                stmt.setString(3, assetID);
                stmt.executeUpdate();

            } else {
                String sql = "delete from ArtistsWatching where hash = ? ;";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1, artistID + assetID);
                stmt.executeUpdate();
            }
            ConnectionFactory.closeConnection(connection, true);
        } catch (SQLException e) {
            e.printStackTrace();
            if (connection != null) {
                ConnectionFactory.closeConnection(connection, false);
            }
        }
    }

    public List<String> getWatchedAssets(String artistID) {
        List<String> assetIDs = new ArrayList<>();

        try {
            Connection connection = ConnectionFactory.openConnection();

            String sql = "select (assetID) from ArtistsWatching where artistID = ?;";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, artistID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                assetIDs.add(rs.getString(1));
            }

            ConnectionFactory.closeConnection(connection, true);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return assetIDs;
    }

}

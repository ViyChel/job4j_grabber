package ru.job4j.html;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Class PsqlStore. The class provides methods for working with the database.
 *
 * @author Vitaly Yagufarov (for.viy@gmail.com)
 * @version 1.0
 * @since 14.06.2020
 */
public class PsqlStore implements Store, AutoCloseable {

    private static final Logger LOG = LoggerFactory.getLogger(PsqlStore.class.getName());
    private Connection cnn;
    private static final String QUERY_SAVE = "INSERT INTO post (name, text, link, created) VALUES (?, ?, ?, ?);";
    private static final String QUERY_GET_ALL = "SELECT * FROM post;";
    private static final String QUERY_FIND_BY_ID = "SELECT * FROM post WHERE id = ?;";

    /**
     * Instantiates a new Psql store.
     *
     * @param cfg the cfg
     */
    public PsqlStore(Properties cfg) {
        this.cnn = new ConnectorDB().getConnection(cfg);
    }

    /**
     * Saves ad to database.
     *
     * @param post
     */
    @Override
    public void save(Post post) {
        try (PreparedStatement ps = cnn.prepareStatement(QUERY_SAVE)) {
            ps.setString(1, post.getName());
            ps.setString(2, post.getText());
            ps.setString(3, post.getUrl());
            ps.setTimestamp(4, post.getCreated());
            ps.executeUpdate();
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * Allows you to extract ads from the database.
     *
     * @return List<Post>
     */
    @Override
    public List<Post> getAll() {
        List<Post> result = new ArrayList<>();
        try (PreparedStatement ps = cnn.prepareStatement(QUERY_GET_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                result.add(createPost(rs));
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public Post findById(String id) {
        Post result = null;
        try (PreparedStatement ps = cnn.prepareStatement(QUERY_FIND_BY_ID)) {
            ps.setInt(1, Integer.parseInt(id));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    result = createPost(rs);
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public void close() throws Exception {
        if (cnn != null) {
            cnn.close();
        }
    }

    private Post createPost(ResultSet rs) throws SQLException {
        String name = rs.getString("name");
        String text = rs.getString("text");
        String url = rs.getString("link");
        Timestamp created = rs.getTimestamp("created");
        return new Post(name, text, url, created);
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws IOException the io exception
     */
    public static void main(String[] args) throws Exception {
        Properties cfg = new Properties();
        InputStream in = PsqlStore.class.getClassLoader().getResourceAsStream("app.properties");
        cfg.load(in);
        PsqlStore psqlStore = new PsqlStore(cfg);
        SqlRuParse sqlRuParse = new SqlRuParse();
        for (int i = 1; i < 6; i++) {
            sqlRuParse.list("https://www.sql.ru/forum/job-offers/" + i);
        }
        for (Post post: sqlRuParse.getAllPosts()) {
            psqlStore.save(post);
        }
        List<Post> list = psqlStore.getAll();
        System.out.println(list.size());
        Post post = psqlStore.findById("5");
        System.out.println(post.getName());
        System.out.println(post.getText());
        System.out.println(post.getUrl());
        System.out.println(post.getCreated());
        psqlStore.close();
    }
}
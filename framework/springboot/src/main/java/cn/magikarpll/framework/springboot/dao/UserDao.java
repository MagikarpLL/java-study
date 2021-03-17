package cn.magikarpll.framework.springboot.dao;

import cn.magikarpll.framework.springboot.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 一般大工程推荐按照 module/dao 来分包， 但我们这是个示例工程就无所谓了，直接所有dao放在一起
 */
@Repository
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String SQL_GET_MATCH_COUNT = "SELECT count(*) FROM t_user WHERE user_name = ? " +
                " AND password = ? ";

    private static final String SQL_FIND_USER_BY_NAME = "SELECT * FROM t_user WHERE user_name = ? ";

    private static final String SQL_UPDATE_LOGIN_INFO = "UPDATE t_user SET last_visit=?, last_ip=?, credits=? WHERE user_id = ?";

    public int getMatchCount(String username, String password) {
        return jdbcTemplate.queryForObject(SQL_GET_MATCH_COUNT, new Object[]{username, password}, Integer.class);
    }

    public User findUserByUserName(String username){
        final User user = new User();
        jdbcTemplate.query(SQL_FIND_USER_BY_NAME, new Object[]{username}, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {
                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("user_name"));
                user.setCredits(rs.getInt("credits"));
            }
        });
        return user;
    }

    public void updateLoginInfo(User user){
        jdbcTemplate.update(SQL_UPDATE_LOGIN_INFO, new Object[]{user.getLastVisit(), user.getLastIp(), user.getCredits(), user.getUserId()});
    }

}

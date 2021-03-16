package cn.magikarpll.framework.spring.dao;

import cn.magikarpll.framework.spring.domain.LoginLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LoginLogDao {

    private JdbcTemplate jdbcTemplate;

    private final static String SQL_INSERT_LOGIN_LOG_SQL = "INSERT INTO t_login_log (user_id, ip, login_datetime) VALUES (?,?,?)";

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertLoginLogSql(LoginLog loginLog){
        Object args[] = {loginLog.getUserId(), loginLog.getIp(), loginLog.getLoginDate()};
        jdbcTemplate.update(SQL_INSERT_LOGIN_LOG_SQL, args);
    }





}

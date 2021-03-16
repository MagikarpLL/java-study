package cn.magikarpll.framework.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private int userId;

    private String username;

    private String password;

    private int credits;

    private String lastIp;

    private Date lastVisit;

}

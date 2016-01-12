package com.cxjhihihi.finger.dao;

import com.cxjhihihi.finger.domain.User;

public interface UserDao extends CommonDao<User> {

    /**
     * @param openid
     * @return
     */
    int isExist(String username);

    /**
     * @param username
     * @return
     */
    User queryByUsername(String username);
}

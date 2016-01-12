package com.cxjhihihi.finger.service;

import com.cxjhihihi.finger.domain.User;

public interface UserService extends CommonService<User> {
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

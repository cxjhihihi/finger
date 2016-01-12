package com.cxjhihihi.finger.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cxjhihihi.finger.dao.UserDao;
import com.cxjhihihi.finger.domain.User;
import com.cxjhihihi.finger.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

    @Override
    public int add(User t) {
        return userDao.add(t);
    }

    @Override
    public int deleteByIds(List<Integer> ids) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int update(User t) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public User queryById(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<User> queryAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int isExist(String username) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public User queryByUsername(String username) {
        // TODO Auto-generated method stub
        return null;
    }

}

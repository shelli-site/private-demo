package com.example.demo.service;

import com.example.demo.dao.UserDao;
import com.example.demo.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Created By ShenXi
 * @Created On 2019/3/27 16:44
 * @Description :
 * @ClassName : UserService
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserService {
    @Autowired
    UserDao userDao;

    @Cacheable(value = "user", key = "#id", unless = "#result==null")
    public User getUserById(String id) {
        return userDao.findById(id).get();
    }

    public List<User> getList() {
        return userDao.findAll();
    }

    public User add(User user) {
        return userDao.save(user);
    }

    public boolean edit(User user) {
        if (userDao.findById(user.getId()) != null)
            return userDao.saveAndFlush(user) != null;
        return false;
    }

    public boolean delByIds(List<String> idList) {
        return userDao.deleteByIds(idList) > 0;
    }
}

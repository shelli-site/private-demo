package com.example.demo.dao;

import com.example.demo.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName UserDao
 * @Description
 * @Author ShenXi
 * @Date 2019/3/27 16:41
 */
@Repository
public interface UserDao extends JpaRepository<User, String> {

    @Modifying
    @Query(value = "delete from auth_user where id in :ids", nativeQuery = true)
    public int deleteByIds(@Param(value = "ids") List<String> ids);

}

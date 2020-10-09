package com.example.annotation.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.annotation.annotation.DataAuth;
import com.example.annotation.domain.User;
import com.example.annotation.domain.VO.UserVO;
import org.apache.ibatis.annotations.Param;

public interface UserMapper extends BaseMapper<User> {
    @DataAuth(columnAlias = "use_id")
    IPage<User> getAll(@Param("query") UserVO userVO, @Param("page") Page page);
}
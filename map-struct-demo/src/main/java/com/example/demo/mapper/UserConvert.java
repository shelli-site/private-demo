package com.example.demo.mapper;

import com.example.demo.entry.UserBO;
import com.example.demo.entry.UserDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Created By shelli On 2020/4/27 15:04
 */
@Mapper
public interface UserConvert {
    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class); // <2>

    UserBO convert(UserDO userDO);
}

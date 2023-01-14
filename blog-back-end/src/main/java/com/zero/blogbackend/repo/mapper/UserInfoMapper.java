package com.zero.blogbackend.repo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zero.blogbackend.model.po.UserInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * UserMapper
 *
 * @author ZERO
 * @date 2023/1/12
 */
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {

}

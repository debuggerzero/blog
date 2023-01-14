package com.zero.blogbackend.model.converter;

import com.zero.blogbackend.model.po.UserInfo;
import com.zero.blogbackend.model.vo.UserInfoVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * UserInfo --> UserInfoVO
 *
 * @author ZERO
 * @date 2023/1/14
 */
@Mapper
public interface ToUserInfoVoConverter {
    ToUserInfoVoConverter CONVERTER = Mappers.getMapper(ToUserInfoVoConverter.class);

    /**
     * ToUserInfoVO
     * @param userInfo userInfo
     * @return UserInfoVO
     */
    UserInfoVO toUserInfoVO(UserInfo userInfo);
}

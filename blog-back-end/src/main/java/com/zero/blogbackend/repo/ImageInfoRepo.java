package com.zero.blogbackend.repo;

import com.zero.blogbackend.model.po.ImageInfo;

import java.util.List;

/**
 * ImageInfoRepo
 *
 * @author ZERO
 * @date 2023/2/3
 */
public interface ImageInfoRepo {

    /**
     * 获取图片列表
     * @param userId 用户 Id
     * @return 图片列表
     */
    List<ImageInfo> getImages(Integer userId);

    /**
     * 获取图片信息
     * @param userId 用户 Id
     * @param hash 哈希值
     * @return 图片信息
     */
    ImageInfo getImage(Integer userId, String hash);

    /**
     * 添加图片
     * @param info 图片信息
     * @return 操作成功的行数
     */
    Integer insertImage(ImageInfo info);

    /**
     * 删除图片
     * @param id 图片 Id
     * @return 操作成功的行数
     */
    Integer deleteImage(Integer id);

}

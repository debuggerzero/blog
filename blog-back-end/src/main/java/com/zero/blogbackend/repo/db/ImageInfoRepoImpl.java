package com.zero.blogbackend.repo.db;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zero.blogbackend.model.po.ImageInfo;
import com.zero.blogbackend.repo.ImageInfoRepo;
import com.zero.blogbackend.repo.mapper.ImageInfoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ImageInfoRepoImpl
 *
 * @author ZERO
 * @date 2023/2/3
 */
@RequiredArgsConstructor
@Repository("imageInfoRepoImpl")
public class ImageInfoRepoImpl implements ImageInfoRepo {

    private final ImageInfoMapper imageInfoMapper;

    @Override
    public List<ImageInfo> getImages(Integer userId) {

        LambdaQueryWrapper<ImageInfo> queryWrapper = new LambdaQueryWrapper<ImageInfo>()
                .eq(ImageInfo::getCreateUser, userId);

        return imageInfoMapper.selectList(queryWrapper);
    }

    @Override
    public ImageInfo getImage(Integer userId, String hash) {

        LambdaQueryWrapper<ImageInfo> queryWrapper = new LambdaQueryWrapper<ImageInfo>()
                .eq(ImageInfo::getImageHash, hash)
                .eq(ImageInfo::getCreateUser, userId);

        return imageInfoMapper.selectOne(queryWrapper);
    }

    @Override
    public Integer insertImage(ImageInfo info) {
        return imageInfoMapper.insert(info);
    }

    @Override
    public Integer deleteImage(Integer id) {
        return imageInfoMapper.deleteById(id);
    }
}

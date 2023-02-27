package com.zero.blogbackend.service;

import com.zero.blogbackend.exception.AssertionException;
import com.zero.blogbackend.model.po.ImageInfo;
import com.zero.blogbackend.model.vo.ImageInfoVO;
import com.zero.blogbackend.repo.ImageInfoRepo;
import com.zero.blogbackend.utils.StringUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.util.Objects;

/**
 * FileOperationService
 *
 * @author ZERO
 * @date 2023/1/30
 */
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class FileOperationService {

    private final CosService cosService;

    @Resource(name = "imageInfoRepoImpl")
    private final ImageInfoRepo imageInfoRepo;

    private String getFilename(String contentType) {
        if (Objects.isNull(contentType)) {
            throw new AssertionException(500002, "图片上传失败");
        }
        return StringUtil.uuid() + "." + contentType.substring(contentType.lastIndexOf("/") + 1);
    }

    private String getImageHash(InputStream inputStream) {
        String hash;
        try {
            hash = DigestUtils.md5Hex(inputStream);
        } catch (IOException e) {
            throw new AssertionException(500005, e.getMessage());
        }
        return hash;
    }

    private ImageInfoVO insertImage(
            Integer id,
            String name,
            String imageHash,
            InputStream inputStream,
            long contentLength
    ) {
        String key = id + "/assets/" + name;
        Integer length = insertImage(
                new ImageInfo(null, name, key, imageHash, id, StringUtil.getCurrentTime())
        );
        if (length == 0) {
            throw new AssertionException(500005, "上传失败");
        }
        key = cosService.putObject(inputStream, key, contentLength);
        return new ImageInfoVO(cosService.getImageUrl(key), name);
    }

    private ByteArrayOutputStream cloneInputStream(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            outputStream.write(inputStream.readAllBytes());
            outputStream.flush();
        } catch (IOException e) {
            throw new AssertionException(500005, e.getMessage());
        }
        return outputStream;
    }

    public Integer insertImage(ImageInfo imageInfo) {
        return imageInfoRepo.insertImage(imageInfo);
    }

    public ImageInfoVO uploadImage(MultipartFile multipartFile, Integer id) {

        InputStream inputStream;
        long contentLength;
        try {
            inputStream = multipartFile.getInputStream();
            contentLength = multipartFile.getResource().contentLength();
        } catch (IOException e) {
            throw new AssertionException(500002, "图片上传失败");
        }
        ByteArrayOutputStream outputStream = cloneInputStream(inputStream);
        inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        String imageHash = getImageHash(inputStream);
        ImageInfo imageInfo = imageInfoRepo.getImage(id, imageHash);

        ImageInfoVO imageInfoVO;
        if (Objects.nonNull(imageInfo)) {
            imageInfoVO = new ImageInfoVO(cosService.getImageUrl(imageInfo.getImagePath()), imageInfo.getImageName());
        } else {
            String filename = getFilename(multipartFile.getContentType());
            inputStream = new ByteArrayInputStream(outputStream.toByteArray());
            return insertImage(id, filename, imageHash, inputStream, contentLength);
        }

        return imageInfoVO;
    }

}

package com.zero.blogbackend.service;

import com.zero.blogbackend.config.CosConfig;
import com.zero.blogbackend.exception.AssertionException;
import com.zero.blogbackend.model.vo.ImageInfoVO;
import com.zero.blogbackend.utils.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * FileOperationService
 *
 * @author ZERO
 * @date 2023/1/30
 */
@Service
@RequiredArgsConstructor
public class FileOperationService {

    private final CosService cosService;

    private final CosConfig cosConfig;

    private String getFilename(String contentType) {
        if (Objects.isNull(contentType)) {
            throw new AssertionException(500002, "图片上传失败");
        }
        return StringUtil.uuid() + "." + contentType.substring(contentType.lastIndexOf("/") + 1);
    }

    public ImageInfoVO uploadImage(MultipartFile multipartFile, String id) {
        String filename = getFilename(multipartFile.getContentType());
        try {
            InputStream inputStream = multipartFile.getInputStream();
            long contentLength = multipartFile.getResource().contentLength();
            String key = id + "/assets/" + filename;
            key = cosService.putObject(inputStream, key, contentLength);
            return new ImageInfoVO(cosConfig.getUrl() + key, filename);
        } catch (IOException e) {
            throw new AssertionException(500002, "图片上传失败");
        }
    }

}

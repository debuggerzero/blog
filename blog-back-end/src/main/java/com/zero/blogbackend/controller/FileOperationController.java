package com.zero.blogbackend.controller;

import com.zero.blogbackend.exception.AssertionException;
import com.zero.blogbackend.model.vo.ImageInfoVO;
import com.zero.blogbackend.service.FileOperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

/**
 * UploadPictureController
 *
 * @author ZERO
 * @date 2023/1/30
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
public class FileOperationController {

    private final FileOperationService fileOperationService;

    @PostMapping("/upload/image/{id}")
    public ImageInfoVO uploadImage(@RequestPart(value = "file") MultipartFile multipartFile, @PathVariable String id) {

        if (Objects.isNull(multipartFile)) {
            throw new AssertionException(500002, "图片上传失败");
        }

        return fileOperationService.uploadImage(multipartFile, id);
    }

}

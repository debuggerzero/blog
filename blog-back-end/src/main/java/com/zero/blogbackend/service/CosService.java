package com.zero.blogbackend.service;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.transfer.TransferManager;
import com.qcloud.cos.transfer.TransferManagerConfiguration;
import com.zero.blogbackend.config.CosConfig;
import com.zero.blogbackend.exception.AssertionException;
import com.zero.blogbackend.utils.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Cos Service
 *
 * @author ZERO
 * @date 2023/1/17
 */
@Service
@RequiredArgsConstructor
public class CosService {

    private final CosConfig cosConfig;

    public COSClient getCosClient() {
        COSCredentials cred = new BasicCOSCredentials(cosConfig.getSecretId(), cosConfig.getSecretKey());
        Region region = new Region(cosConfig.getReginName());
        ClientConfig clientConfig = new ClientConfig(region);
        return new COSClient(cred, clientConfig);
    }

    public void shutdownCosClient(COSClient cosClient) {
        cosClient.shutdown();
    }

    public TransferManager getTransferManager() {
        COSClient cosClient = getCosClient();
        ExecutorService threadPool = Executors.newFixedThreadPool(32);
        TransferManager transferManager = new TransferManager(cosClient, threadPool);
        TransferManagerConfiguration transferManagerConfiguration = new TransferManagerConfiguration();
        transferManagerConfiguration.setMultipartUploadThreshold(5 * 1024 * 1024);
        transferManagerConfiguration.setMinimumUploadPartSize(1024 * 1024);
        transferManager.setConfiguration(transferManagerConfiguration);
        return transferManager;
    }

    public void shutdownTransferManager(TransferManager transferManager) {
        transferManager.shutdownNow(true);
    }

    /**
     * 上传文章
     * @param article 文章内容
     * @param key 键
     * @return 文章存储路径
     */
    public String putArticle(String article, String key) {

        String path = key + ".json";
        TransferManager transferManager = getTransferManager();
        InputStream inputStream = new ByteArrayInputStream(article.getBytes());

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(article.getBytes().length);

        PutObjectRequest putObjectRequest =
                new PutObjectRequest(cosConfig.getBucketName(), cosConfig.getPrefix() + path, inputStream, objectMetadata);

        putObjectRequest.setStorageClass(StorageClass.Standard);

        try {
            UploadResult uploadResult = transferManager.upload(putObjectRequest).waitForUploadResult();
        }
        catch (Exception e) {
            throw new AssertionException(500002, "文件上传失败");
        }
        finally {
            shutdownTransferManager(transferManager);
        }

        return path;
    }

}

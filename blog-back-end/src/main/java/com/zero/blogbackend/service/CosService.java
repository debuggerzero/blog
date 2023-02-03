package com.zero.blogbackend.service;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.transfer.TransferManager;
import com.qcloud.cos.transfer.TransferManagerConfiguration;
import com.qcloud.cos.utils.IOUtils;
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
        InputStream inputStream = new ByteArrayInputStream(article.getBytes());
        return putObject(inputStream, key, (long) article.getBytes().length);
    }

    /**
     * 上传 Object
     * @param inputStream 数据流
     * @param key 键
     * @param contentLength 内容长度
     * @return key
     */
    public String putObject(InputStream inputStream, String key, Long contentLength) {

        TransferManager transferManager = getTransferManager();

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(contentLength);

        PutObjectRequest putObjectRequest =
                new PutObjectRequest(cosConfig.getBucketName(), cosConfig.getPrefix() + key, inputStream, objectMetadata);
        putObjectRequest.setStorageClass(StorageClass.Standard);

        try {
            UploadResult uploadResult = transferManager.upload(putObjectRequest).waitForUploadResult();
            return uploadResult.getKey();
        }
        catch (Exception e) {
            throw new AssertionException(500002, "文件上传失败");
        }
        finally {
            shutdownTransferManager(transferManager);
        }
    }

    public String getObject(String key) {

        COSClient cosClient = getCosClient();

        GetObjectRequest getObjectRequest =
                new GetObjectRequest(cosConfig.getBucketName(), cosConfig.getPrefix() + key);
        COSObjectInputStream cosObjectInput = null;
        byte[] bytes = null;

        try {
            COSObject cosObject = cosClient.getObject(getObjectRequest);
            COSObjectInputStream objectContent = cosObject.getObjectContent();
            bytes = IOUtils.toByteArray(objectContent);
        }
        catch (Exception e) {
            throw new AssertionException(500004, "文章获取失败");
        } finally {
            shutdownCosClient(cosClient);
        }

        return new String(bytes);
    }

    public void deleteObject(String key) {
        COSClient cosClient = getCosClient();
        try {
            cosClient.deleteObject(cosConfig.getBucketName(), cosConfig.getPrefix() + key);
        }
        catch (Exception e) {
            throw new AssertionException(500004, "删除失败");
        } finally {
            shutdownCosClient(cosClient);
        }
    }

    public String getArticleKey(String userId, String articleId) {
        return userId + "/" + articleId + ".md";
    }

    public String getImageUrl(String imageKey) {
        return cosConfig.getUrl() + cosConfig.getPrefix() + imageKey;
    }

    public String getImageKey(String imageUrl) {
        return imageUrl.replaceAll(cosConfig.getUrl(), "").replaceAll(cosConfig.getPrefix(), "");
    }
}

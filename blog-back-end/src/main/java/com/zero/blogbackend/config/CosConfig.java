package com.zero.blogbackend.config;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.transfer.TransferManager;
import com.qcloud.cos.transfer.TransferManagerConfiguration;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Cos 对象存储配置
 *
 * @author ZERO
 * @date 2023/1/17
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "cos")
public class CosConfig {

    private String secretId;

    private String secretKey;

    private String reginName;

    private String bucketName;

    private String prefix;

}

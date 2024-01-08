package com.greathealth.greathealth.config;

import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class OSSUtil {

    private final MinioClient minioClient;


    /**
     * 判断桶是否存在
     */
    public boolean isBucker(String bucketName)
            throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException,
            NoSuchAlgorithmException, ServerException, InternalException, XmlParserException,
            ErrorResponseException {
        return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
    }

    /**
     * 如果桶不存在，则创建
     */
    public boolean mkdirBucket(String bucketName)
            throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException,
            NoSuchAlgorithmException, ServerException, InternalException, XmlParserException,
            ErrorResponseException {
        // 如存储桶不存在，创建之。
        boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (!found) {
            // 创建名为bucketName的存储桶。
            minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(bucketName)
                    .build());
            return true;
        } else {
            return false;
        }
    }

    /**
     * 列出所有存储桶的名称
     */
    public List<String> listBuckets()
            throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException,
            NoSuchAlgorithmException, ServerException, InternalException, XmlParserException,
            ErrorResponseException {
        List<Bucket> bucketList = minioClient.listBuckets();
        List<String> bucketListName = new ArrayList<>();
        for (Bucket bucket : bucketList) {
            bucketListName.add(bucket.name());
        }
        return bucketListName;
    }

    /**
     * 生成一个给HTTP GET请求用的presigned URL。浏览器/移动端的客户端可以用这个URL进行下载，即使其所在的存储桶是私有的。这个presigned
     * URL可以设置一个失效时间，默认值是7天。
     *
     * <p>*
     */
    public String getObjectURL(String bucketName, String objectName, Integer expires)
            throws Exception {
//        log.info("bucketName   " + bucketName + "   objectName   " + objectName);
        return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .expiry(expires, TimeUnit.DAYS)
                        .method(Method.GET)
                        .build());
    }

    public ResponseEntity<byte[]> getObject(String url) throws URISyntaxException {
//        log.info(url);
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setReadTimeout(100 * 1000);
        requestFactory.setConnectTimeout(100 * 1000);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        URI uri = new URI(url);
        return restTemplate.getForEntity(uri, byte[].class);
    }

    // 无法使用，官方文档有问题
    public byte[] getObject(String bucket, String objectName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        System.out.println("bucket = " + bucket + "objectName=" + objectName);
        int available = minioClient.getObject(GetObjectArgs.builder()
                .bucket(bucket)
                .object(objectName)
                .build()).available();
        System.out.println("available = " + available);
        byte[] bytes = new byte[available];
        int read = minioClient.getObject(GetObjectArgs.builder()
                .bucket(bucket)
                .object(objectName)
                .build()).read(bytes);
        System.out.println("read = " + read);
        System.out.println("bytes = " + bytes.length);
        return bytes;
    }

    /**
     * 删除文件
     */
    public void deleteFile(String bucketName, String fileName)
            throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException,
            NoSuchAlgorithmException, ServerException, InternalException, XmlParserException,
            ErrorResponseException {
        minioClient.removeObject(
                RemoveObjectArgs.builder().bucket(bucketName).object(fileName).build());
    }

    public void putFile(String bucketName, String fileName, InputStream inputStream, long size)
            throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException,
            NoSuchAlgorithmException, ServerException, InternalException, XmlParserException,
            ErrorResponseException {
        minioClient.putObject(
                PutObjectArgs.builder().bucket(bucketName).object(fileName).stream(inputStream, size, -1)
                        .build());
    }
}

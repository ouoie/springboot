package com.greathealth.greathealth.service.impl;


import com.greathealth.greathealth.config.MinioConfig;
import com.greathealth.greathealth.config.OSSUtil;
import com.greathealth.greathealth.service.FileService;
import com.greathealth.greathealth.common.core.domain.AjaxMapResult;
import com.greathealth.greathealth.common.core.domain.AjaxResult;
import com.greathealth.greathealth.utils.NewDateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileOSSServiceImpl implements FileService {


    private final OSSUtil ossUtil;

    private final MinioConfig minioConfig;

    @Override
    public AjaxResult upload(MultipartFile file) {
        try {
            String bucket = minioConfig.getBucket();
            // 上传文件路径
            if (!ossUtil.isBucker(bucket)) {
                ossUtil.mkdirBucket(bucket);
            }
            String path = NewDateUtils.localDateToString(LocalDate.now(), "YYYY/MM/dd/");
            String filename = file.getOriginalFilename();
            String fileFormat = ".jpg";
            if (StringUtils.hasText(filename)) {
                int index = filename.lastIndexOf(".");
                if (index > 0) {
                    fileFormat = filename.substring(index);
                }
            }
            filename = path + UUID.randomUUID() + fileFormat;
            ossUtil.putFile(bucket, filename, file.getInputStream(), file.getSize());
            String url = ossUtil.getObjectURL(bucket, filename, 7);
            return AjaxMapResult.success().put("url", url.substring(0, url.indexOf("?")));


        } catch (Exception e) {
            log.error("文件上传失败", e);
            return AjaxResult.error("上传失败");
        }
    }

    @Override
    public ResponseEntity<byte[]> fileDownload(String url) {
        try {
            return ossUtil.getObject(url);
        } catch (Exception e) {
            log.error("下载文件失败", e);
        }
        return null;
    }

}

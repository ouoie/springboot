package com.greathealth.greathealth.controller;

import com.greathealth.greathealth.common.annotation.CanRepeatSubmit;
import com.greathealth.greathealth.service.FileService;
import com.greathealth.greathealth.common.core.controller.BaseController;
import com.greathealth.greathealth.common.core.domain.AjaxResult;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/common")
@Api(tags = "文件操作模块")
public class FileController extends BaseController {
    private final FileService fileService;

    /**
     * 通用上传请求（单个）
     */
    @PostMapping("/upload")
    @CanRepeatSubmit
    public AjaxResult uploadFile(MultipartFile file) {
        return fileService.upload(file);
    }


    @GetMapping("/download")
    public ResponseEntity<byte[]> fileDownload(String url) {
        return fileService.fileDownload(url);
    }
}

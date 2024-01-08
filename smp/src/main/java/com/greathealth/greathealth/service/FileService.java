package com.greathealth.greathealth.service;

import com.greathealth.greathealth.common.core.domain.AjaxResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;


public interface FileService {

    AjaxResult upload(MultipartFile file);

    ResponseEntity<byte[]> fileDownload(String url);


}

package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/admin/common")
public class CommonController {

    @Autowired
    private AliOssUtil aliOssUtil;

    @PostMapping("/upload")
    public Result<String> upload(@RequestParam("file") MultipartFile file) {
        log.info("文件上传:{}", file);
        try {
            //获取原始文件名
            String originalFilename = file.getOriginalFilename();
            //截取拓展名
            int index = originalFilename.lastIndexOf(".");
            String suffix = originalFilename.substring(index);
            //使用uuid拼接新的文件名
            String uuid = UUID.randomUUID().toString();
            String newFileName = uuid + suffix;
            //上传
            String upload = aliOssUtil.upload(file.getBytes(), newFileName);
            //返回请求路径
            return Result.success(upload);
        } catch (IOException e) {
            //throw new RuntimeException(e);
            log.error("文件上传失败:{}",e.getMessage());
        }
        return Result.error(MessageConstant.UPLOAD_FAILED);
    }
}



package org.example.dify_test.Controller;

import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.example.dify_test.Service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
public class FileUploadController {

    private FileUploadService fileUploadService;

    @Autowired
    public FileUploadController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }
    // 单文件上传接口
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file,@RequestParam("user") String user,@RequestHeader("Authorization") String Authorization) {
        // 判断是否为空
//        if (file.isEmpty()) {
//            return "上传失败：文件为空";
//        }
//
//        // 目标保存路径（记得确保文件夹存在）
//        String fileName = file.getOriginalFilename();
//        System.out.println(fileName);
        try {
            String result = fileUploadService.uploadFile(file, Authorization,user);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("转发失败: " + e.getMessage());
        }
    }
}

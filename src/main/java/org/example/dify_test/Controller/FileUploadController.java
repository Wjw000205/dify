package org.example.dify_test.Controller;

import org.example.dify_test.Service.FileUploadService;
import com.alibaba.fastjson2.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.example.dify_test.common.Result;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@RestController
public class FileUploadController {

    private FileUploadService fileUploadService;

    @Autowired
    public FileUploadController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }
    // 单文件上传接口
    @PostMapping("/upload")
    public Result<JSONObject> uploadFile(@RequestParam("file") MultipartFile file,
                                         @RequestParam("user") String user,
                                         @RequestHeader("Authorization") String Authorization) {
        try {
            Path uploadPath = Paths.get("E:\\Java Progress\\dify\\files");
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String safeFileName = Paths.get(file.getOriginalFilename()).getFileName().toString();
            Path target = uploadPath.resolve(safeFileName);

            // 保存到持久化路径
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

            // 把持久化后的路径传给 Service
            JSONObject result = fileUploadService.uploadFile(target, Authorization, user);
            return Result.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("上传失败: " + e.getMessage());
        }
    }


    @GetMapping("/results/{task_id}")
    public Result<JSONObject> getTaskRes(@PathVariable("task_id") String taskID) {
        JSONObject result = fileUploadService.getTaskRes(taskID);
        return Result.success(result);
    }
}

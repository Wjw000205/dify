package org.example.dify_test.Controller;

import net.minidev.json.JSONObject;
import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.example.dify_test.Service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.demo.common.Result;
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
    public Result<JSONObject> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("user") String user, @RequestHeader("Authorization") String Authorization) {
        try {
            JSONObject result = fileUploadService.uploadFile(file, Authorization,user);
            return Result.success(result);
        } catch (Exception e) {
            return Result.fail("获取数据失败");
        }
    }

    @GetMapping("/results/{task_id}")
    public Result<JSONObject> getTaskRes(@PathVariable("task_id") String taskID) {
        JSONObject result = fileUploadService.getTaskRes(taskID);
        return Result.success(result);
    }
}

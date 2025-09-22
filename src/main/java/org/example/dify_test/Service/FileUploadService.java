package org.example.dify_test.Service;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.web.bind.annotation.RequestParam;

import java.nio.file.Path;

public interface FileUploadService{
    JSONObject uploadFile(Path file, String Authorization, String user);

    JSONObject getTaskRes(@RequestParam("task_id") String taskID);
}

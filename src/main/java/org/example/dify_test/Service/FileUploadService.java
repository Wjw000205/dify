package org.example.dify_test.Service;

import net.minidev.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService{
    JSONObject uploadFile(MultipartFile file, String Authorization, String user);

    JSONObject getTaskRes(@RequestParam("task_id") String taskID);
}

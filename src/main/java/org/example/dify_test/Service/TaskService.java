package org.example.dify_test.Service;


import com.alibaba.fastjson2.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.HashMap;

@Service
public interface TaskService {
    public void runTask(Path file, String Authorization, String user, String taskID, HashMap<String, JSONObject> resMap, HashMap<String, Integer> keyMap);
}

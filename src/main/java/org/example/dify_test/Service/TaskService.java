package org.example.dify_test.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

@Service
public interface TaskService {
    public void runTask(MultipartFile file, String Authorization, String user, String taskID, HashMap<String, String> resMap, HashMap<String, Integer> keyMap);
}

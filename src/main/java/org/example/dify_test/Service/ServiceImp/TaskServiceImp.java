package org.example.dify_test.Service.ServiceImp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dify_test.Service.TaskService;
import org.example.dify_test.Util.ChatPostClient;
import org.example.dify_test.Util.MultipartPostClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

@Service
public class TaskServiceImp implements TaskService {
    @Async
    public void runTask(MultipartFile file, String Authorization, String user, String taskID, HashMap<String, String> resMap, HashMap<String, Integer> keyMap) {
        System.out.println("异步线程开始执行任务...");
        String uploadUrl = "https://dify.chenbingyuan.com/v1/files/upload";  // 可配置到 yml 中
        String chatUrl = "https://dify.chenbingyuan.com/v1/chat-messages";
        keyMap.put(taskID, 0);
        try {
            String res = MultipartPostClient.postFileAndText(uploadUrl, file, user, Authorization);
            System.out.println(res);
            //读取文件id
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(res);
            String fileId = jsonNode.get("id").asText();
//            System.out.println(fileId);

            String chatRes = ChatPostClient.postJson(chatUrl, fileId, user, Authorization);
            keyMap.put(taskID,1);
            resMap.put(taskID,chatRes);
            System.out.println(chatRes);
        } catch (Exception e) {
            keyMap.put(taskID, -1);
            Thread.currentThread().interrupt();
        }
        System.out.println("异步任务执行完毕！");
    }
}

package org.example.dify_test.Service.ServiceImp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.dify_test.Service.TaskService;
import org.example.dify_test.Util.ChatPostClient;
import org.example.dify_test.Util.MultipartPostClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson2.JSONObject;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
public class TaskServiceImp implements TaskService {
    @Async
    public void runTask(Path file, String Authorization, String user,
                        String taskID, HashMap<String, JSONObject> resMap,
                        HashMap<String, Integer> keyMap) {
        System.out.println("异步线程开始执行任务...");
        String uploadUrl = "http://82.156.159.53:30001/v1/files/upload";
        String chatUrl = "http://82.156.159.53:30001/v1/chat-messages";

        keyMap.put(taskID, 0);

        try {
            // 上传文件
            String res = MultipartPostClient.postFileAndText(uploadUrl, file, user, Authorization);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(res);
            String fileId = jsonNode.get("id").asText();
//            System.out.println(fileId);

            // 调用 chat 接口
            String chatRes = ChatPostClient.postJson(chatUrl, fileId, user, Authorization);

            if (chatRes == null || chatRes.isEmpty()) {
                keyMap.put(taskID, -1);
                resMap.put(taskID, new JSONObject() {{
                    put("error", "接口返回为空");
                }});
            } else {
                List<String> answers = new ArrayList<>();

                for (String line : chatRes.split("\n")) {
                    line = line.trim();
                    if (line.startsWith("data:")) {
                        String jsonStr = line.substring(5).trim();

                        // 忽略空行或 [DONE]
                        if (jsonStr.isEmpty() || "[DONE]".equals(jsonStr)) {
                            continue;
                        }

                        try {
                            JSONObject obj = JSONObject.parseObject(jsonStr);

                            // 只保留 event=message 的 answer
                            if ("message".equals(obj.getString("event"))) {
                                String answer = obj.getString("answer");
                                if (answer != null) {
                                    answers.add(answer);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                if (!answers.isEmpty()) {
                    keyMap.put(taskID, 1);
                    // 只返回答案列表
                    resMap.put(taskID, new JSONObject() {{
                        put("answers", answers);
                    }});
                } else {
                    keyMap.put(taskID, -1);
                    resMap.put(taskID, new JSONObject() {{
                        put("error", "没有解析到任何 answer");
                        put("raw", chatRes);
                    }});
                }
            }

        } catch (Exception e) {
            keyMap.put(taskID, -1);
            log.error("异步任务执行出错", e);
        }

        System.out.println("异步任务执行完毕！");
    }


}

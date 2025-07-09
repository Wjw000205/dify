package org.example.dify_test.Util;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.JSONPObject;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ChatPostClient {

//    {
//        "inputs": {},
//        "query": "What are the specs of the iPhone 13 Pro Max?",
//            "response_mode": "streaming",
//            "conversation_id": "",
//            "user": "abc-123",
//            "files": [
//        {
//            "type": "image",
//                "transfer_method": "remote_url",
//                "url": "https://cloud.dify.ai/logo/logo-site.png"
//        }
//    ]
//    }


    /**
     * 发送POST请求，请求体是JSON字符串，支持自定义请求头
     *
     * @param url        请求URL
     * @param fileId    文件id
     * @param user    用户id
     * @param Authorization  用户的Authorization
     * @return 服务器响应的字符串
     * @throws Exception 发送请求异常
     */
    public static String postJson(String url, String fileId, String user, String Authorization) throws Exception {

        String query = "这是一个任务书";

        String response_mode = "streaming";

        String conversation_id = "";

        JSONObject jsonNode = new JSONObject();
        JSONObject temp = new JSONObject();
        JSONObject inputs = new JSONObject();
        JSONArray files = new JSONArray();
        // 请求头
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", Authorization);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Content_type","application/json");

        //文件json
        temp.put("type","document");
        temp.put("transfer_method","local_file");
        temp.put("upload_file_id",fileId);
        temp.put("url","");
        files.add(temp);


        //请求体
        jsonNode.put("inputs", inputs);
        jsonNode.put("query", query);
        jsonNode.put("response_mode", response_mode);
        jsonNode.put("conversation_id", conversation_id);
        jsonNode.put("user", user);
        jsonNode.put("files", files);

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(new URI(url))
                .POST(HttpRequest.BodyPublishers.ofString(jsonNode.toString()))
                .header("Content-Type", "application/json")
                .headers("Authorization",Authorization);


        HttpRequest request = requestBuilder.build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }
}

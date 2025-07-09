package org.example.dify_test.Service.ServiceImp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.example.dify_test.Service.FileUploadService;
import org.example.dify_test.Util.ChatPostClient;
import org.example.dify_test.Util.MultipartPostClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileUploadServiceImp implements FileUploadService {

    @Override
    public String uploadFile(MultipartFile file,String Authorization,String user) {
        String uploadUrl = "https://dify.chenbingyuan.com/v1/files/upload";  // 可配置到 yml 中
        String chatUrl = "https://dify.chenbingyuan.com/v1/chat-messages";
        try {
            String res =  MultipartPostClient.postFileAndText(uploadUrl, file, user,Authorization);
            System.out.println(res);
            //读取文件id
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(res);
            String fileId = jsonNode.get("id").asText();
//            System.out.println(fileId);

            String chatRes = ChatPostClient.postJson(chatUrl,fileId,user,Authorization);
            System.out.println(chatRes);
            return chatRes;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

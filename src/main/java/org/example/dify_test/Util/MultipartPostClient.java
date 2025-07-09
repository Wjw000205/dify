package org.example.dify_test.Util;

import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class MultipartPostClient {
    /**
     * 发起 multipart/form-data POST 请求
     * @param url        请求地址（目标接口）
     * @param file       前端上传的文件（MultipartFile）
     * @param user       用户标识
     * @return           目标服务的响应内容（字符串）
     * @throws IOException 文件读取异常
     */
    public static String postFileAndText(String url, MultipartFile file, String user,String Authorization) throws IOException {
        RestTemplate restTemplate = new RestTemplate();

        // 请求头
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", Authorization);
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // multipart 请求体
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        // 封装文件
        InputStreamResource fileResource = new InputStreamResource(file.getInputStream()) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }

            @Override
            public long contentLength() throws IOException {
                return file.getSize();
            }
        };

        body.add("file", fileResource);           // 文件字段名为 "file"
        body.add("user", user);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // 发起 POST 请求
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);

        return response.getBody(); // 返回响应体字符串
    }
}

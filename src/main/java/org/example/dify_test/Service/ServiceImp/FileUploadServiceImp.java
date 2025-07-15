package org.example.dify_test.Service.ServiceImp;
import net.minidev.json.JSONObject;
import org.example.dify_test.Service.FileUploadService;
import org.example.dify_test.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.HashMap;
import java.util.UUID;

@Service
public class FileUploadServiceImp implements FileUploadService {

    private TaskService taskService;

    @Autowired
    public FileUploadServiceImp(TaskService taskService) {
        this.taskService = taskService;
    }

    //用于临时存储接口返回的数据
    public HashMap<String, String> resMap = new HashMap<>();
    //用于存储接口的状态，0表示未完成，1表示接口访问正常，-1表示接口访问异常
    public HashMap<String, Integer> keyMap = new HashMap<>();

    @Override
    public JSONObject uploadFile(MultipartFile file, String Authorization, String user) {
        String taskId = UUID.randomUUID().toString();
        taskService.runTask(file, Authorization, user, taskId, resMap, keyMap);
        JSONObject res = new JSONObject();
        res.put("taskId", taskId);
        return res;
    }

    @Override
    public JSONObject getTaskRes(String taskID) {
        JSONObject res = new JSONObject();
        Integer value = keyMap.get(taskID);
        if(value!=null&&value==1){
            res.put("state",1);
            res.put("msg","数据查询成功");
            res.put("result",resMap.get(taskID));
        }
        else if(value!=null&&value==0){
            res.put("state",0);
            res.put("msg","报告正在生成，请稍后");
        }
        else{
            res.put("state",-1);
            res.put("msg","系统错误，请稍后重试");
        }
        return res;
    }
}

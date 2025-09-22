package org.example.dify_test.common;

public class Result<T> {
    private Integer code;    // 状态码
    private String msg;      // 提示信息
    private T data;          // 返回数据

    // 构造器私有，使用静态方法创建
    private Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    // 成功
    public static <T> Result<T> success(T data) {
        return new Result<>(200, "成功", data);
    }

    // 成功 - 无数据
    public static <T> Result<T> success() {
        return new Result<>(200, "成功", null);
    }

    // 失败
    public static <T> Result<T> fail(String msg) {
        return new Result<>(500, msg, null);
    }

    // 自定义
    public static <T> Result<T> of(Integer code, String msg, T data) {
        return new Result<>(code, msg, data);
    }

    // Getter & Setter（可省略，或使用lombok）
    public Integer getCode() { return code; }
    public String getMsg() { return msg; }
    public T getData() { return data; }

    public void setCode(Integer code) { this.code = code; }
    public void setMsg(String msg) { this.msg = msg; }
    public void setData(T data) { this.data = data; }
}

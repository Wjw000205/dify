package org.example.dify_test.Util;

import java.util.UUID;

public class TaskIDGen {

    /**
     * 生成带前缀的唯一ID（推荐用于业务系统）
     * 例如：ORDER_20250715183245_550e8400e29b41d4a716446655440000
     */
    public static String generateWithPrefix(String prefix) {
        return prefix + "_" + getTimestamp() + "_" + getUUID();
    }

    /**
     * 生成不带前缀的唯一ID
     * 例如：20250715183245_550e8400e29b41d4a716446655440000
     */
    public static String generate() {
        return getTimestamp() + "_" + getUUID();
    }

    /**
     * 获取当前时间戳字符串，格式：yyyyMMddHHmmss
     */
    private static String getTimestamp() {
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return java.time.LocalDateTime.now().format(formatter);
    }

    /**
     * 获取一个无横线的UUID
     */
    private static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}

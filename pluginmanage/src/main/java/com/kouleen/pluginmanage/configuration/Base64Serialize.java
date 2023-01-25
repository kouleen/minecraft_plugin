package com.kouleen.pluginmanage.configuration;

import java.io.*;
import java.util.Base64;

/**
 * @author zhangqing
 * @since 2023/1/24 23:22
 */
public class Base64Serialize implements Serialize{

    public String encoderBase64(Object object) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ObjectOutputStream output = new ObjectOutputStream(outputStream);
            output.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] bytes = outputStream.toByteArray();
        return encoderBase64(bytes);
    }

    public <T> T decoderBase64(String base64, Class<T> type) {
        byte[] bytes = decoderBase64(base64);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        try {
            ObjectInputStream input = new ObjectInputStream(inputStream);
            return type.cast(input.readObject());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 可以用<code>encoderBase64(String.getBytes())</code> 来获取字符串的Base64编码
     *
     * @param bytes 字节
     * @return Base64编码
     */
    public String encoderBase64(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    public byte[] decoderBase64(String base64) {
        return Base64.getDecoder().decode(base64);
    }

    @Override
    public String serializeObject(Object object) {
        return encoderBase64(object);
    }

    @Override
    public void serializeObject(Object object, Writer writer) {
        FileUtil.writeString(writer, encoderBase64(object));
    }

    @Override
    public <T> T deserializeObject(String serialize, Class<T> type) {
        return decoderBase64(serialize, type);
    }

    @Override
    public <T> T deserializeObject(InputStream serialize, Class<T> type) {
        return decoderBase64(FileUtil.readString(serialize).toString(), type);
    }

    @Override
    public <T> T deserializeObject(Reader serialize, Class<T> type) {
        return decoderBase64(FileUtil.readString(serialize).toString(), type);
    }
}

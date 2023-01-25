package com.kouleen.pluginmanage.configuration;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;

/**
 * @author zhangqing
 * @since 2023/1/24 23:20
 */
public class YamlSerialize implements Serialize {

    private static Yaml yaml;

    static {
        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        yaml = new Yaml(dumperOptions);
    }

    /**
     * 将一个JavaBean序列化成字符串
     *
     * @param object 对象
     * @return 字符串
     */
    @Override
    public String serializeObject(Object object) {
        return yaml.dump(object);
    }

    /**
     * 将一个JavaBean序列化成字符串写入文件
     *
     * @param object 对象
     */
    @Override
    public void serializeObject(Object object, Writer writer) {
        yaml.dump(object, writer);
    }

    /**
     * 将序列化字符串反序列化成一个JavaBean
     *
     * @param serialize 序列化的字符串
     * @param type      类型
     * @return 对象
     */
    @Override
    public <T> T deserializeObject(String serialize, Class<T> type) {
        return yaml.loadAs(serialize, type);
    }

    /**
     * 从文件读取序列化字符串反序列化成一个JavaBean
     *
     * @param serialize 输入流
     * @param type      类型
     * @return 对象
     */
    @Override
    public <T> T deserializeObject(InputStream serialize, Class<T> type) {
        return yaml.loadAs(serialize, type);
    }

    /**
     * 从文件读取序列化字符串反序列化成一个JavaBean
     *
     * @param serialize 字符流
     * @param type      类型
     * @return 对象
     */
    @Override
    public <T> T deserializeObject(Reader serialize, Class<T> type) {
        return yaml.loadAs(serialize, type);
    }
}

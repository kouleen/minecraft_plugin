package com.kouleen.pluginmanage.configuration;

import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;

/**
 * @author zhangqing
 * @since 2023/1/24 23:22
 */
public interface Serialize {

    YamlSerialize YAML_SERIALIZE = new YamlSerialize();
    Base64Serialize BASE_64_SERIALIZE = new Base64Serialize();

    String serializeObject(Object object);
    void serializeObject(Object object, Writer writer);

    <T> T deserializeObject(String serialize, Class<T> type);
    <T> T deserializeObject(InputStream serialize, Class<T> type);
    <T> T deserializeObject(Reader serialize, Class<T> type);
}

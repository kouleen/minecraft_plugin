package com.kouleen.pluginmanage.configuration;

import java.io.File;
import java.util.*;

/**
 * @author zhangqing
 * @since 2023/1/24 23:23
 */
public class Configuration {

    private String key;
    private Configuration root;
    private Configuration parent;
    private Map<String, Object> config;
    private File configFile;

    @SuppressWarnings("all")
    private Configuration(Object object, String key, Configuration root, Configuration parent) {
        this(null, object);
        this.root = root;
        this.parent = parent;
        this.key = key;

        if (parent.getConfig().get(key) == null)
            parent.getConfig().put(key, config);
    }

    @SuppressWarnings("unchecked")
    public Configuration(File configFile, Object config) {
        this.configFile = configFile;
        this.root = this;
        if (config instanceof Map)
            this.config = (Map<String, Object>) config;
        if (config == null)
            this.config = new LinkedHashMap<>();
    }

    public String getKey() {
        return key;
    }

    public Configuration getParent() {
        return parent;
    }

    public boolean hasParent() {
        return parent != null;
    }

    /**
     * 获取配置文件的根
     *
     * @return 根
     */
    public Configuration getRoot() {
        return root;
    }

    /**
     * 获取配置文件
     *
     * @return 如果是子节点 将返回null
     */
    public File getConfigFile() {
        return configFile;
    }

    /**
     * 获取配置文件的内容
     *
     * @return 返回配置内容
     */
    public Map<String, Object> getConfig() {
        return config;
    }

    /**
     * 获取配置文件内所有的Key
     *
     * @return 所有键
     */
    public Set<String> getKeys() {
        return config.keySet();
    }

    /**
     * 获取配置文件的节点
     *
     * @param key 键
     * @return 配置文件
     */
    public Configuration getConfiguration(String key) {
        int pointIndex = key.lastIndexOf('.');
        if (pointIndex != -1) {
            Configuration configuration = getConfiguration(key.substring(0, pointIndex));
            return configuration.getConfiguration(key.substring(pointIndex + 1));
        }
        return new Configuration(config.get(key), key, root, this);
    }

    public boolean hasKey(String key) {
        return config.containsKey(key);
    }

    public Object get(String key) {
        return config.get(key);
    }

    public Object get(String key, Object def) {
        int pointIndex = key.lastIndexOf('.');
        if (pointIndex != -1) {
            Configuration configuration = getConfiguration(key.substring(0, pointIndex));
            if (configuration.hasKey(key))
                return configuration.get(key.substring(pointIndex + 1), def);
        }
        return get(key) == null ? def : get(key);
    }

    public String getString(String key) {
        return getString(key, "");
    }

    public String getString(String key, String def) {
        Object val = get(key, def);
        return (val != null) ? val.toString() : def;
    }

    public byte getByte(String key) {
        return getByte(key, (byte) 0);
    }

    public byte getByte(String key, byte def) {
        Object val = get(key, def);
        return (val instanceof Number) ? ((Number) val).byteValue() : def;
    }

    public short getShort(String key) {
        return getShort(key, (short) 0);
    }

    public short getShort(String key, short def) {
        Object val = get(key, def);
        return (val instanceof Number) ? ((Number) val).shortValue() : def;
    }

    public int getInt(String key) {
        return getInt(key, 0);
    }

    public int getInt(String key, int def) {
        Object val = get(key, def);
        return (val instanceof Number) ? ((Number) val).intValue() : def;
    }

    public long getLong(String key) {
        return getLong(key, 0L);
    }

    public long getLong(String key, long def) {
        Object val = get(key, def);
        return (val instanceof Number) ? ((Number) val).longValue() : def;
    }

    public double getDouble(String key) {
        return getDouble(key, 0.0D);
    }

    public double getDouble(String key, double def) {
        Object val = get(key, def);
        return (val instanceof Number) ? ((Number) val).doubleValue() : def;
    }

    public float getFloat(String key) {
        return getFloat(key, 0.0F);
    }

    public float getFloat(String key, float def) {
        Object val = get(key, def);
        return (val instanceof Number) ? ((Number) val).floatValue() : def;
    }

    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean def) {
        Object val = get(key, def);
        return (val instanceof Boolean) ? (boolean) val : def;
    }

    public List<?> getList(String key) {
        return getList(key, new ArrayList<String>());
    }

    public List<?> getList(String key, List<?> def) {
        Object val = get(key, def);
        return (List<?>) ((val instanceof List) ? val : def);
    }

    public List<String> getStringList(String key) {
        List<?> list = getList(key);

        List<String> result = new ArrayList<>();

        for (Object object : list)
            result.add(object.toString());

        return result;
    }

    public void set(String key, Object object) {
        int pointIndex = key.lastIndexOf('.');
        if (pointIndex != -1) {
            Configuration configuration = getConfiguration(key.substring(0, pointIndex));
            configuration.set(key.substring(pointIndex + 1), object);
            return;
        }
        config.put(key, object);
    }

    public void asyncSave(){
        asyncSaveToFile(root.configFile);
    }

    public void asyncSaveToFile(File file){
        // 暂时不加
    }

    public void save() {
        saveToFile(root.configFile);
    }

    public void saveToFile(File file) {
        clearEmptyElements(config);
        Serialize.YAML_SERIALIZE.serializeObject(config, FileUtil.getWriter(file));
    }

    @SuppressWarnings("unchecked")
    private static void clearEmptyElements(Map<String, Object> config) {
        for (Map.Entry<String, Object> entry : config.entrySet()) {
            if (entry.getValue() instanceof Map) {
                if (((Map<?, ?>) entry.getValue()).isEmpty()) {
                    config.put(entry.getKey(), null);
                } else {
                    clearEmptyElements((Map<String, Object>) entry.getValue());
                }
            }
        }
    }
}

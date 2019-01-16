package com.xy.bizport.share.lib.settings;

import com.xy.bizport.androidcommon.util.TextUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ShareSettingsRepository {

    private static volatile ShareSettingsRepository instance;
    private Map<String, JSONObject> cache = new HashMap<>();
    private ShareSettingsDao settingsDao = new ShareSettingsDao();

    public static ShareSettingsRepository getInstance() {
        if (instance == null) {
            synchronized (ShareSettingsRepository.class) {
                if (instance == null) {
                    instance = new ShareSettingsRepository();
                }
            }
        }
        return instance;
    }

    private ShareSettingsRepository() {
    }

    public JSONObject get(String key) {
        if (TextUtils.isEmpty(key)) {
            return null;
        }

        if (!cache.containsKey(key)) {
            JSONObject setting = settingsDao.findById(key);
            cache.put(key, setting);
            return setting;
        }
        return cache.get(key);
    }

    public String getString(String key, String fallback) {
        JSONObject setting = get(key);
        return setting == null ? fallback : setting.optString(ShareSettingsDao.VALUE);
    }

    public int getInt(String key, int fallback) {
        JSONObject setting = get(key);
        return setting == null ? fallback : setting.optInt(ShareSettingsDao.VALUE, fallback);
    }

    public long getLong(String key, long fallback) {
        JSONObject setting = get(key);
        return setting == null ? fallback : setting.optLong(ShareSettingsDao.VALUE, fallback);
    }

    public double getDouble(String key, double fallback) {
        JSONObject setting = get(key);
        return setting == null ? fallback : setting.optDouble(ShareSettingsDao.VALUE, fallback);
    }

    public boolean getBoolean(String key, boolean fallback) {
        JSONObject setting = get(key);
        return setting == null ? fallback : setting.optBoolean(ShareSettingsDao.VALUE, fallback);
    }

    public boolean delete(String key) {
        try {
            cache.remove(key);
            settingsDao.deleteById(key);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public boolean save(String key, Object value) {
        try {
            if (value == null) {
                delete(key);
            }
            else {
                JSONObject setting = new JSONObject();
                setting.put(ShareSettingsDao.KEY, key);
                setting.put(ShareSettingsDao.VALUE, value);
                settingsDao.insertOrUpdate(setting);
                cache.put(key, setting);
            }
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public boolean save(Map<String, Object> params) {
        try {
            settingsDao.beginTransaction();
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (entry.getValue() != null) {
                    save(entry.getKey(), entry.getValue());
                }
            }
            settingsDao.setTransactionSuccessful();
            return true;
        }
        catch (Exception e) {
            return false;
        }
        finally {
            settingsDao.endTransaction();
        }
    }
}

package course.select.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // 保存資料
    public void save(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    // 保存資料並設置過期時間
    public void saveWithExpire(String key, String value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    // 獲取資料
    public String get(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    // 更新資料
    public void update(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    // 刪除資料
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    // 設置過期時間
    public void setExpire(String key, long timeout, TimeUnit unit) {
        redisTemplate.expire(key, timeout, unit);
    }
}

package course.selection.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // 保存數據
    public void save(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    // 獲取數據
    public String get(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    // 更新數據
    public void update(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    // 刪除數據
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    // 設置過期時間
    public void setExpire(String key, long timeout, TimeUnit unit) {
        redisTemplate.expire(key, timeout, unit);
    }
}

package spharos.settle.global.config.redis;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RedisUtil {

    private final StringRedisTemplate template;

    // 값 가져오기
    public String getData(String key) {
        ValueOperations<String, String> valueOperations = template.opsForValue();
        return valueOperations.get(key);
    }

    // 값 존재 확인
    public boolean existData(String key) {
        return Boolean.TRUE.equals(template.hasKey(key));
    }

    // 유효기간 설정해서 데이터 저장
    public void setDataExpire(String key, String value, long duration) {
        ValueOperations<String, String> valueOperations = template.opsForValue();
        Duration expireDuration = Duration.ofSeconds(duration);
        valueOperations.set(key, value, expireDuration);
    }

    public String getValueByKey(String key) {
        ValueOperations<String, String> valueOps = template.opsForValue();
        return valueOps.get(key);
    }

    // 데이터 삭제
    public void deleteData(String key) {
        template.delete(key);
    }

}

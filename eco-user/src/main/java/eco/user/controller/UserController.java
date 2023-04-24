package eco.user.controller;

import com.eco.common.redis.utils.RedisUtils;
import com.eco.common.result.Result;
import com.eco.common.web.util.resubmit.ResubmitCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yiren
 * @title: UserController
 * @description: TODO
 * @date 2023/4/23 9:48
 */
@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    RedisUtils redisUtils;

    @RequestMapping("test")
    public Result<String> test(String test) {
        redisUtils.set("test", "dd",10);
        Object test1 = redisUtils.get("test");
        return Result.success(test1.toString());
    }
}

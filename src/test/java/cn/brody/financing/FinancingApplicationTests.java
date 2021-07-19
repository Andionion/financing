package cn.brody.financing;

import cn.brody.financing.util.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FinancingApplicationTests {

    @Test
    void contextLoads() {
        RedisUtil.setString("foo", "bar");
        String foo = RedisUtil.getString("foo");
        System.out.println(foo);
    }

}

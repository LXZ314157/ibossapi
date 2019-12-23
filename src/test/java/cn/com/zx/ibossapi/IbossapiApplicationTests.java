package cn.com.zx.ibossapi;

import cn.com.zx.ibossapi.domain.Token;
import cn.com.zx.ibossapi.util.AESUtil;
import cn.com.zx.ibossapi.util.DateUtil;
import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IbossapiApplicationTests {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private Gson gson;

    @Test
	public void contextLoads() {
       String s =  redisTemplate.opsForValue().get("key1");
//       String token = "45ACACBD6FC6F30BDB1507F445636E1D1605E30DDE13DB2A40F916BF49906E8903A8BBDC58D01CC6A7CA04E7321B0A057622861E38EA15F9B708520411D6DCB4A6B0629264E879C67981A6B0B92FAA0D32F84C587DE28C275B7A64235F764F6153FE1C64D85F40EEB67654A9048AFED0";
//        redisTemplate.opsForValue().set("token",token);
//        List<String> list = Arrays.asList("jack","rose","mike");
//        redisTemplate.opsForList().leftPushAll("nameList",list);
//        System.out.println( redisTemplate.opsForValue().get("token"));
//
//        UserInfo userInfo = new UserInfo();
//        userInfo.setId(1);
//        userInfo.setName("小吕");
//        userInfo.setTel("18317160072");
//
//        redisTemplate.opsForValue().set("userInfo",gson.toJson(userInfo));
//        String userInfoKey = redisTemplate.opsForValue().get("userInfo");
//        UserInfo userInfo = JSON.parseObject(userInfoKey,UserInfo.class);
//        System.out.println(userInfo.getName());
//        System.out.println(userInfo.toString());


//        redisTemplate.opsForValue().set("token","dsf",30, TimeUnit.MINUTES);


        Token token = new Token();
        String key = "e10adc3949ba59abbe56e003r5yf883e";
        token.setUserCode("123");
        token.setOpenid("abc");
        String timestamp = DateUtil.getTimestamp();
        token.setTimestamp(timestamp);
        System.out.println(AESUtil.encrypt(JSON.toJSONString(token),key));

	}

}

package cn.com.zx.ibossapi.controller.myController;

import cn.com.zx.ibossapi.domain.MongoTest;
import cn.com.zx.ibossapi.domain.UserComent;
import cn.com.zx.ibossapi.service.mongodb.MongoTestDao;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** mongodb测试控制层
 * @author lvxuezhan
 * @date 2019/11/24
 **/

/**
 * MongoDB测试 一
 */
@RestController
public class MongoTestController {

    @Autowired
    private MongoTestDao mtdao;

    @Autowired
    private MongoTemplate mongoTemplate;


    @GetMapping(value="/saveOne")
    public String saveOne() {
        MongoTest mgtest=new MongoTest();
        mgtest.setId(11);
        mgtest.setAge(33);
        mgtest.setName("ceshi");
        Boolean b = mtdao.saveOne(mgtest);
        if(b){
            return "保存成功";
        }else{
            return "保存失败";
        }
    }

    @GetMapping(value="/saveList")
    public String saveList() {
        JSONObject document1 = new JSONObject();
        document1.put("atl_id",2);

        UserComent userComent1 = new UserComent();
        userComent1.setUserCode("user_04");
        userComent1.setNikeName("jana");
        userComent1.setComent("我觉得这篇文章很有价值");
        userComent1.setApprNum(100);
        userComent1.setOppoNum(1);

        UserComent userComent2 = new UserComent();
        userComent2.setUserCode("user_05");
        userComent2.setNikeName("ricle");
        userComent2.setComent("我觉得也是");
        userComent2.setApprNum(2);
        userComent2.setOppoNum(198);

        List<Map<String,String>> replyList = new ArrayList<>();
        Map<String,String> replyMap1 = new HashMap<>();
        replyMap1.put("userCode","user_07");
        replyMap1.put("coment","的确很好啊");
        Map<String,String> replyMap2 = new HashMap<>();
        replyMap2.put("userCode","user_09");
        replyMap2.put("coment","哈哈，我说的最有道理");
        replyList.add(replyMap1);
        replyList.add(replyMap2);
        userComent1.setReplyList(replyList);
        document1.put("userContent1",userComent1);
        document1.put("userContent2",userComent2);
        boolean b = mtdao.saveList(document1);
        if(b){
            return "保存成功";
        }else{
            return "保存失败";
        }
    }

    @GetMapping(value="/findOne")
    public MongoTest findTestByName(){
        MongoTest mgtest= mtdao.findTestByName("ceshi");
        System.out.println("mgtest is "+mgtest);
        return mgtest;
    }

    @GetMapping(value="/findAll")
    public List<MongoTest> findAll(){
        return mtdao.findAll();
    }

    @GetMapping(value="/test3")
    public void updateTest(){
        MongoTest mgtest=new MongoTest();
        mgtest.setId(11);
        mgtest.setAge(44);
        mgtest.setName("ceshi2");
        mtdao.updateTest(mgtest);
    }

    @GetMapping(value="/test4")
    public void deleteTestById(){
        mtdao.deleteTestById(11);
    }

}

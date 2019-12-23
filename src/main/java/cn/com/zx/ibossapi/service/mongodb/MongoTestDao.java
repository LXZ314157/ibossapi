package cn.com.zx.ibossapi.service.mongodb;

import cn.com.zx.ibossapi.domain.MongoTest;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author lvxuezhan
 * @date 2019/11/24
 **/
@Component
public class MongoTestDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 创建单个对象
     */
    public Boolean saveOne(MongoTest test) {
        Boolean b = true;
        try {
            mongoTemplate.save(test);
        } catch (Exception e) {
            e.printStackTrace();
            b = false;
        }
        return b;
    }

    /**
     * 创建多个对象
     */
    public boolean saveList(JSONObject document) {
        Boolean b = true;
        try {
            mongoTemplate.save(document,"atl_table");
        } catch (Exception e) {
            e.printStackTrace();
            b = false;
        }
        return b;
    }

    /**
     * 根据用户名查询对象
     * @return
     */
    public MongoTest findTestByName(String name) {
        Query query=new Query(Criteria.where("name").is(name));
        MongoTest mgt =  mongoTemplate.findOne(query , MongoTest.class);
        return mgt;
    }

    /**
     * 查询集合
     * @return
     */
    public List<MongoTest> findAll(){
        return mongoTemplate.findAll(MongoTest.class);
    }

    /**
     * 更新对象
     */
    public void updateTest(MongoTest test) {
        Query query=new Query(Criteria.where("id").is(test.getId()));
        Update update= new Update().set("age", test.getAge()).set("name", test.getName());
        //更新查询返回结果集的第一条
        mongoTemplate.updateFirst(query,update,MongoTest.class);
        //更新查询返回结果集的所有
        // mongoTemplate.updateMulti(query,update,TestEntity.class);
    }

    /**
     * 删除对象
     * @param id
     */
    public void deleteTestById(Integer id) {
        Query query=new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query,MongoTest.class);
    }


}

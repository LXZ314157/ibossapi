package cn.com.zx.ibossapi.domain;

import lombok.Data;

/**
 * @author lvxuezhan
 * @date 2019/11/24
 **/
@Data
public class MongoTest {
    private Integer id;
    private Integer age;
    private String name;

    public MongoTest() {
    }

    public MongoTest(Integer id, Integer age, String name) {
        this.id = id;
        this.age = age;
        this.name = name;
    }
}

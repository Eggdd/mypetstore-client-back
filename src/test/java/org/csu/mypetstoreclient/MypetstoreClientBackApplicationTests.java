package org.csu.mypetstoreclient;

import org.csu.mypetstoreclient.entity.Category;
import org.csu.mypetstoreclient.persistence.CategoryMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MypetstoreClientBackApplicationTests {

    @Autowired
    private CategoryMapper categoryMapper;

    @Test
    void contextLoads() {
//        List<Category> categoryList = categoryMapper.selectList(null);
//        System.out.println(categoryList);
        int a = 1;


    }

}

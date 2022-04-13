package org.csu.mypetstoreclient;

import org.csu.mypetstoreclient.entity.Category;
import org.csu.mypetstoreclient.persistence.CategoryMapper;
import org.csu.mypetstoreclient.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MypetstoreClientBackApplicationTests {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private AccountService accountService;

    @Test
    void contextLoads() {
//        List<Category> categoryList = categoryMapper.selectList(null);
//        System.out.println(categoryList);
        System.out.println(accountService.getAccountByPhone("555-555-5555", "j2ee").getData());


    }

}

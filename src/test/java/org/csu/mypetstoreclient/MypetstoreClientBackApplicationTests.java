package org.csu.mypetstoreclient;

import org.csu.mypetstoreclient.entity.Category;
import org.csu.mypetstoreclient.persistence.CategoryMapper;
import org.csu.mypetstoreclient.service.AccountService;
import org.csu.mypetstoreclient.vo.AccountVO;
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
//        System.out.println(accountService.getAccountByPhoneAndPassword("555-555-5555", "j2ee").getData());
//        AccountVO accountVO = accountService.getAccountByUsername("111").getData();
//        accountVO.setEmail("1849@qq.com");
//        accountVO.setPassword("123456");
//        System.out.println(accountService.updateAccount(accountVO).getMsg());
//        System.out.println(accountService.changePhone("111", "186").getMsg());
    }

}

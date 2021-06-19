package com.example.demo.blImpl;

import com.example.demo.bl.UserService;
import com.example.demo.vo.UserVO;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceImplTest {

    @Autowired
    UserService userService;

    UserVO userVO;

    @Test
    void register() {
    }

    @Test
    void getUser() {
    }

    @Test
    void login() {
    }

    @Test
    void search() {
        ArrayList<String> test = new ArrayList<String>();
        test.add("茅野爱衣");
        Assert.assertNotNull(userService.search(test,1));
    }

    @Test
    void answer() {
        Assert.assertNotNull(userService.answer("你好"));
    }

    @Test
    void supplement() {
        Assert.assertNotNull(userService.supplement("茅野"));
    }

    @Test
    void recommend() {
        Assert.assertNotNull(userService.recommend(1));
    }

    @Test
    void getHistory() {
        Assert.assertNotNull(userService.getHistory(1));
    }

    @Test
    void deleteHistory() {
    }

    @Test
    void clearHistories() {
    }

    @Test
    void searchByEid() {
    }
}
package com.example.demo.bl;

import com.example.demo.vo.UserVO;
import com.example.demo.vo.Userform;
import com.example.demo.vo.GraphVO;

import java.util.ArrayList;

/**
 * @Author:BlackQAQ
 * @Date:2021/3/29
 * @Description:Interfaces of UserService
 */

public interface UserService {
    Integer register(Userform userform);

    UserVO getUser(Integer uid);

    Integer login(Userform userform);

    GraphVO search(ArrayList<String> keyWords,int uid);

    String answer(String question);

    ArrayList<String> supplement(String keyWord);

    ArrayList<String> recommend(int uid);

    ArrayList<String> getHistory(int uid);

    Integer deleteHistory(int uid, String key);

    Integer clearHistories(int uid);

    GraphVO searchByEid(int eid,int uid);

}

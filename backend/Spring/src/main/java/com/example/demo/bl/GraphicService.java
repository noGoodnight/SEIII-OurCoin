package com.example.demo.bl;


import com.example.demo.po.Graph;
import com.example.demo.vo.GraphVO;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

/**
 * @Author:BlackQAQ
 * @Date:2021/3/14
 * @Description:Interfaces of GraphicService
 */

public interface GraphicService {
    Integer saveGraph(GraphVO graphVO);

    ArrayList<GraphVO> getGraphsByUid(Integer uid);

    GraphVO getGraphByGid(Integer gid);

    Integer generateGraph(GraphVO graphVO);

    String deleteGraphByGid(int gid);
}

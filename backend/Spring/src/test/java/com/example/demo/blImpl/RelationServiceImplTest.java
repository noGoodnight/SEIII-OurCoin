package com.example.demo.blImpl;

import com.example.demo.bl.RelationService;
import com.example.demo.vo.RelationVO;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class RelationServiceImplTest {

    @Autowired
    RelationService relationService = null;

    RelationVO relationVO;

    @Test
    void addRelation() {
        relationVO = new RelationVO();
        relationVO.setRid(36159);
        relationVO.setName("relationAddTest");
        relationVO.setGid(1);
        relationVO.setTheFirstEid(1);
        relationVO.setTheSecondEid(2);
        Assert.assertEquals(java.util.Optional.of(36159),java.util.Optional.of(relationService.addRelation(relationVO)));
    }

    @Test
    void deleteRelation() {
        relationVO = new RelationVO();
        relationVO.setRid(40001);
        relationVO.setName("relationAddTest");
        relationVO.setGid(1);
        relationVO.setTheFirstEid(1);
        relationVO.setTheSecondEid(2);
        Assert.assertEquals(java.util.Optional.of(0),java.util.Optional.of(relationService.deleteRelation(relationVO)));
    }
}
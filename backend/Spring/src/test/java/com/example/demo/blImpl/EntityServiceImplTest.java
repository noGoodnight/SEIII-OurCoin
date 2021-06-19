package com.example.demo.blImpl;

import com.example.demo.bl.EntityService;
import com.example.demo.vo.EntityVO;
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
class EntityServiceImplTest {

    @Autowired
    EntityService entityService;

    EntityVO entityVO;

    @Test
    void addEntity() {
        entityVO = new EntityVO();
        entityVO.setEid(20988);
        entityVO.setGid(1);
        entityVO.setName("addEntityTest");
        entityVO.setX(800);
        entityVO.setY(800);
        entityVO.setCategory("0");
        entityVO.setColor("#3455EA");
        Assert.assertEquals(java.util.Optional.of(20988), java.util.Optional.of(entityService.addEntity(entityVO)));
    }

    @Test
    void deleteEntity() {
        entityVO = new EntityVO();
        entityVO.setEid(30000);
        entityVO.setGid(1);
        entityVO.setName("addEntityTest");
        entityVO.setX(800);
        entityVO.setY(800);
        entityVO.setCategory("0");
        entityVO.setColor("#3455EA");
        Assert.assertEquals(java.util.Optional.of(0),java.util.Optional.of(entityService.deleteEntity(entityVO)));
    }

    @Test
    void searchEntities() {
        String test = "爱丽丝";
        Assert.assertNotNull(entityService.searchEntities(test));
    }

    @Test
    void getEntityByEid() {
        Assert.assertEquals("漫研部", entityService.getEntityByEid(1).getName());
    }

    @Test
    void getDetailByEid() {
    }
}
package com.fei.wx.service;

import com.fei.wx.util.BussinessException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SubjectServiceTest {

    @Autowired
    private SubjectService subjectService;

    @Test
    public void addSortDetail() {
        for (int i = 0; i < 10; i++) {
            try {
                subjectService.addSortDetail(26, i+3);
            } catch (BussinessException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void addGroupDetail() {
    }
}

package com.atguigu.shardingjdbcdemo;

import com.atguigu.shardingjdbcdemo.entity.Course;
import com.atguigu.shardingjdbcdemo.entity.Success;
import com.atguigu.shardingjdbcdemo.entity.Udict;
import com.atguigu.shardingjdbcdemo.entity.User;
import com.atguigu.shardingjdbcdemo.mapper.CourseMapper;
import com.atguigu.shardingjdbcdemo.mapper.SuccessMapper;
import com.atguigu.shardingjdbcdemo.mapper.UdictMapper;
import com.atguigu.shardingjdbcdemo.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShardingjdbcdemoApplicationTests {

    //注入mapper
    @Autowired
    private CourseMapper courseMapper;

    //注入user的mapper
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UdictMapper udictMapper;

    @Autowired
    private SuccessMapper successMapper;

    /**
     * 测试读写分离
     */

    @Test
    public void addWriteAndRead(){
        Success success = new Success();
        success.setId(100);
        success.setContent("再确认一下哈");
        successMapper.insert(success);
    }

    @Test
    public void queryWriteAndRead(){
        QueryWrapper<Success> wrapper = new QueryWrapper<>();
        wrapper.eq("id","100");
        List<Success> list = successMapper.selectList(wrapper);
        for (Success success : list) {
            System.out.println(success);
        }
    }

    //======================测试公共表===================
    //添加操作
    @Test
    public void addDict() {
        Udict udict = new Udict();
        udict.setUstatus("b");
        udict.setUvalue("已启用");
        udictMapper.insert(udict);
    }

    //删除操作
    @Test
    public void deleteDict() {
        QueryWrapper<Udict> wrapper = new QueryWrapper<>();
        //设置userid值
        wrapper.eq("dictid", 565618439285112833L);
        udictMapper.delete(wrapper);
    }

    //======================测试垂直分库==================
    //添加操作
    @Test
    public void addUserDb() {
        User user = new User();
        user.setUsername("李佳乐");
        user.setUstatus("ok");
        userMapper.insert(user);
    }

    //查询操作
    @Test
    public void findUserDb() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        //设置userId值
        wrapper.eq("user_id", 565528815346909185L);
        User user = userMapper.selectOne(wrapper);
        System.out.println(user);
    }


    //======================测试水平分库=====================
    //添加操作
    @Test
    public void addCourseDb() {
        for (int i = 0; i < 5; i++) {
            Course course = new Course();
            course.setCname("vue" + i);
            //分库根据user_id
            course.setUserId(111L);
            course.setCstatus("正常");
            courseMapper.insert(course);
        }
    }

    //查询操作
    @Test
    public void findCourseDb() {
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        //设置userid值
        wrapper.eq("user_id", 111L);
        //设置cid值
//        wrapper.eq("cid",565518062934032385L);
//        Course course = courseMapper.selectOne(wrapper);
        List<Course> list = courseMapper.selectList(wrapper);
        for (Course course : list) {
            System.out.println(course);
        }
//        System.out.println(course);
    }

    //=======================测试水平分表===================
    //添加课程的方法
    @Test
    public void addCourse() {
        for (int i = 1; i <= 10; i++) {
            Course course = new Course();
            course.setCname("java" + i);
            course.setUserId(100L);
            course.setCstatus("Normal" + i);
            courseMapper.insert(course);
        }
    }

    //查询课程的方法
    @Test
    public void findCourse() {
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.eq("cid", 565219245722435584L);
        Course course = courseMapper.selectOne(wrapper);
        System.out.println(course);
    }


}

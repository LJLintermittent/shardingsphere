package com.atguigu.shardingjdbcdemo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 一些声明信息
 * Description: <br/>
 * date: 2021/2/8 18:44<br/>
 *
 * @author ${李佳乐}<br/>
 * @since JDK 1.8
 */
@Data
@TableName(value = "success")
public class Success {
    private int id;
    private String content;
}

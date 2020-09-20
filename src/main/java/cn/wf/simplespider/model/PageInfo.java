package cn.wf.simplespider.model;

import lombok.Data;

import java.util.Date;

/**
 * @Author: fan.wang
 * @Date: 2020/9/18 10:02
 * @description: 页面信息
 */
@Data
public class PageInfo {

    private String source;
    private String content;
    private Date createTime;
    private Date updateTime;

}

package cn.wf.simplespider.utils;

import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

/**
 * @Author: fan.wang
 * @Date: 2020/9/22 9:48
 * @description: 根据xpath获取TagNode对象
 */
public class TagNodeUtil {

    public static String getInfoByXPath(TagNode tagNode, String xpath) {
        String info = null;
        try {
            Object[] objects = tagNode.evaluateXPath(xpath);
            if (objects.length > 0) {
                TagNode node = (TagNode) objects[0];
                info = node.getText().toString();
            }
        } catch (XPatherException e) {
            e.printStackTrace();
        }
        return info;
    }

    public static String getAttributeByName(TagNode tagNode, String xpath, String name) {
        String info = null;
        try {
            Object[] objects = tagNode.evaluateXPath(xpath);
            if (objects.length > 0) {
                TagNode node = (TagNode) objects[0];
                info = node.getAttributeByName(name);
            }
        } catch (XPatherException e) {
            e.printStackTrace();
        }
        return info;
    }
}

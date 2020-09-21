package cn.wf.simplespider.factory;

import cn.wf.simplespider.enums.SourceEnum;
import cn.wf.simplespider.factory.Processor.Processor;
import cn.wf.simplespider.factory.Processor.impl.Bilibili;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * @Author: fan.wang
 * @Date: 2020/9/21 9:36
 * @description: 解析页面工厂
 */
@Component
public class ProcessPageInfoFactory {

    private Map<Integer, Processor> pageInfoMap;

    @PostConstruct
    public void init() {
        pageInfoMap = Maps.newHashMap();
        pageInfoMap.put(SourceEnum.BILIBILI.getType(), new Bilibili());
    }


    public Processor getProcessor(Integer sourceType) {
        return pageInfoMap.get(sourceType);
    }

}

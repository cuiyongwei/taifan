package com.education.service;

import com.education.domain.Resources;
import com.education.domain.ResourcesBefore;
import com.education.mapper.ResourcesMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

@Service
public class ResourcesServiceimpl implements ResourcesService{

    @Resource
    private ResourcesMapper resourcesMapper;

    Resources resources = new Resources();

    //查看所有资源
    @Override
    public List<Resources> getResources() {
        return resourcesMapper.getResources();
    }

    //添加资源
    @Override
    public void addResources(ResourcesBefore resourcesBefore) {
        //把获取的时间给后端的类，然后把后端的类传过去
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        System.out.println("获取的当前时间"+calendar);
        resources.setCreatedon(calendar.getTime());
        BeanUtils.copyProperties(resourcesBefore,resources);
        resourcesMapper.addResources(resources);
    }

    //修改资源
    @Override
    public void upDateResources(ResourcesBefore resourcesBefore) {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        System.out.println("获取的当前时间"+calendar);
        resources.setCreatedon(calendar.getTime());
        resources.setUpdatedon(calendar.getTime());
        BeanUtils.copyProperties(resourcesBefore,resources);
        resourcesMapper.upDateResources(resources);
    }

    //删除资源
    @Override
    public void deleteResources(String id) {
        resourcesMapper.deleteResources(id);
    }

}

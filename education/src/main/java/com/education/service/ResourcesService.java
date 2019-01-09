package com.education.service;

import com.education.domain.Resources;
import com.education.domain.ResourcesBefore;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 资源
 */
@Service
public interface ResourcesService {

    //查看所有资源
    List<Resources> getResources();

    //添加资源
    void addResources(ResourcesBefore resourcesBefore);

    //修改资源
    void upDateResources(ResourcesBefore resourcesBefore);

    //删除资源
    void deleteResources(String id);

}

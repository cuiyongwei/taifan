package com.education.controller;

import com.education.domain.Resources;
import com.education.domain.ResourcesBefore;
import com.education.service.ResourcesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 资源控制层
 */

@Api(value = "资源controlller",tags = {"资源操作接口"})
@RestController
@RequestMapping(value = "/Resources")
public class ResourcesController {

    @Autowired
    private ResourcesService resourcesService;

    //查询所有资源
    @ApiOperation(value = "查询所有资源",notes = "查询所有资源")
    @GetMapping(value = "")
    public List<Resources> getResources(){
        return resourcesService.getResources();
    }

    //添加资源
    @ApiOperation(value = "添加资源",notes = "添加资源")
    @PostMapping(value = "")
    public void addResources(@ApiParam(value = "资源的各个属性") @RequestBody ResourcesBefore resourcesBefore){
        resourcesService.addResources(resourcesBefore);
    }

    //修改资源
    @ApiOperation(value = "修改资源",notes = "修改资源")
    @PutMapping(value = "")
    public void upDateResources(@ApiParam(value = "资源的各个属性") @RequestBody ResourcesBefore resourcesBefore){
        resourcesService.upDateResources(resourcesBefore);

    }

    //删除资源
    @ApiOperation(value = "删除资源",notes = "删除资源")
    @DeleteMapping("/{id}")
    @ApiImplicitParam(name = "id",value = "资源id",required = true,dataType = "String",paramType = "path")
    public void deleteResources(@PathVariable("id") String id){
        resourcesService.deleteResources(id);
    }

}

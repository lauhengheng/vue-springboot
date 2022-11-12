package com.lauheng.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.pagehelper.PageInfo;
import com.lauheng.jwt.JwtUtil;
import com.lauheng.model.Activity;
import com.lauheng.model.ApplyInfo;
import com.lauheng.model.Team;
import com.lauheng.model.TeamUser;
import com.lauheng.service.ITeamService;
import com.lauheng.service.ITeamUserService;
import com.lauheng.util.json.JsonObject;
import com.lauheng.util.json.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.apache.tomcat.jni.Multicast;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 * 社团信息管理 前端控制器
 * </p>
 *
 * @author lauheng
 * @since 2022-10-11
 */
@Api(tags = {"社团信息管理"})
@RestController
@RequestMapping("/team")
@CrossOrigin
public class TeamController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Resource
    private ITeamService teamService;

    @Resource
    private ITeamUserService teamUserService;

    @RequestMapping("/queryTeamList")
    public JsonObject queryTeamList(@RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "10") int limit,
                                    Team team){
        //创建返回对象模型
        JsonObject object=new JsonObject();
        //获取结果集
        PageInfo<Team> pageInfo = teamService.queryTeamList(page, limit, team);
        object.setCode(20000);
        object.setData(pageInfo.getList());
        object.setTotal(pageInfo.getTotal());
        return object;

    }
    //上传图片 其他模块也可直接使用
//    @ResponseBody
    @RequestMapping("/fileUpload")
    public R fileUpload(@RequestParam(value = "file")
                                MultipartFile file){
        //判断上传内容是否为空
        if(file.isEmpty()){
            System.out.println("图片不存在");
        }
        //获取图片名称
        String fileName = file.getOriginalFilename();
        //获取文件的后缀
        String suffixName=fileName.substring(fileName.lastIndexOf("."));
        //随机生成一个文件名称
        fileName=UUID.randomUUID()+suffixName;
        //设置文件上传路径
        String filePath="d://images//";
        //上传
        File dest=new File(filePath,fileName);
        //判断文件路径是否存在，不存在创建
        if(!dest.getParentFile().exists()){
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            System.out.println("上传出错");
        }
        //设置最终的文件名称 返回前端
        String name="/images"+fileName;
        return R.ok(name,null);

    }


    @ApiOperation(value = "新增社团信息管理")
    @RequestMapping("/addInfo")
    public R add(@RequestBody Team team){

        int num=teamService.add(team);
        if(num>0){
            return R.ok();
        }
        return R.fail("添加失败");
    }

    @ApiOperation(value = "删除社团信息管理")
    @RequestMapping("/deleteByID")
    public R delete(Long id){
        int num= teamService.delete(id);
        if (num>0){
            return R.ok();
        }
        return R.fail("删除失败");
    }

    @ApiOperation(value = "更新社团信息管理")
    @RequestMapping("/updateInfo")
    public R update(@RequestBody Team team){
        int num = teamService.updateData(team);
        if (num>0){
            return R.ok();
        }
        return R.fail("修改失败");
    }

    @ApiOperation(value = "查询社团信息管理分页数据")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "page", value = "页码"),
        @ApiImplicitParam(name = "pageCount", value = "每页条数")
    })
    @GetMapping()
    public IPage<Team> findListByPage(@RequestParam Integer page,
                                   @RequestParam Integer pageCount){
        return teamService.findListByPage(page, pageCount);
    }

    @ApiOperation(value = "id查询社团信息管理")
    @GetMapping("{id}")
    public Team findById(@PathVariable Long id){
        return teamService.findById(id);
    }


    /*
    *************
    * 以下是前端页面所需接口
     */

    @RequestMapping("/queryTeamAll")
    public JsonObject queryTeamList(){
        //创建返回对象模型
        JsonObject object=new JsonObject();
        //获取结果集
        PageInfo<Team> pageInfo = teamService.queryTeamList(1, 100, null);
        object.setCode(20000);
        object.setData(pageInfo.getList());
        object.setTotal(pageInfo.getTotal());
        return object;

    }

    @RequestMapping("/findTeamById")
    public Map findTeamById(int id){
        Team team = teamService.findById(new Long(id));
        Map map = new HashMap();
        map.put("code",20000);
        map.put("data",team);
        return map;

    }

    /**
     * 获取我的社团列表
     */
    @RequestMapping("/queryMyTeamList")
    public JsonObject queryMyTeamList(HttpServletRequest request){
        //通过header 获取token
        String token=request.getHeader("token");
        //获取用户id
        Integer userId= JwtUtil.getUserId(token);
        //根据用户的id 获取用户对象
        TeamUser teamUser=teamUserService.getById(userId);
        //通过对象获取电话
        List<Team> list=teamService.queryMyteamList(teamUser.getTel());
        //创建返回结果集对象
        //创建返回结果集对象
        JsonObject jsonObject=new JsonObject();
        jsonObject.setCode(20000);
        jsonObject.setData(list);
        return jsonObject;

    }

}

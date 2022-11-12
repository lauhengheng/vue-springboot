package com.lauheng.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.pagehelper.PageInfo;
import com.lauheng.jwt.JwtUtil;
import com.lauheng.model.ApplyInfo;
import com.lauheng.model.ApplyList;
import com.lauheng.model.Team;
import com.lauheng.model.TeamUser;
import com.lauheng.service.IApplyInfoService;
import com.lauheng.service.ITeamUserService;
import com.lauheng.util.json.JsonObject;
import com.lauheng.util.json.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 社团入团申请记录,申请生成可入团 前端控制器
 * </p>
 *
 * @author lauheng
 * @since 2022-10-11
 */
@Api(tags = {"社团入团申请记录,申请生成可入团"})
@RestController
@RequestMapping("/applyInfo")
@CrossOrigin
public class ApplyInfoController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Resource
    private IApplyInfoService applyInfoService;

    @Resource
    private ITeamUserService teamUserService;



    @ApiOperation(value = "新增社团入团申请记录,申请生成可入团")
    @RequestMapping("/addInfo")
    public R add(@RequestBody ApplyInfo applyInfo){
        /**
         * 根据接收到的teamId和电话
         * 1、判断在申请列表中是否有该账号，如果有不允许申请
         * 2、判断社团成员中是否存在有该电话和teamId的账号信息，如果存在不允许
         */
        String tel=applyInfo.getTel();
        Integer teamId=applyInfo.getTeamId();
        //根据上面的参数获取 申请是否有该对象
        ApplyInfo applyInfoList = applyInfoService.queryApplyInfoByTeamIdAndTel(teamId, tel);
        if(applyInfoList!=null){ //如果有记录
            if(applyInfoList.getStatus()==0){
                return R.fail(20000,"已经申请，但正在审核中，耐心等待...");
            }else{
                return R.fail(20000,"已经申请，不用再申请");
            }

        }
        //接着判断是否在teamuser中存在
        TeamUser teamUser=teamUserService.queryTeamUserByTeamIdAndTel(teamId,tel);
        if(teamUser!=null){
            return R.fail(20000,"已经申请并通过，不用再申请");
        }
        //如果以上都没有继续添加
        applyInfo.setStatus(0);//未通过
        applyInfo.setCreateTime(new Date());//当前时间
        int num =  applyInfoService.add(applyInfo);
        if(num>0){
            return R.ok("恭喜你，申请成功！");
        }
        return R.fail("失败");
    }



    @ApiOperation(value = "更新社团入团申请记录,申请生成可入团")
    @PutMapping()
    public int update(@RequestBody ApplyInfo applyInfo){
        return applyInfoService.updateData(applyInfo);
    }

    @ApiOperation(value = "查询社团入团申请记录,申请生成可入团分页数据")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "page", value = "页码"),
        @ApiImplicitParam(name = "pageCount", value = "每页条数")
    })
    @GetMapping()
    public IPage<ApplyInfo> findListByPage(@RequestParam Integer page,
                                   @RequestParam Integer pageCount){
        return applyInfoService.findListByPage(page, pageCount);
    }

    @ApiOperation(value = "id查询社团入团申请记录,申请生成可入团")
    @GetMapping("{id}")
    public ApplyInfo findById(@PathVariable Long id){
        return applyInfoService.findById(id);
    }

    /**
     * 高级分页查询接口
     */
    @RequestMapping("/queryAppayInfoList")
    public JsonObject queryAppayInfoList(ApplyInfo info,
                                         @RequestParam(defaultValue="1") int page,
                                         @RequestParam(defaultValue = "15") int limit,
                                         HttpServletRequest request){

        //通过header 获取token
        String token=request.getHeader("token");
        //通过token获取角色类型和用户id
        Integer type=JwtUtil.getUserType(token);
        if (type==0){//如果是一个社团的管理员
            //获取当前用户账号的id
            Integer userId=JwtUtil.getUserId(token);
            Team team=new Team();
            team.setUserId(userId);
            info.setTeam(team);

        }
        //创建返回结果集对象
        JsonObject jsonObject=new JsonObject();
        PageInfo<ApplyInfo> applyInfoPageInfo = applyInfoService.queryAppInfoByPage(page,limit,info);

        jsonObject.setCode(20000);
        jsonObject.setTotal(applyInfoPageInfo.getTotal());
        jsonObject.setData(applyInfoPageInfo.getList());
        return jsonObject;
    }

    /**
     * 根据申请记录的id获取审核记录的列表信息
     */
    @RequestMapping("/queryListByAppId")
    public Map queryListByAppId(int appId){
        Map map=new HashMap();
        //通过申请id的记录获取审核记录列表
        List<ApplyList> applyLists = applyInfoService.queryAppListByAppId(appId);
        map.put("code",20000);
        map.put("data",applyLists);
        return map;
    }

    /**
     * 审核记录接口
     */
    @RequestMapping("/addAppInfo")
    public R addAppInfo(@RequestBody ApplyList applyList,
                        HttpServletRequest request){
        /**
         * 1、接收前端传值的数据
         *      appId 申请记录的id
         *      content 审核内容
         *          审核时间，审核人(登录者 token解析出来) 审核状态
         * 2、如果审核状态是1的话 同意
         *      2.1 更改状态码 申请记录的状态码
         *      2.2 添加一条审核记录到审核记录信息表
         *      2.3 加入成员到社团用户信息表
         *       如果不同意
         *       执行1-2步骤
         * 3、把处理的结果给前端请求
         *
         */
        //获取token 解析登录者id
        //通过header 获取token
        String token=request.getHeader("token");
        //通过token获取角色类型和用户id
        Integer userId=JwtUtil.getUserId(token);
        applyList.setAppAuthor(userId);//把审核者id获取
        applyList.setAppTime(new Date());//当前时间
        //根据审核的状态调用相关方法
        if(applyList.getStatus()==1){
            //更改申请记录的状态
            ApplyInfo info=new ApplyInfo();
            info.setStatus(1);
            info.setId(applyList.getAppId());
            applyInfoService.updateStatus(info);

            //添加审批记录信息
            applyInfoService.insertAppList(applyList);
            //把成员添加到 社团成员里面
            //根据申请appId 获取当前申请记录内容
            ApplyInfo applyInfo=applyInfoService.getById(applyList.getAppId());
            TeamUser tu=new TeamUser();
            tu.setJoinTime(new Date());
            tu.setSex(applyInfo.getSex());
            tu.setUsername(applyInfo.getRealname());
            tu.setTel(applyInfo.getTel());
            tu.setEmail(applyInfo.getEmail());
            teamUserService.add(tu);
        }else{//不通过
            ApplyInfo info=new ApplyInfo();
            info.setStatus(2);
            info.setId(applyList.getAppId());
            applyInfoService.updateStatus(info);

            //添加审批记录信息
            applyInfoService.insertAppList(applyList);

        }

    return R.ok();
    }
    @ApiOperation(value = "删除社团入团申请记录,申请生成可入团")
    @RequestMapping("/deleteById")
    public R delete(Long id){
        int num =applyInfoService.delete(id);
        if(num>0){
            return R.ok();
        }
        return R.fail("删除失败");
    }

}

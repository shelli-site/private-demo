package com.example.demo.web;

import com.example.demo.pojo.User;
import com.example.demo.service.UserService;
import com.example.demo.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @Created By ShenXi
 * @Created On 2019/3/27 16:48
 * @Description :
 * @ClassName : UserController
 */
@Slf4j
@Api(tags = "用户API")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping()
    @ApiOperation(value = "用户列表查询")
    public Result getList() {
        return Result.custom("200", "查询成功", userService.getList());
    }

    @ApiOperation(value = "用户查询(ID)")
    @ApiImplicitParam(name = "id", value = "查询ID", required = true)
    @GetMapping("/{id}")
    public Result get(@PathVariable("id") String id) {
        return Result.custom("200", "查询成功", userService.getUserById(id));
    }

    @ApiOperation(value = "用户新增")
    @PostMapping()
    public Result get(@RequestBody User user) {
        return Result.custom("201", "新增成功", userService.add(user));
    }

    @ApiOperation(value = "用户编辑")
    @PutMapping("/{id}")
    public Result edit(@PathVariable("id") String id, @RequestBody User user) {
        if (userService.edit(user))
            return Result.custom("200", "编辑成功", "");
        return Result.custom("422", "请检查数据", "");
    }

    @ApiOperation(value = "用户删除(IDS)")
    @ApiImplicitParam(name = "ids", value = "删除ID", required = true)
    @DeleteMapping("/{ids}")
    public Result delByIds(@PathVariable("ids") String ids) {
        List<String> idList = new ArrayList<>(Arrays.asList(ids.split(",")));
        if (userService.delByIds(idList))
            return Result.custom("204", "删除成功", "");
        return Result.custom("422", "删除失败", "");
    }
}

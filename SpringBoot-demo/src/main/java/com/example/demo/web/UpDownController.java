package com.example.demo.web;

import com.example.demo.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @Created By ShenXi
 * @Created On 2019/3/28 10:49
 * @Description :
 * @ClassName : UpDownController
 */
@Slf4j
@Controller
@Api(tags = "文件上传")
@RequestMapping("/up")
public class UpDownController {
    @ApiOperation(value = "页面")
    @RequestMapping(value = "/uploadPage", method = RequestMethod.GET)
    public String uploadPage() {
        return "upDown";
    }

    @ApiOperation(value = "文件上传方式3，上传在tomcat插件工作目录下")
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public Result upload(HttpServletRequest req, @RequestParam("file") MultipartFile file, Model m) {
        try {
            String fileName = System.currentTimeMillis() + file.getOriginalFilename();
            String destFileName = req.getServletContext().getRealPath("") + "uploaded" + File.separator + fileName;

            File destFile = new File(destFileName);
            destFile.getParentFile().mkdirs();
            file.transferTo(destFile);

            m.addAttribute("fileName", fileName);
            log.info(destFileName);
            log.info(fileName);
            return Result.success("/uploaded/" + fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return Result.failMsg(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            return Result.failMsg(e.getMessage());
        }


    }
}

package com.example.demo.web;

import com.example.demo.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @Created By ShenXi
 * @Created On 2019/3/25 20:58
 * @Description :
 * @ClassName : MainController
 */
@Slf4j
@Controller
@Api(tags = "文件测试API")
public class MainController {


    @GetMapping("")
    @ResponseBody
    @ApiOperation(value = "抛出异常")
    public String index() throws Exception {
        if (1 == 1)
            throw new Exception("这是一个异常");
        return "Hello";
    }

    @GetMapping("/picUpload")
    @ApiOperation(value = "获取文件上传页面1")
    public String picUpload() {
        int a = 1;
        return "picUpload";
    }

    /**
     * @Param: [fileUpload]
     * @return: [fileUpload]
     * @Date: On 2019/3/25 21:27
     * @Author: ShenXi
     * @Description
     **/
    @PostMapping("/upload")
    @ResponseBody
    @ApiOperation(value = "文件上传方式1，上传在static/img下")
    public Object upload(MultipartFile fileUpload) {
        //获取文件名
        String fileName = fileUpload.getOriginalFilename();
        log.info("获取文件 " + fileName);
        //获取文件后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //重新生成文件名
        fileName = UUID.randomUUID() + suffixName;
        //指定本地文件夹存储图片
        String filePath = "D:/Works/Web/IDEA/spring-boot-demo/src/main/resources/static/img/";
        log.info("文件存到 " + filePath + fileName);
        try {
            //将图片保存到static文件夹里
            fileUpload.transferTo(new File(filePath + fileName));
            log.info("文件上传成功，但如何实时显示呢？");
            return "<a href = \"img/" + fileName + "\">保存成功，点击查看。</a>";
        } catch (Exception e) {
            e.printStackTrace();
            log.info("文件上传失败");
            return new Result("500", "fail to upload");
        }
    }

    @GetMapping(value = "/file")
    @ApiOperation(value = "获取文件上传页面2")
    public String file() {
        return "file";
    }

    /**
     * @Param: [file, model, request]
     * @return: [file, model, request]
     * @Date: On 2019/3/25 22:09
     * @Author: ShenXi
     * @Description
     **/
    @PostMapping(value = "/fileUpload")
    @ResponseBody/*, Model model, HttpServletRequest request*/
    @ApiOperation(value = "文件上传方式2，上传在自定义目录下")
    public String fileUpload(@RequestParam(value = "file") MultipartFile file) throws Exception {
        String md5 = DigestUtils.md5Hex((file.getInputStream()));
        log.info(md5);
        if (file.isEmpty()) {
            System.out.println("文件为空空");
        }
        String fileName = file.getOriginalFilename();  // 文件名
        log.info("获取文件 " + fileName);
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        String filePath = "D:/temp-rainy/"; // 上传后的路径
        fileName = UUID.randomUUID() + suffixName; // 新文件名
        log.info("文件存到 " + filePath + fileName);
        log.info("该文件夹路径已在Config中配置过。");
        File dest = new File(filePath + fileName);
        if (!dest.getParentFile().exists()) {
            log.info(filePath + "不存在，创建文件夹");
            dest.getParentFile().mkdirs();
        }
        try {
            log.info(dest.getAbsolutePath());
            //file.transferTo(new File(dest.getAbsolutePath()));
            file.transferTo(dest);
        } catch (IOException e) {
            log.info("文件上传失败");
            e.printStackTrace();
        }
        log.info("文件上传成功!");
        // String filename = "/temp-rainy/" + fileName;
        return "http://localhost:8084/temp-rainy/" + fileName;
    }


}

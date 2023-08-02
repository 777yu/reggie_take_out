package com.cui.controller;


import com.cui.common.R;
import com.sun.org.apache.xpath.internal.operations.Gte;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {

    @Value("${reggie.path}")
    private String bashPath;

    @PostMapping("/upload")
    public R<String> upload(MultipartFile file){
        log.info("上传文件");

        // 获取图片的原始名称
        String originalFilename = file.getOriginalFilename();
        String substring = originalFilename.substring(originalFilename.lastIndexOf("."));
        // 使用UUID 重新生成文件名
        String fileName = UUID.randomUUID().toString()+substring;

        // 判断文件夹是否存在 如果不存在则创建一个文件夹
        File dir = new File(bashPath);
        if(!dir.exists()){
            //不存在创建文件夹
            dir.mkdirs();

        }
        try {
            file.transferTo(new File(bashPath+fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return R.success(fileName);
    }


    @GetMapping("/download")
    public void download(String name , HttpServletResponse httpServletResponse){
        // 创建文件输入流
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(bashPath + name));

            ServletOutputStream outputStream = httpServletResponse.getOutputStream();

            httpServletResponse.setContentType("image/jpeg");

            int  len = 0;
            byte[] bytes = new byte[1024];

            while ((len = fileInputStream.read(bytes)) != -1){

                outputStream.write(bytes,0,len);
                outputStream.flush();
            }

            outputStream.close();
            fileInputStream.close();


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

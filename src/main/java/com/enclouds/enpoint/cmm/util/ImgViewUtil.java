package com.enclouds.enpoint.cmm.util;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLConnection;

@RestController
@RequestMapping("/image")
public class ImgViewUtil {

    private static final String filePath = "C:/Users/user/Pictures/";

    @RequestMapping("/view")
    public void imageView(HttpServletRequest request, HttpServletResponse response, @RequestParam String seq) throws Exception {

        BufferedImage bufferedImage = ImageIO.read(new File(filePath + "test.PNG"));

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", byteArrayOutputStream);

        byteArrayOutputStream.flush();

        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        response.getOutputStream().write(imageBytes);
        byteArrayOutputStream.close();
    }

}

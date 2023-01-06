package com.shunqi.util;

import cn.hutool.core.io.file.FileReader;
import com.qiniu.common.Zone;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Random;

public class t {
    public static void main(String[] args) throws FileNotFoundException {
//        QiniuUtils.upload2Qiniu("D:\\heima.jpg","heima2.jpg");
//        QiniuUtils.deleteFileFromQiniu("heima2.jpg");
//        String filename = "ee7dcf84-8a3a-4ab9-b981-9c5d272fd58d3.jpg";
//        String url = "D:\\MyCode\\2022\\11\\health_parent\\health_backend\\src\\main\\webapp\\images\\" + filename;
//        FileReader fileReader = new FileReader(url);
//        byte[] bytes = fileReader.readBytes();
//        QiniuUtils.upload2Qiniu(bytes,filename);

        Random random = new Random();
        int int1 = random.nextInt();
        System.out.println(int1);
        String s = Integer.toHexString(int1);
        System.out.println(s);
        System.out.println(s.substring(0,4));
    }
}

package com.shunqi.controller;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class tt {
    public static void main(String[] args) throws IOException, TemplateException {
        //1.创建配置类
        Configuration configuration=new Configuration(Configuration.getVersion());
        //2.设置模板所在的目录
        configuration.setDirectoryForTemplateLoading(new File("D:\\ftl"));
        //3.设置字符集
        configuration.setDefaultEncoding("utf-8");
        //4.加载模板
        Template template = configuration.getTemplate("test.ftl");
        //5.创建数据模型
        Map<String,String> map=new HashMap<>();
        map.put("name", "张三");
        map.put("message", "欢迎来到传智播客！");
        map.put("name2", "lisi");
        map.put("age", "23");
        //6.创建Writer对象
        Writer out =new FileWriter(new File("d:\\ftl\\test.html"));
        //7.输出
        template.process(map, out);
        //8.关闭Writer对象
        out.close();
    }
}

package com.javaboy.tienchin;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

@SpringBootTest
public class GenerateCode {

    @Test  // 渠道管理
    void GenerateChannelCode() {

        String path = "D:\\Project\\tienchin\\TienChin\\tienchin-channel\\src\\main\\";

        FastAutoGenerator.create("jdbc:mysql://localhost:3306/tienchin-video", "root", "200112")
                .globalConfig(builder -> {
                    builder.author("shmily") // 设置作者
                            .fileOverride() // 覆盖已生成文件
                            .outputDir(path + "\\java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.javaboy.tienchin") // 设置父包名
                            .moduleName("channel") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, path + "\\resources\\mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("tienchin_channel") // 设置需要生成的表名
                            .addTablePrefix("tienchin_"); // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }

    @Test  // 促销活动
    void GenerateActivityCode() {

        String path = "D:\\Project\\tienchin\\TienChin\\tienchin-activity\\src\\main\\";

        FastAutoGenerator.create("jdbc:mysql://localhost:3306/tienchin-video", "root", "200112")
                .globalConfig(builder -> {
                    builder.author("shmily") // 设置作者
                            .fileOverride() // 覆盖已生成文件
                            .outputDir(path + "\\java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.javaboy.tienchin") // 设置父包名
                            .moduleName("activity") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, path + "\\resources\\mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("tienchin_activity") // 设置需要生成的表名
                            .addTablePrefix("tienchin_"); // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }

    @Test  // 私教课程
    void GenerateCourseCode() {

        String path = "D:\\Project\\tienchin\\TienChin\\tienchin-course\\src\\main\\";

        FastAutoGenerator.create("jdbc:mysql://localhost:3306/tienchin-video", "root", "200112")
                .globalConfig(builder -> {
                    builder.author("shmily") // 设置作者
                            .fileOverride() // 覆盖已生成文件
                            .outputDir(path + "\\java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.javaboy.tienchin") // 设置父包名
                            .moduleName("course") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, path + "\\resources\\mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("tienchin_course") // 设置需要生成的表名
                            .addTablePrefix("tienchin_"); // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }

    @Test  // 线索管理
    void GenerateClueCode() {

        String path = "D:\\Project\\tienchin\\TienChin\\tienchin-clue\\src\\main\\";

        FastAutoGenerator.create("jdbc:mysql://localhost:3306/tienchin-video", "root", "200112")
                .globalConfig(builder -> {
                    builder.author("shmily") // 设置作者
                            .fileOverride() // 覆盖已生成文件
                            .outputDir(path + "\\java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.javaboy.tienchin") // 设置父包名
                            .moduleName("clue") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, path + "\\resources\\mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("tienchin_clue", "tienchin_assignment", "tienchin_follow_record") // 设置需要生成的表名
                            .addTablePrefix("tienchin_"); // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}

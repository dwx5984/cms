package com.lcx.cms;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代码生成器
 */
@FieldDefaults(makeFinal = true)
public class CodeGeneration {

    private String pathSplit = "/";

    private String templatePrefix = "/cms";

    private String outputDir = "./src/main/java";

    /**
     * 是否生成 CRUD 测试界面及对应的 controller 方法
     */
    private boolean crud = true;

    private String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/cms?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai";

    private String username = "root";

    private String password = "root";

    private String driverName = "com.mysql.cj.jdbc.Driver";

    private String author = "lcx";

    /**
     * 上级包名
     */
    private String parent = "com.lcx.cms.entity";

    /**
     * 末层子包名
     */
    private String moduleName = "cs";

    private String[] tablePrefix = {"cms_","sys_"};

    private String[] includeTable = {"cms_chat"};
    /**
     ,
     */

    /**
     * MySQL 生成演示
     */
    @Test
    public void generate() {
        AutoGenerator autoGenerator = new AutoGenerator();

        // 数据源
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL);
        dataSourceConfig.setTypeConvert(new MySqlTypeConvert());
        dataSourceConfig.setDriverName(driverName);
        dataSourceConfig.setUsername(username);
        dataSourceConfig.setPassword(password);
        dataSourceConfig.setUrl(jdbcUrl);
        autoGenerator.setDataSource(dataSourceConfig);

        // 全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setOutputDir(outputDir);
        globalConfig.setFileOverride(true);
        globalConfig.setActiveRecord(true);
        globalConfig.setEnableCache(false);
        globalConfig.setBaseResultMap(true);
        globalConfig.setBaseColumnList(true);
        globalConfig.setOpen(false);
        globalConfig.setAuthor(author);
        globalConfig.setDateType(DateType.ONLY_DATE);

        globalConfig.setServiceName("%sService");
        autoGenerator.setGlobalConfig(globalConfig);

        // 策略
        StrategyConfig strategy = new StrategyConfig();
        strategy.setTablePrefix(tablePrefix);
        strategy.setInclude(includeTable);
        // 对象（驼峰）-数据库（下划线）转换
        strategy.setNaming(NamingStrategy.underline_to_camel);
        autoGenerator.setStrategy(strategy);

        // 包配置
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent(parent);
        packageConfig.setModuleName(moduleName);
        autoGenerator.setPackageInfo(packageConfig);

        InjectionConfig injectionConfig = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("crud", crud);
                map.put("package", parent + "." + moduleName);
                this.setMap(map);
            }
        };

        List<FileOutConfig> fileOutConfigs = new ArrayList<FileOutConfig>();
        // 调整 xml 生成目录到 resource
        fileOutConfigs.add(new FileOutConfig(templatePrefix + "/templates/mapper.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String packagePath = (parent + "." + moduleName).replaceAll("\\.", pathSplit).concat(pathSplit);
                return outputDir + "/../resources/" + packagePath + ConstVal.MAPPER.toLowerCase() + pathSplit + tableInfo.getEntityName() + ConstVal.MAPPER + ConstVal.XML_SUFFIX;
            }
        });
        if (crud) {
//            fileOutConfigs.add(new FileOutConfig(templatePrefix + "/templates/test.html.vm") {
//                @Override
//                public String outputFile(TableInfo tableInfo) {
//                    return outputDir + "/../resources/static/view/" + moduleName + pathSplit + tableInfo.getEntityName() + "Test.html";
//                }
//            });
//            fileOutConfigs.add(new FileOutConfig(templatePrefix + "/templates/list.html.vm") {
//                @Override
//                public String outputFile(TableInfo tableInfo) {
//                    return outputDir + "/../resources/static/view/" + moduleName + pathSplit + tableInfo.getEntityName() + "List.html";
//                }
//            });
        }
        injectionConfig.setFileOutConfigList(fileOutConfigs);
        autoGenerator.setCfg(injectionConfig);

        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(null);
        templateConfig.setEntity(templatePrefix + ConstVal.TEMPLATE_ENTITY_JAVA);
        templateConfig.setMapper(templatePrefix + ConstVal.TEMPLATE_MAPPER);
        templateConfig.setService(templatePrefix + ConstVal.TEMPLATE_SERVICE);
        templateConfig.setServiceImpl(templatePrefix + ConstVal.TEMPLATE_SERVICE_IMPL);
        templateConfig.setController(templatePrefix + ConstVal.TEMPLATE_CONTROLLER);

        autoGenerator.setTemplate(templateConfig);

        autoGenerator.execute();

    }

}
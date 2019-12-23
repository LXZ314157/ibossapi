package cn.com.zx.ibossapi.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 读取项目相关配置
 *
 * @author lvxuezhan
 */
@Component
@Data
@ConfigurationProperties(prefix = "iboss")
public class IbossConfig
{
    /** 项目名称 */
    private String pagesize;

    /** 服务器根路径 */
    private String serverRootPath;

}

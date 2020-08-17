package com.activiti.config;
 
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.ProcessEngineConfigurationConfigurer;
import org.springframework.stereotype.Component;
 
/**
 * @ClassName: ActivitiConfig
 * @Description: (工作流配置类)
 * @author 卡塔库栗
 * 2019年7月2日 上午11:37:30
 */
@Component
public class ActivitiConfig implements ProcessEngineConfigurationConfigurer {
    @Override
    public void configure(SpringProcessEngineConfiguration processEngineConfiguration) {
    	  processEngineConfiguration.setActivityFontName("宋体");
          processEngineConfiguration.setLabelFontName("宋体");
          processEngineConfiguration.setAnnotationFontName("宋体");
          
          processEngineConfiguration.setDbIdentityUsed(true);
          processEngineConfiguration.setDatabaseSchemaUpdate("true");
        
    }
}
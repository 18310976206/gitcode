package com.activiti.listener;

import java.io.Serializable;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;


/**
 * 
* <b>Description:</b><br> (执行监听器)EndEventListener
* @author <a href="" target="_blank">gzl</a>
* @version 1.0
* @Note
* <b>ProjectName:</b> we-bpm
* <br><b>PackageName:</b> com.activiti.listener
* <br><b>ClassName:</b> MyExecutionListener
* <br><b>Date:</b> 2020年8月12日 下午4:28:57
 */
@Component
public class TestExecutionListener implements Serializable,ExecutionListener{
	private static final long serialVersionUID = 1L;

	// 可注入bean
	@Override
	public void notify(DelegateExecution execution) throws Exception {
		// 业务逻辑
    	
	}

}

package com.activiti.listener;

import java.io.Serializable;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;

/**
 * @ClassName: StartEventListener
 * @Description: (任务监听器)
 * @author 卡塔库栗
 * 2019年7月1日 下午3:30:49
 */
@Component
public class TestTaskListener implements Serializable,TaskListener{
	private static final long serialVersionUID = 1L;
	//可以注入bean
	
	@Override
	public void notify(DelegateTask delegateTask) {
		// 业务逻辑
	}
}

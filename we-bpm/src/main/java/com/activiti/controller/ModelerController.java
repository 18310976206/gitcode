package com.activiti.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.platform.util.Result;


/**
 * 
* <b>Description:</b><br> 流程模型管理
* @author <a href="" target="_blank">gzl</a>
* @version 1.0
* @Note
* <b>ProjectName:</b> we-bpm
* <br><b>PackageName:</b> com.activiti.controller
* <br><b>ClassName:</b> ModelerController
* <br><b>Date:</b> 2020年8月12日 下午11:05:54
 */
@RestController
@RequestMapping("model")
public class ModelerController{
 
    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    ProcessEngineConfiguration processEngineConfiguration;
    
  
    /**
     * 
    * <b>Description:</b><br> 创建模型
    * @param name
    * @param description
    * @param key
    * @return
    * @throws UnsupportedEncodingException
    * @Note
    * <b>Author:</b> <a href="" target="_blank">gzl</a>
    * <br><b>Date:</b> 2020年8月12日 下午11:07:09
    * <br><b>Version:</b> 1.0
     */
    @SuppressWarnings("deprecation") 
	@RequestMapping("create")
    public Result newModel(String name,String description,String key) throws UnsupportedEncodingException {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //初始化一个空模型
        Model model = repositoryService.newModel();
        //设置一些默认信息
        int revision = 1;  // 版本号
        
        ObjectNode modelNode = objectMapper.createObjectNode();
        modelNode.put(ModelDataJsonConstants.MODEL_NAME, name);
        modelNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
        modelNode.put(ModelDataJsonConstants.MODEL_REVISION, revision);
        modelNode.put("key", key);
 
        model.setName(name); // 流程名称
        model.setKey(key); // 流程key
        model.setMetaInfo(modelNode.toString());
 
        repositoryService.saveModel(model);
        String id = model.getId();
 
        //完善ModelEditorSource
        ObjectNode editorNode = objectMapper.createObjectNode();
        editorNode.put("id", "canvas");
        editorNode.put("resourceId", "canvas");
        ObjectNode stencilSetNode = objectMapper.createObjectNode();
        stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
        editorNode.put("stencilset", stencilSetNode);
        repositoryService.addModelEditorSource(id,editorNode.toString().getBytes("utf-8"));
        return Result.success().put("modelId", id);
    }
   
    @RequestMapping("open")
    public void open(HttpServletRequest request, HttpServletResponse response){
    	String modelId = request.getParameter("modelId");
    	 try {
			response.sendRedirect(request.getContextPath() + "/html/modeler.html?modelId=" +modelId);
		} catch (IOException e) {
			// TODO 添加异常处理的方式，方法
			e.printStackTrace();
		}
       
    }
 
    /**
     * 
    * <b>Description:</b><br> 删除模型
    * @param id
    * @return
    * @Note
    * <b>Author:</b> <a href="" target="_blank">gzl</a>
    * <br><b>Date:</b> 2020年8月12日 下午11:07:47
    * <br><b>Version:</b> 1.0
     */
    @RequestMapping("delete")
    public Result deleteModel(String id){
        RepositoryService repositoryService = processEngine.getRepositoryService();
        repositoryService.deleteModel(id);
        return Result.success();
    }
 
    /**
     * 
    * <b>Description:</b><br> 批量删除
    * @param id
    * @return
    * @Note
    * <b>Author:</b> <a href="" target="_blank">gzl</a>
    * <br><b>Date:</b> 2020年8月12日 下午11:08:03
    * <br><b>Version:</b> 1.0
     */
    @RequestMapping("deleteForBatch")
    public Result deleteModelForBatch(String id){
    	RepositoryService repositoryService = processEngine.getRepositoryService();
    	String[] ids = id.split(",");
    	for (int i = 0; i < ids.length; i++) {
    		repositoryService.deleteModel(ids[i]);
		}
    	return Result.success();
    }
 
    
    /**
     * 
    * <b>Description:</b><br> 部署模型为流程定义
    * @param id
    * @return
    * @throws Exception
    * @Note
    * <b>Author:</b> <a href="" target="_blank">gzl</a>
    * <br><b>Date:</b> 2020年8月12日 下午11:08:42
    * <br><b>Version:</b> 1.0
     */
    @RequestMapping("deploy")
    public Result deploy(String id) throws Exception {
        //获取模型
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Model modelData = repositoryService.getModel(id);
        byte[] bytes = repositoryService.getModelEditorSource(modelData.getId());
 
        if (bytes == null) {
            return Result.fail("模型数据为空，请先设计流程并成功保存，再进行发布。");
        }
        JsonNode modelNode = new ObjectMapper().readTree(bytes);
 
        BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
        if(model.getProcesses().size()==0){
            return Result.fail("数据模型不符要求，请至少设计一条主线流程。");
        }
        byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(model);
 
        //发布流程
        String processName = modelData.getName() + ".bpmn20.xml";
        Deployment deployment = repositoryService.createDeployment()
                .name(modelData.getName())
                .addString(processName, new String(bpmnBytes, "UTF-8"))
                .deploy();
        modelData.setDeploymentId(deployment.getId());
        repositoryService.saveModel(modelData);
        return Result.success();
    }
    
  
    /**
     * 
    * <b>Description:</b><br> 获取流程资源 xml/png
    * @param processDefinitionId
    * @param resourceType
    * @param response
    * @Note
    * <b>Author:</b> <a href="" target="_blank">gzl</a>
    * <br><b>Date:</b> 2020年8月12日 下午11:09:01
    * <br><b>Version:</b> 1.0
     */
    @RequestMapping("resource") 
    public void loadByDeployment(String processDefinitionId, String resourceType, HttpServletResponse response){ 
    	RepositoryService repositoryService = processEngine.getRepositoryService();
    	ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
    			.processDefinitionId(processDefinitionId)
    			.singleResult(); 
    	String resourceName = ""; 
    	if (resourceType.equals("image")) { 
    		resourceName = processDefinition.getDiagramResourceName(); 
    	} else if (resourceType.equals("xml")) { 
    		resourceName = processDefinition.getResourceName(); 
    	} 
    	InputStream resourceAsStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), resourceName); 
    	byte[] b = new byte[1024]; 
    	int len = -1; 
    	try { 
    		while ((len = resourceAsStream.read(b, 0, 1024)) != -1) { 
    			response.getOutputStream().write(b, 0, len); 
    		} 
    	} catch (IOException e) { 
    		e.printStackTrace();
    	}
    }
    
   
    
  
    /**
     * 
    * <b>Description:</b><br> 删除部署流程
    * @param processDefinitionId
    * @param deploymentId
    * @param cascade
    * @return
    * @Note
    * <b>Author:</b> <a href="" target="_blank">gzl</a>
    * <br><b>Date:</b> 2020年8月12日 下午11:09:54
    * <br><b>Version:</b> 1.0
     */
    @RequestMapping("deleteProcessDeploymentId")
    @Transactional
    public Result deleteProcessDeploymentId(String processDefinitionId,String deploymentId,boolean cascade) {
    	RepositoryService repositoryService = processEngine.getRepositoryService();
    	RuntimeService runtimeService = processEngine.getRuntimeService();
    	// 判断流程是否结束
    	List<ProcessInstance> list = runtimeService.createProcessInstanceQuery().deploymentId(deploymentId).list();
    	if(!cascade) { // 单个删除
    		if(null == list || list.size() == 0) { // 流程 暂无启动 可以删除
    			repositoryService.deleteDeployment(deploymentId);
    			return Result.success("删除成功");
    		}else {
    			return Result.success("流程已启动，请勿删除");
    		}
    	}
    	repositoryService.deleteDeployment(deploymentId,true); // 级联删除
    	return Result.success("删除成功");
    }
    
}
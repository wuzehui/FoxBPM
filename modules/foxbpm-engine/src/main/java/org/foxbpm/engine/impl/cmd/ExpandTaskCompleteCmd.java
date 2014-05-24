package org.foxbpm.engine.impl.cmd;

import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.impl.command.AbstractCustomExpandTaskCommand;
import org.foxbpm.engine.impl.command.ExpandTaskCommand;
import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.impl.util.ReflectUtil;
import org.foxbpm.model.config.foxbpmconfig.TaskCommandDefinition;

public class ExpandTaskCompleteCmd<T> implements Command<T>{

	protected ExpandTaskCommand expandTaskCommand;
	
	public ExpandTaskCompleteCmd (ExpandTaskCommand expandTaskCommand){
		this.expandTaskCommand=expandTaskCommand;
	}
	
	

	@SuppressWarnings("unchecked")
	public T execute(CommandContext commandContext) {
		
		if(Authentication.getAuthenticatedUserId()==null||Authentication.getAuthenticatedUserId().equals("")){
			throw new FoxBPMException("登录用户不能为空!");
		}
		// TODO Auto-generated method stub
		Object[] obj = new Object[] {expandTaskCommand};  
		
		
		TaskCommandDefinition taskCommandDef= commandContext.getProcessEngineConfigurationImpl().getTaskCommandDefinition(this.expandTaskCommand.getCommandType());
		if(taskCommandDef!=null){
			String classNameString=taskCommandDef.getCmd();
			if(classNameString==null||classNameString.equals("")){
				throw new FoxBPMException("配置文件中ID为 "+this.expandTaskCommand.getCommandType() + " 的扩展配置cmd属性不能为空!");
			}
			
			String commandClassNameString=taskCommandDef.getCommand();
			if(commandClassNameString==null||commandClassNameString.equals("")){
				throw new FoxBPMException("配置文件中ID为 "+this.expandTaskCommand.getCommandType() + " 的扩展配置command属性不能为空!");
			}
			
			//commandClassNameString
			AbstractCustomExpandTaskCommand abstractCustomExpandTaskCommand=(AbstractCustomExpandTaskCommand)ReflectUtil.instantiate(commandClassNameString, obj);
			
			Object[] objTemp = new Object[] {abstractCustomExpandTaskCommand};  
			@SuppressWarnings("rawtypes")
			AbstractExpandTaskCmd abstractExpandTaskCmd=(AbstractExpandTaskCmd)ReflectUtil.instantiate(classNameString, objTemp);
			return (T) abstractExpandTaskCmd.execute(commandContext);
			
		}
		else{
			throw new FoxBPMException("配置文件中不存在ID为 "+this.expandTaskCommand.getCommandType() + " 的扩展配置.");
		}
	

	}


}

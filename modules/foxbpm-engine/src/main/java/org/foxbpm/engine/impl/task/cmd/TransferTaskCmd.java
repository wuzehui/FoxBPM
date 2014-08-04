/**
 * Copyright 1996-2014 FoxBPM ORG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * @author kenshin
 */
package org.foxbpm.engine.impl.task.cmd;
import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.entity.TaskEntity;
import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.impl.task.command.TransferTaskCommand;
import org.foxbpm.engine.impl.util.ClockUtil;
import org.foxbpm.engine.impl.util.GuidUtil;
import org.foxbpm.engine.task.TaskCommand;
import org.foxbpm.kernel.runtime.FlowNodeExecutionContext;

/**
 * @author kenshin
 *
 */
public class TransferTaskCmd extends AbstractExpandTaskCmd<TransferTaskCommand, Void> {

	private static final long serialVersionUID = 1L;

	/**
	 * 转发的用户编号
	 */
	protected String transferUserId;

	public TransferTaskCmd(TransferTaskCommand transferTaskCommand) {

		super(transferTaskCommand);
		this.transferUserId = transferTaskCommand.getTransferUserId();

	}

	@Override
	protected Void execute(CommandContext commandContext, TaskEntity task) {
		

		
		/** 放置流程实例级别的瞬态变量 */
		task.setProcessInstanceTransientVariables(this.transientVariables);
		/** 获取任务命令 */
		TaskCommand taskCommand = getTaskCommand(task);
		/** 获取流程内容执行器 */
		FlowNodeExecutionContext executionContext = getExecutionContext(task);
		/** 任务命令的执行表达式变量 */
		taskCommand.getExpressionValue(executionContext);
		/** 设置任务处理者 */
		task.setAssignee(Authentication.getAuthenticatedUserId());
		/** 设置任务的处理命令 commandId commandName commandType */
		task.setTaskCommand(taskCommand);		
		/** 处理意见 */
		task.setTaskComment(taskComment);
		/** 结束任务,但是并不驱动流程向下。 */
		task.end(taskCommand, taskComment);
		/** 创建新任务 */	
		cloneAndInsertTask(task);
		
		return null;
	}
	
	
	private void cloneAndInsertTask(TaskEntity task){
		
		/** 这里可以采用浅克隆的方式克隆出一个单简单字段的任务,也可以自己重新创建任务,但是需要赋值令牌、流程实例、定义等信息 */
		
		/** 克隆一个任务 */
		TaskEntity newTask=(TaskEntity) task.clone();
		/** 重置任务字段 */
		newTask.setId(GuidUtil.CreateGuid());
		/** 设置新的处理者为被转发人 */
		newTask.setAssignee(transferUserId);
		/** 重置创建时间 */
		newTask.setCreateTime(ClockUtil.getCurrentTime());
		newTask.setEndTime(null);
		newTask.setCommandId(null);
		newTask.setCommandType(null);
		newTask.setCommandMessage(null);
		newTask.setTaskComment(null);
		newTask.setAgent(null);
		newTask.setAdmin(null);
		newTask.setDraft(false);
		newTask.setOpen(false);
		newTask.setSuspended(false);
		/** 插入任务 */
		Context.getCommandContext().getTaskManager().insert(newTask);
	}

}

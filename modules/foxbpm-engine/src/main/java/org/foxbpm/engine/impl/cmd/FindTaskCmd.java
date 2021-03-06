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
 * @author ych
 */
package org.foxbpm.engine.impl.cmd;

import org.foxbpm.engine.impl.entity.TaskEntity;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;

/**
 * 根据任务Id查询任务
 * @author ych
 *
 */
public class FindTaskCmd implements Command<TaskEntity> {

	protected String taskId;
	
	public FindTaskCmd(String taskId){
		this.taskId=taskId;
	}
	
	public TaskEntity execute(CommandContext commandContext) {
		TaskEntity taskEntity=commandContext.getTaskManager().findTaskById(taskId);
		return taskEntity;
	}

}

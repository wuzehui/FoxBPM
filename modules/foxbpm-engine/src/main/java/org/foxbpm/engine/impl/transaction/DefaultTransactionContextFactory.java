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
 * @author ych
 */
package org.foxbpm.engine.impl.transaction;

import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.transaction.TransactionContext;
import org.foxbpm.engine.transaction.TransactionContextFactory;

/**
 * 默认事务工厂
 * @author ych
 *
 */
public class DefaultTransactionContextFactory implements TransactionContextFactory {

	public TransactionContext openTransactionContext(CommandContext commandContext) {
		return new DefaultTransactionContext(commandContext);
	}

}

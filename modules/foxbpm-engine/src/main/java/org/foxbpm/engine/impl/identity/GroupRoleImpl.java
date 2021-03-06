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
package org.foxbpm.engine.impl.identity;

import java.util.ArrayList;
import java.util.List;

import org.foxbpm.engine.identity.GroupDefinition;
import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.entity.GroupEntity;
import org.foxbpm.engine.sqlsession.ISqlSession;

public class GroupRoleImpl implements GroupDefinition {

	private String type;
	private String name;
	
	public GroupRoleImpl(String type,String definitionName) {
		this.type = type;
		this.name = definitionName;
	}
	
	@SuppressWarnings("unchecked")
	public List<GroupEntity> selectGroupByUserId(String userId) {
		ISqlSession sqlsession = Context.getCommandContext().getSqlSession();
		List<GroupEntity> groups = (List<GroupEntity>)sqlsession.selectList("selectRoleByUserId", userId);
		return groups;
	}
	
	 
	public String getType() {
		return type;
	}
	
	 
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}
	
	@SuppressWarnings("unchecked")
	 
	public List<String> selectUserIdsByGroupId(String groupId) {
		ISqlSession sqlsession = Context.getCommandContext().getSqlSession();
		List<String> userIds = (List<String>)sqlsession.selectList("selectUserIdsByRoleId", groupId);
		return userIds;
	}
	
	 
	public List<GroupEntity> selectChildrenByGroupId(String groupId) {
		List<GroupEntity> groups = new ArrayList<GroupEntity>();
		GroupEntity role = selectGroupByGroupId(groupId);
		if(role != null){
			groups.add(role);
		}
		return groups;
	}
	
	 
	public GroupEntity selectGroupByGroupId(String groupId) {
		ISqlSession sqlSession = Context.getCommandContext().getSqlSession();
		GroupEntity group = (GroupEntity) sqlSession.selectOne("selectRoleById", groupId);
		return group;
	}
	
	@SuppressWarnings("unchecked")
	 
	public List<GroupEntity> selectAllGroup() {
		ISqlSession sqlSession = Context.getCommandContext().getSqlSession();
		List<GroupEntity> groups = (List<GroupEntity>) sqlSession.selectList("selectAllRole");
		return groups;
	}
	
	@SuppressWarnings("unchecked")
	 
	public List<GroupRelationEntity> selectAllRelation() {
		ISqlSession sqlSession = Context.getCommandContext().getSqlSession();
		List<GroupRelationEntity> groupRelations = (List<GroupRelationEntity>) sqlSession.selectList("selectAllRoleRelation");
		return groupRelations;
	}

}

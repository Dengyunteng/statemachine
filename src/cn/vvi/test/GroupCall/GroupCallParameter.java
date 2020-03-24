package cn.vvi.test.GroupCall;

import cn.vvi.util.IParameter;

public class GroupCallParameter implements IParameter{
	private long callId;
	
	private GroupCallStateMachine groupCallStateMachine;

	public long getCallId() {
		return callId;
	}

	public void setCallId(long callId) {
		this.callId = callId;
	}

	public GroupCallStateMachine getGroupCallStateMachine() {
		return groupCallStateMachine;
	}

	public void setGroupCallStateMachine(GroupCallStateMachine groupCallStateMachine) {
		this.groupCallStateMachine = groupCallStateMachine;
	}

	@Override
	public void release() {
		// TODO Auto-generated method stub
		
	}
}

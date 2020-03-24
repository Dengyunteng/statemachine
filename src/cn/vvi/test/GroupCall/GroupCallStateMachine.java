package cn.vvi.test.GroupCall;

import cn.vvi.test.GroupCall.stateHandler.GroupCallGrantedStateHandler;
import cn.vvi.test.GroupCall.stateHandler.GroupCallGrantingStateHandler;
import cn.vvi.test.GroupCall.stateHandler.GroupCallNoGrantedStateHandler;
import cn.vvi.util.StateMachine;

import java.util.LinkedList;

public class GroupCallStateMachine extends StateMachine<GroupCallState, GroupCallParameter> {
	
	private LinkedList<Long> waitingList = new LinkedList<>();
	
	public GroupCallStateMachine(){
//		addStateHandler(new GroupCallIdleStateHandler());
//		addStateHandler(new GroupCallCeasedStateHandler());
//		addStateHandler(new GroupCallEndStateHandler());
		addStateHandler(new GroupCallGrantedStateHandler());
		addStateHandler(new GroupCallGrantingStateHandler());
		addStateHandler(new GroupCallNoGrantedStateHandler());
		
		addAllowMoveStatePair(GroupCallState.IDLE, GroupCallState.GRANTING);
		addAllowMoveStatePair(GroupCallState.GRANTING, GroupCallState.GRANTED);
		addAllowMoveStatePair(GroupCallState.GRANTING, GroupCallState.NOGRANTED);
		addAllowMoveStatePair(GroupCallState.GRANTED, GroupCallState.CEASED);
		addAllowMoveStatePair(GroupCallState.CEASED, GroupCallState.END);
		addAllowMoveStatePair(GroupCallState.END, GroupCallState.IDLE);
		addAllowMoveStatePair(GroupCallState.NOGRANTED, GroupCallState.END);
	}
	
	public void startGroupCall(long callId){
		if(getCurrentState().equals(GroupCallState.IDLE)){
			if(moveToState(GroupCallState.GRANTING)){
				getCurrentParameter().setCallId(callId);
				getCurrentParameter().setGroupCallStateMachine(this);
				handleState();
			}
			else{
				System.err.println("状态迁移失败，未知异常！！！");
			}
		}
		else{
			System.out.println("状态：" + getCurrentState() + "不是IDLE，目前无法处理该组呼，加入到等待队列");
			waitingList.addLast(callId);
		}
	}
	
	public void stopGroupCall(){
		if(moveToState(GroupCallState.CEASED)){
			getCurrentParameter().setCallId(12l);
			handleState();
		}
		else{
			System.err.println("状态迁移失败，未知异常！！！");
		}
	}

	@Override
	protected void onStateHandleOver(GroupCallState state) {
		if(state.equals(GroupCallState.IDLE)){
			if(waitingList.size() > 0){
				long callId = waitingList.removeFirst();
				startGroupCall(callId);
			}
		}
	}
	
	public static void main(String[] args) {
		final GroupCallStateMachine groupCallStateMachine = new GroupCallStateMachine();
		for(int i = 0 ; i<10 ; i++){
			groupCallStateMachine.startGroupCall(i);
		}
		try {
			Thread.sleep(60 * 1000);
		} catch (InterruptedException e) {}
	}
}

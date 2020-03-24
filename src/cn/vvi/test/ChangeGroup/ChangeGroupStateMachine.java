package cn.vvi.test.ChangeGroup;

import cn.vvi.util.StateMachine;
import cn.vvi.test.ChangeGroup.stateHandler.ChangeGroupChangedStateHandler;
import cn.vvi.test.ChangeGroup.stateHandler.ChangeGroupChangingStateHandler;
import cn.vvi.test.ChangeGroup.stateHandler.ChangeGroupIdleStateHandler;

public class ChangeGroupStateMachine extends StateMachine<ChangeGroupState, ChangeGroupParameter> {

	public ChangeGroupStateMachine() {
		addStateHandler(new ChangeGroupIdleStateHandler());
		addStateHandler(new ChangeGroupChangingStateHandler());
		addStateHandler(new ChangeGroupChangedStateHandler());
		
		addAllowMoveStatePair(ChangeGroupState.IDLE, ChangeGroupState.CHANGING);
		addAllowMoveStatePair(ChangeGroupState.CHANGING, ChangeGroupState.CHANGED);
		addAllowMoveStatePair(ChangeGroupState.CHANGED, ChangeGroupState.IDLE);
	}
	
	public void changeGroup(){
		if(moveToState(ChangeGroupState.CHANGING)){
			handleState();
		}
	}

	@Override
	protected void onStateHandleOver(ChangeGroupState state) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		ChangeGroupStateMachine changeGroupStateMachine = new ChangeGroupStateMachine();
		changeGroupStateMachine.changeGroup();
	}
}

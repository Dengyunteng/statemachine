package cn.vvi.test.GroupCall.stateHandler;

import cn.vvi.util.StateHandlerBaseImpl;
import cn.vvi.test.GroupCall.GroupCallParameter;
import cn.vvi.test.GroupCall.GroupCallState;

public class GroupCallGrantingStateHandler extends StateHandlerBaseImpl<GroupCallState, GroupCallParameter> {

	@Override
	public GroupCallState getHandledState() {
		return GroupCallState.GRANTING;
	}

	@Override
	public void handleState(GroupCallParameter parameter) {
		System.out.println("handle GRANTING, and move to GRANTED or NOGRANTED");
		// ju ti ye wu luoji 
		
		if(Math.random() > 0.5){
			moveToState(GroupCallState.GRANTED);
		}
		else{
			moveToState(GroupCallState.NOGRANTED);
		}
	}

}

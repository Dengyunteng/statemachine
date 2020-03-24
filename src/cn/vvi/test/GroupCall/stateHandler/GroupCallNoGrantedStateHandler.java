package cn.vvi.test.GroupCall.stateHandler;

import cn.vvi.util.StateHandlerBaseImpl;
import cn.vvi.test.GroupCall.GroupCallParameter;
import cn.vvi.test.GroupCall.GroupCallState;

public class GroupCallNoGrantedStateHandler extends StateHandlerBaseImpl<GroupCallState, GroupCallParameter> {

	@Override
	public GroupCallState getHandledState() {
		return GroupCallState.NOGRANTED;
	}

	@Override
	public void handleState(GroupCallParameter parameter) {
		System.out.println("handle NOGRANTED, and move to END");
		moveToState(GroupCallState.END);
	}

}

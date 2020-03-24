package cn.vvi.test.GroupCall;

import cn.vvi.util.IState;

public enum GroupCallState implements IState<GroupCallState> {
	IDLE,GRANTING,GRANTED,CEASED,NOGRANTED,END;

	@Override
	public GroupCallState getDefaultState() {
		return IDLE;
	}

}

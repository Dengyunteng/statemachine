package cn.vvi.test.ChangeGroup;

import cn.vvi.util.IState;

public enum ChangeGroupState implements IState<ChangeGroupState> {
	IDLE,CHANGING,CHANGED;

	@Override
	public ChangeGroupState getDefaultState() {
		return IDLE;
	}

}

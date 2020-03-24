package cn.vvi.test.ChangeGroup.stateHandler;

import cn.vvi.util.StateHandlerBaseImpl;
import cn.vvi.test.ChangeGroup.ChangeGroupParameter;
import cn.vvi.test.ChangeGroup.ChangeGroupState;

public class ChangeGroupChangedStateHandler extends StateHandlerBaseImpl<ChangeGroupState, ChangeGroupParameter> {

	@Override
	public ChangeGroupState getHandledState() {
		return ChangeGroupState.CHANGED;
	}

	@Override
	public void handleState(ChangeGroupParameter parameter) {
		System.out.println("handle CHANGED, and move to IDLE");
		moveToState(ChangeGroupState.IDLE);
	}

}

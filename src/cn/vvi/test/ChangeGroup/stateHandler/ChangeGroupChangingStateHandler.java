package cn.vvi.test.ChangeGroup.stateHandler;

import cn.vvi.util.StateHandlerBaseImpl;
import cn.vvi.test.ChangeGroup.ChangeGroupParameter;
import cn.vvi.test.ChangeGroup.ChangeGroupState;

public class ChangeGroupChangingStateHandler extends StateHandlerBaseImpl<ChangeGroupState, ChangeGroupParameter> {

	@Override
	public ChangeGroupState getHandledState() {
		return ChangeGroupState.CHANGING;
	}

	@Override
	public void handleState(ChangeGroupParameter parameter) {
		System.out.println("handle CHANGING, and move to CHANGED");
		moveToState(ChangeGroupState.CHANGED);
	}

}

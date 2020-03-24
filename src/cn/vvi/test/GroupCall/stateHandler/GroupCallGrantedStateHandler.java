package cn.vvi.test.GroupCall.stateHandler;

import cn.vvi.test.GroupCall.GroupCallParameter;
import cn.vvi.test.GroupCall.GroupCallState;
import cn.vvi.util.StateHandlerBaseImpl;

import java.util.Timer;
import java.util.TimerTask;

public class GroupCallGrantedStateHandler extends StateHandlerBaseImpl<GroupCallState, GroupCallParameter> {

	private final Timer timer = new Timer();
	
	@Override
	public GroupCallState getHandledState() {
		return GroupCallState.GRANTED;
	}

	@Override
	public void handleState(final GroupCallParameter parameter) {
		System.out.println("handle GRANTED, CallId = " + parameter.getCallId() + ", wait 2s to ceased");
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				parameter.getGroupCallStateMachine().stopGroupCall();
			}
		}, 2 * 1000);
	}

}

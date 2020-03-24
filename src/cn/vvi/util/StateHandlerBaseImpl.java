package cn.vvi.util;

public abstract class StateHandlerBaseImpl<State extends Enum<State> & IState<State>, Parameter extends IParameter> implements IStateHandler<State, Parameter> {

	private IMoveToState<State> moveToState;

	@Override
	public void setMoveToStateEvent(IMoveToState<State> moveToState) {
		this.moveToState = moveToState;
	}
	
	protected boolean moveToState(State state){
		if(moveToState != null){
			return moveToState.moveToState(state);
		}
		throw new IllegalArgumentException("moveToState不能为null");
	}
}

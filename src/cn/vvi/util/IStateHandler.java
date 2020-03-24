package cn.vvi.util;

/** 状态处理类必须实现该接口 */
public interface IStateHandler<State extends Enum<State> & IState<State>, Parameter extends IParameter> {
	public State getHandledState();
	public void handleState(Parameter parameter);
	public void setMoveToStateEvent(IMoveToState<State> moveToState);
}

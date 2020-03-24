package cn.vvi.util;

/** 状态描述接口 */
public interface IState<State extends Enum<State> & IState<State>> {
	public State getDefaultState();
}

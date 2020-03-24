package cn.vvi.util;

import java.util.ArrayList;

public interface IMoveToState<State extends Enum<State> & IState<State>> {
	public boolean moveToState(State state);
}

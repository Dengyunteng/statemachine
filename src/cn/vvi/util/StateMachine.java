package cn.vvi.util;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public abstract class StateMachine<State extends Enum<State> & IState<State>, Parameter extends IParameter> {
	private final Map<State, IStateHandler<State, Parameter>> state2Handler = new HashMap<>();
	private State currentState;
	private final State defaultState;
	private Parameter currentParameter;
	private final Class<State> stateClass;
	private final Class<Parameter> parameterClass;
	private final Map<State, List<State>> stateMoveAllowMap = new HashMap<>();
	private boolean handleStateRunning = false;
	private final IMoveToState<State> moveToState = new IMoveToState<State>() {
		@Override
		public boolean moveToState(State state) {
			return StateMachine.this.moveToState(state);
		}
	};
	
	@SuppressWarnings("unchecked")
	public StateMachine(){
		//获取该状态机中泛型State的Class对象，并将currentState赋值为State类中方法getDefaultState的返回值
		Type type = this.getClass();
		while(true){
			if(type instanceof ParameterizedType && ((ParameterizedType)type).getRawType().equals(StateMachine.class)){
				break;
			}
			if(type.equals(Object.class)){
				type = null;
				break;
			}
			if(type instanceof Class<?>){
				type = ((Class<?>) type).getGenericSuperclass();
			}
			else{
				type = null;
				break;
			}
		}
		
		if(type != null){
			stateClass = (Class<State>)((ParameterizedType) type).getActualTypeArguments()[0];
			parameterClass = (Class<Parameter>)((ParameterizedType) type).getActualTypeArguments()[1];
			Method valuesMethod;
			try {
				valuesMethod = stateClass.getDeclaredMethod("values");
			} catch (NoSuchMethodException | SecurityException e1) {
				throw new IllegalArgumentException("State不是一个枚举类或该枚举中无法获取values方法");
			}
			State[] allStates;
			try {
				allStates = (State[]) valuesMethod.invoke(null);
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | ClassCastException e) {
				throw new IllegalArgumentException("State类无法调用values方法，请检查State类型是否正确传入");
			}
			if(allStates.length > 0){
				defaultState = allStates[0].getDefaultState();
				currentState = defaultState;
				if(defaultState == null){
					throw new IllegalArgumentException("枚举类State中，方法getDefaultState的返回值不能为null");
				}
			}
			else{
				throw new IllegalArgumentException("枚举类State中至少需要有一个枚举对象");
			}
		}
		else{
			throw new IllegalArgumentException("无法获取StateMachine的参数化类型对象");
		}
	}
	
	/**
	 * 添加一个状态处理类，如果该状态处理类对应的状态已经被添加过，则抛出InvalidParameterException异常
	 * @param handler 状态处理类
	 */
	protected void addStateHandler(IStateHandler<State, Parameter> handler){
		if(handler.getHandledState() == null){
			throw new InvalidParameterException("handler的方法getHandledState()的返回值不能为null");
		}
		if(state2Handler.containsKey(handler.getHandledState())){
			throw new InvalidParameterException("状态"+handler.getHandledState()+"已经被添加过，不能重复添加");
		}
		state2Handler.put(handler.getHandledState(), handler);
		handler.setMoveToStateEvent(moveToState);
	}
	
	/**
	 *  添加允许从一个状态迁移到另一个状态的状态对列表
	 * @param from 初始状态
	 * @param to 目标状态
	 * @return 返回true表示允许这种变迁，返回false表示不允许这种变迁，
	 */
	protected void addAllowMoveStatePair(State from, State to) {
		if(from != null && to != null){
			List<State> stateList = stateMoveAllowMap.get(from);
			if(stateList == null){
				stateList = new ArrayList<>();
				stateMoveAllowMap.put(from, stateList);
			}
			if(!stateList.contains(to)){
				stateList.add(to);
			}
		}
	}
	
	/** 获取当前状态 */
	public State getCurrentState(){
		return currentState;
	}
	
	/** 获取当前参数 */
	protected Parameter getCurrentParameter(){
		return currentParameter;
	}
	
	protected synchronized void handleState(){
		if(!handleStateRunning){
			handleStateRunning = true;
			State oldState;
			IStateHandler<State, Parameter> handler;
			do{
				oldState = currentState;
				handler = state2Handler.get(currentState);
				if(handler != null){
					handler.handleState(currentParameter);
				}
				if(oldState.equals(defaultState) && currentState.equals(oldState)){
					if(currentParameter != null){
						currentParameter.release();
						currentParameter = null;
					}
				}
			}
			while(!currentState.equals(oldState));
			handleStateRunning = false;
		}
	}
	
	protected abstract void onStateHandleOver(State state);
	/**
	 * 将当前状态改变为目标状态
	 * @param state 目标
	 * @return 改变成功则返回true，改变失败返回false
	 */
	protected synchronized boolean moveToState(State state){
		if(allowMoveState(currentState, state)){
			if(currentState.equals(defaultState)){
				try {
					currentParameter = parameterClass.newInstance();
				} catch (InstantiationException | IllegalAccessException e) {
					throw new RuntimeException("Parameter的对象无法创建，请确认Parameter有public的无参构造函数！！！", e);
				}
			}
			onStateHandleOver(currentState);
			//("当前状态："+currentState+"    to："+state);
			this.currentState = state;
			return true;
		}
		else{
			//("不允许从状态：" + currentState + " move到状态：" + state);
		}
		return false;
	}
	
	/**
	 *  状态的变迁主要在每个状态的处理函数中触发
	 *  该方法用于显示的声明是否能从from状态迁移到to状态
	 *  若不打算显示的声明，则直接返回true即可
	 * @param from
	 * @param to
	 * @return 返回true表示允许这种变迁，返回false表示不允许这种变迁，
	 */
	private boolean allowMoveState(State from, State to){
		return stateMoveAllowMap.containsKey(from) && stateMoveAllowMap.get(from).contains(to);
	}
	public static void main(String[] args){
		System.out.print("dada");
	}
}

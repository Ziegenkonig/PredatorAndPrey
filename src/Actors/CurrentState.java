package Actors;

//Each instance of prey and predator are assigned a single state at a given time

public enum CurrentState {
	IDLE,
	HUNTING,
	EATING,
	MOVINGTOWATER,
	DRINKING,
	SLEEPING,
	SNEAKING,
	MOVING
}

package com.example.mongescreeninginterface.helpers;

public class UserObjectActionInfo {
    private static UserObjectActionInfo instance;
    public static UserObjectActionInfo getInstance(){
        if(instance == null){
            instance = new UserObjectActionInfo();
        }
        return instance;
    }

    private UserObjectActionInfo(){
        currentActionIndex = 0;
    }
    private UserObjectAction[] actions = UserObjectAction.values();
    private int currentActionIndex;

    public void toggleAction(){
        currentActionIndex = (currentActionIndex+1)%actions.length;
    }

    public UserObjectAction getAction(){
        return actions[currentActionIndex];
    }
}

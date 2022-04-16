package com.team404.battleship;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.ReflectionPool;

import java.util.InputMismatchException;

public class ActionsFactory {

    private final ObjectMap<Class, Pool> actionPool = new ObjectMap();
    public Action obtain(Class<? extends Action> cls)
    {
        Pool pool = (Pool) get(cls);
        Action act = (Action) pool.obtain();
        act.setPool(pool);
        return act;
    }
    public <T extends Action>  Pool<T> get(Class<? extends Action> type){
        Pool<T> pool = actionPool.get(type);
        if(pool == null){
            pool = new ReflectionPool(type, 4, 15);
            actionPool.put(type,pool);
        }
        return pool;
    }

    private static ActionsFactory m_instance= null;

    public static ActionsFactory getInstance(){
        if(m_instance == null)
        {
            m_instance = new ActionsFactory();
        }
        return m_instance;
    }
    private ActionsFactory(){

    }
}

package com.team404.battleship;

import android.support.annotation.RequiresApi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.SnapshotArray;

import java.util.ArrayList;
import java.util.Random;

public class ActionScene extends BaseScreen{
    private final int cellSize;
    private ActionSceneData sceneData;
    private Stage stage;
    private Grid grid;
    private Grid grid2;
    private ArrayList<ShipData> shipList;
    private ArrayList<Ship> ships;
    private SpriteBatch batch;


    private Stack selfTableStack;
    private Stack OpponentTableStack;
    private Stack UIWidgetStack;
    final BaseGame game;
    public class ShipData{
        boolean Orientation;
        float x;
        float y;
        int logicX;
        int logicY;
        int size;
        ShipData(float x, float y,int lx,int ly, int size, boolean ort)
        {
            this.x = x;
            this.y = y;
            this.size = size;
            this.Orientation = ort;
            this.logicX= lx;
            this.logicY=ly;
        }
    }
    public ActionScene(PlanningScreen.PlanningScreenRedirectPayload payload){
        super(payload);
        shipList = new ArrayList<>();
        ArrayList<Ship> ships = payload.grid.getShipsList();
        for(Ship s : ships)
        {
            shipList.add(new ShipData(s.getX(),s.getY(),s.getBoardIndexX(),s.getBoardIndexY(),s.getSize(),s.getOrientation()));

        }
        Player p1 = new Player(shipList);
        Player p2 = new Player(shipList);
        cellSize = (Gdx.graphics.getHeight() - 150)/10;
        game = new BaseGame(p1,p2);

    }

    @Override
    void createData() {
        sceneData = new ActionSceneData();
        this.data = sceneData;
    }

    @Override
    public void show() {
        stage = new Stage();
        batch = new SpriteBatch();

        final float animation_Duration = .3f;



//        grid.addListener(new ClickListener(){
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                MoveToAction action = Actions.moveTo(Gdx.graphics.getWidth()/2f,0);
//                action.setDuration(animation_Duration);
//                st.addAction(action);
//
//            }
//        });
//
//
//
//        grid2 = new Grid(cellSize, sceneData.asset.get("skin/GamePlaySkin/gameplay_skin.json",Skin.class),10,10, InputListener.class ){
//            @Override
//            public void initListener() {
//                super.initListener();
//                SnapshotArray<Actor> cells = getChildren();
//                for(int i = 0; i < cells.size;i++)
//                {
//                    final Actor act = cells.get(i);
//                    final int _x = i%10;
//                    final int _y = 9-i/10;
//                    act.addListener(new ClickListener(){
//                        @Override
//                        public void clicked(InputEvent event, final float x, final float y) {
//                            Pool<Rumble> action = ActionsFactory.getInstance().get(Rumble.class);
//
//                            RunnableAction runable = Actions.run(new Runnable() {
//                                @Override
//                                public void run() {
//                                    MoveToAction action2 = Actions.moveTo(-cellSize*5,0);
//                                    action2.setDuration(animation_Duration);
//                                    st.addAction(action2);
//                                    if(game.end()) {
//                                        throw new NullPointerException();
//                                    }
//                                    Ship k = new Ship(sceneData.asset.get("ship5_h.png",Texture.class),sceneData.asset.get("ship5_v.png",Texture.class),cellSize,1,false);
//                                    Table tbl = new Table();
//                                    tbl.add(k);
//
//                                    st.addActor(tbl);
//                                    //k.setPosition(act.getX(),act.getY());
//                                    grid2.gridSnap(k,x,y);
//                                }
//                            });
//                            stage.addAction(Actions.sequence(action.obtain(),Actions.delay(0.5f),runable,Actions.delay(1.0f)));
//                        }
//                    });
//                }
//            }
//        };
//
//        grid.initListener();
//        grid.setPosition(Gdx.graphics.getWidth()/2f,Gdx.graphics.getHeight()/2f);
//
//
//
//        st.add(grid2);
//        st.setPosition(Gdx.graphics.getWidth()/2f,Gdx.graphics.getHeight()/2f);
//        grid2.initListener();
//
//
//
//
//        stage.addActor(st);
//        stage.addActor(grid);


        InitSelfTable();
        InitOpponentTable();
        Gdx.input.setInputProcessor(stage);
    }

    public void InitSelfTable(){
        if(stage == null) return;

        selfTableStack = new Stack();
        stage.addActor(selfTableStack);
        grid = new Grid(cellSize, sceneData.asset.get("skin/GamePlaySkin/gameplay_skin.json",Skin.class),10,10);
        selfTableStack.add(grid);
        selfTableStack.setFillParent(true);
        selfTableStack.setLayoutEnabled(false);
        grid.setLayoutEnabled(true);
        grid.setPosition(Gdx.graphics.getWidth()/2f,Gdx.graphics.getHeight()/2f);
        for(ShipData d : shipList)
        {
            Ship k = new Ship(sceneData.asset.get("ship5_h.png",Texture.class),sceneData.asset.get("ship5_v.png",Texture.class),cellSize,d.size,false);

            if(d.Orientation){
                k.toggleOrientation();
            }
            k.setPosition(d.x,d.y);
            selfTableStack.add(k);
        }
        game.AddListener(new GameEvent() {
            @Override
            public void GetShoot(int x, int y) {
                final int _x = x;
                final int _y = y;
                stage.addAction(Actions.sequence(
                        Actions.delay(1f),
                        Actions.run(new Runnable() {
                            @Override
                            public void run() {
                                Ship k = new Ship(sceneData.asset.get("ship5_h.png", Texture.class), sceneData.asset.get("ship5_v.png", Texture.class), cellSize, 1, false);
                                Vector2 pos = new Vector2();
                                Actor act = grid.getChild((9 - _y) * 10 + _x);
                                act.localToStageCoordinates(pos);
                                k.setPosition(pos.x, pos.y);
                                selfTableStack.addActor(k);
                                if (!game.end()) {
                                    stage.addAction(Actions.sequence(Actions.delay(3f),
                                            Actions.run(new Runnable() {
                                                @Override
                                                public void run() {
                                                    OpponentTableStack.addAction(Actions.moveTo(0, 0, 0.5f));
                                                }
                                            })));
                                } else {
                                    stage.addAction(Actions.sequence(Actions.delay(3f),
                                            Actions.run(new Runnable() {
                                                @Override
                                                public void run() {
                                                    throw new NullPointerException();
                                                }
                                            })));

                                }
                            }
                        })
                ));
            }
        });
    }

    public void InitOpponentTable(){
        if(stage == null) return;

        OpponentTableStack = new Stack();
        stage.addActor(OpponentTableStack);
        grid2 = new Grid(cellSize, sceneData.asset.get("skin/GamePlaySkin/gameplay_skin.json",Skin.class),10,10){
            @Override
            public void initListener() {
                super.initListener();
                SnapshotArray<Actor> acts = getChildren();
                for(int x = 0; x <10; x++)
                {
                    for(int y = 0; y < 10;y++)
                    {
                        final int _x = x;
                        final int _y = y;
                        final Actor act = acts.get(10*y+x);

                        act.addListener(new ClickListener(){
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                if(!game.shoot(_x,9-_y)) return;
                                if(game.end()) {
                                    throw new NullPointerException();
//                                    return;
                                }
                                Ship k = new Ship(sceneData.asset.get("ship5_h.png",Texture.class),sceneData.asset.get("ship5_v.png",Texture.class),cellSize,1,false);
                                Vector2 pos = new Vector2();
                                act.localToStageCoordinates(pos);
                                k.setPosition(pos.x,pos.y);
                                OpponentTableStack.addActor(k);


                                Pool<Rumble> action = ActionsFactory.getInstance().get(Rumble.class);
                                stage.addAction(Actions.sequence(
                                        action.obtain(),
                                        Actions.delay(0.5f),
                                        Actions.run(new Runnable() {
                                            @Override
                                            public void run() {
                                                OpponentTableStack.addAction(Actions.moveTo(-Gdx.graphics.getWidth(),0,0.5f));
                                                grid.setTouchable(Touchable.disabled);
                                                game.next();
                                            }
                                        })
                                ));

                            }
                        });
                    }
                }
            }
        };
        OpponentTableStack.add(grid2);
        OpponentTableStack.setFillParent(true);
        OpponentTableStack.setLayoutEnabled(false);
        grid2.setLayoutEnabled(true);
        grid2.setPosition(Gdx.graphics.getWidth()/2f,Gdx.graphics.getHeight()/2f);
        OpponentTableStack.setPosition(0,0);
    }
    @Override
    public void render(float delta) {

        batch.begin();
        batch.draw(sceneData.empty_bg,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        batch.end();
        stage.act(delta);
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public class ActionSceneData implements ILoadable{
        public AssetManager asset;
        public Texture empty_bg;
        ActionSceneData(){
            asset = new AssetManager();
            asset.load("empty_bg.jpg",Texture.class);
            asset.load("skin/GamePlaySkin/gameplay_skin.json", Skin.class);
            asset.load("ship5_v.png", Texture.class);
            asset.load("ship5_h.png", Texture.class);
        }

        public void onFinish(){
            empty_bg = asset.get("empty_bg.jpg");

        }
        @Override
        public boolean load() {
            boolean isFinish = asset.update();
            if(isFinish){
                onFinish();
            }
            return isFinish;
        }
    }
}

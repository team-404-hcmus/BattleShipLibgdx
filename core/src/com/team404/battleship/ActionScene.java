package com.team404.battleship;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.SnapshotArray;

import java.util.ArrayList;

public class ActionScene extends BaseScreen{
    private final int cellSize;
    private ActionSceneData sceneData;
    private Stage stage;
    private Grid grid;
    private ArrayList<ShipData> shipList;
    private ArrayList<Ship> ships;
    private SpriteBatch batch;
    public class ShipData{
        boolean Orientation;
        float x;
        float y;
        int size;
        ShipData(float x, float y, int size, boolean ort)
        {
            this.x = x;
            this.y = y;
            this.size = size;
            this.Orientation = ort;
        }
    }
    public ActionScene(PlanningScreen.PlanningScreenRedirectPayload payload){
        super(payload);
        shipList = new ArrayList<>();
        ArrayList<Ship> ships = payload.grid.getShipsList();
        for(Ship s : ships)
        {
            shipList.add(new ShipData(s.getX(),s.getY(),s.getSize(),s.getOrientation()));
        }
        cellSize = (Gdx.graphics.getHeight() - 150)/10;

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
        grid = new Grid(cellSize, sceneData.asset.get("skin/GamePlaySkin/gameplay_skin.json",Skin.class),10,10, InputListener.class ){
            @Override
            public void initListener() {
                layout();
                SnapshotArray<Actor> cells = getChildren();

                for(int i = 0; i < cells.size;i++)
                {
                    final Actor act = cells.get(i);
                    act.addListener(new ClickListener(){
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            Pool<Rumble> action = ActionsFactory.getInstance().get(Rumble.class);
                            stage.addAction(action.obtain());
                        }
                    });
                }
            }
        };
        grid.initListener();
        grid.setPosition(Gdx.graphics.getWidth()/2f,Gdx.graphics.getHeight()/2f);
        stage.addActor(grid);
        for(ShipData d : shipList)
        {
            Ship k = new Ship(sceneData.asset.get("ship5_h.png",Texture.class),sceneData.asset.get("ship5_v.png",Texture.class),cellSize,d.size,false);
            if(d.Orientation){
                k.toggleOrientation();
            }
            k.setPosition(d.x,d.y);
            stage.addActor(k);
        }

        Gdx.input.setInputProcessor(stage);
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

package com.team404.battleship;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.ArrayList;

public class ActionScene extends BaseScreen{
    private final int cellSize;
    private ActionSceneData sceneData;
    private Stage stage;
    private Grid grid;
    private ArrayList<ShipData> shipList;
    class ShipData{
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
        final Ship k = new Ship(sceneData.asset.get("ship5_h.png",Texture.class),sceneData.asset.get("ship5_v.png",Texture.class),cellSize,5,false);
        final Ship k2 = new Ship(sceneData.asset.get("ship5_h.png",Texture.class),sceneData.asset.get("ship5_v.png",Texture.class),cellSize,2,false);
        final Ship k3 = new Ship(sceneData.asset.get("ship5_h.png",Texture.class),sceneData.asset.get("ship5_v.png",Texture.class),cellSize,3,false);
        stage = new Stage();
        for(ShipData d : shipList)
        {
            k.moveData(d.x,d.y,d.size,d.Orientation);
            k2.moveData(d.x,d.y,d.size,d.Orientation);
            k3.moveData(d.x,d.y,d.size,d.Orientation);
        }
        stage.addActor(k);
        stage.addActor(k2);
        stage.addActor(k3);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
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

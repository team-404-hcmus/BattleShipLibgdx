package com.team404.battleship;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.SnapshotArray;

public class PlanningScreen extends BaseScreen{

    private SpriteBatch batch;
    private Stage stage;
    private final float cellSize;
    private GamePlayData gamePlayData;
    final int cellCountsRowCol = 10;
    PlanningScreen() {
        super(null);
        cellSize = (Gdx.graphics.getHeight() - 150)/10;
    }

    @Override
    void createData() {
        gamePlayData = new GamePlayData();
        data = gamePlayData;
    }

    @Override
    public void show() {

        batch = new SpriteBatch();
        stage = new Stage();
        final Ship k = new Ship(gamePlayData.asset.get("ship5_h.png",Texture.class),gamePlayData.asset.get("ship5_v.png",Texture.class),cellSize,5);
        final Ship k2 = new Ship(gamePlayData.asset.get("ship5_h.png",Texture.class),gamePlayData.asset.get("ship5_v.png",Texture.class),cellSize,2);
        final Ship k3 = new Ship(gamePlayData.asset.get("ship5_h.png",Texture.class),gamePlayData.asset.get("ship5_v.png",Texture.class),cellSize,3);


        final Grid  grid = new Grid(cellSize, gamePlayData.asset.get("skin/GamePlaySkin/gameplay_skin.json",Skin.class),cellCountsRowCol,cellCountsRowCol,InputListener.class );

        grid.initListener();
        grid.setPosition(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);
        DragAndDrop.Target t = new DragAndDrop.Target(grid) {
            @Override
            public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                if(source.getActor() instanceof Ship)
                {
                    return true;
                }
                return false;
            }

            @Override
            public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                Grid act = (Grid)this.getActor();
                Ship ship = (Ship)source.getActor();
                act.gridSnap(ship,ship.getX(),ship.getY());
                if(act.isSnappable(ship))
                {
                   act.addShip(ship);
                   ship.newOrigin();
                }
                else{
                    ship.returnToOrigin();
                    act.gridSnap(ship,ship.getX(),ship.getY());
                }

            }
        };
        k.AddTarget(t);
        k2.AddTarget(t);
        Table main = new Table();
        main.setFillParent(true);
        main.add(k);
        main.row();
        main.add(k2);
        main.row();
        main.add(k3);
        main.left();
        main.setDebug(true);
        k.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                k.newOrigin();
                k.toggleOrientation();
                grid.gridSnap(k,k.getX(),k.getY());
                if(!((Grid)k.getTarget().getActor()).isSnappable(k)){
                    k.toggleOrientation();
                    k.returnToOrigin();
                }
                k.newOrigin();
            }
        });
        k2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                k2.newOrigin();
                k2.toggleOrientation();
                grid.gridSnap(k2,k2.getX(),k2.getY());
                if(!((Grid)k2.getTarget().getActor()).isSnappable(k2)){
                    k2.toggleOrientation();
                    k2.returnToOrigin();
                }
                k2.newOrigin();
            }
        });
        k3.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                PlanningScreenRedirectPayload payload = new PlanningScreenRedirectPayload();
                payload.grid = grid;
                ((battleship)Gdx.app.getApplicationListener()).setScreen(new LoadingScreen<ActionScene>(new ActionScene(payload)));
            }
        });
        grid.setPosition(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);
        stage.addActor(grid);
        stage.addActor(main);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        drawBackGround(delta);
        stage.draw();
        stage.act(delta);

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
        gamePlayData.empty_bg.dispose();
        gamePlayData.asset.dispose();
    }

    @Override
    public void dispose() {

    }

    private void drawBackGround(float delta){
        batch.begin();
        batch.draw(gamePlayData.empty_bg,0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        batch.end();
    }


    public class GamePlayData implements ILoadable{
        public AssetManager asset;
        public Texture empty_bg;
        GamePlayData(){
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

    class PlanningScreenRedirectPayload extends  RedirectingPayload{
        public Grid grid;
    }


}
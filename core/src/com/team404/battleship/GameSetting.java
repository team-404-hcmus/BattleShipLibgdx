package com.team404.battleship;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Pool;

public class GameSetting extends BaseScreen{

    private SpriteBatch batch;
    private Stage stage;
    private Table cellTable;
    private final float cellSize;
    private GamePlayData gamePlayData;
    private boolean clicked = true;
    GameSetting() {
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
        cellTable = new Table();
        cellTable.setFillParent(true);


        for(int i = 0; i < 10; i++)
        {
            for(int k = 0;k < 10; k++)
            {
                ImageButton img = new ImageButton(gamePlayData.asset.get("skin/GamePlaySkin/gameplay_skin.json",Skin.class)){
                    @Override
                    public void act(float delta){
                        super.act(delta);
                    }
                };
                img.setTransform(true);
                img.addListener(new InputListener(){
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return clicked;
                    }

                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        super.touchUp(event, x, y, pointer, button);
                        cellTable.addAction(sequence(run(new Runnable() {
                            @Override
                            public void run() {
                                clicked = false;
                            }
                        }),new Rumble(),run(new Runnable() {
                            @Override
                            public void run() {
                                clicked = true;
                            }
                        })));
                    }
                });
                cellTable.add(img).size(cellSize);

            }
            cellTable.row();
        }
        Ship k = new Ship(gamePlayData.asset.get("ship5.png",Texture.class),cellSize,5);
        DragAndDrop dnd = new DragAndDrop();
        dnd.addSource(new DragAndDrop.Source(k){

            @Override
            public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
                DragAndDrop.Payload pl = new DragAndDrop.Payload();
                pl.setDragActor(this.getActor());
                return pl;
            }
        });
        dnd.addTarget(new DragAndDrop.Target(cellTable) {
            @Override
            public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                return true;
            }

            @Override
            public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                    source.getActor().setPosition(0,0);
            }
        });


        stage.addActor(cellTable);
        stage.addActor(k);
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
            asset.load("ship5.png", Texture.class);
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

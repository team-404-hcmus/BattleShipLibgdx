package com.team404.battleship;

import android.support.annotation.RequiresApi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
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
    private Label lbl;


    private Stack selfTableStack;
    private Stack OpponentTableStack;
    private Stack UIWidgetStack;
    final BaseGame game;

    Animation<TextureRegion> walkAnimation; // Must declare frame type (TextureRegion)
    TextureRegion[] walkFrames;

    ExplosionManager AnimManager;
    float stateTime;


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
        InitSelfTable();
        InitOpponentTable();

        final int cols = 8;
        final int rows = 4;
        TextureRegion[][] tmp = TextureRegion.split(sceneData.explosionSheet,
                sceneData.explosionSheet.getWidth() / cols,
                sceneData.explosionSheet.getHeight() / rows);

        walkFrames = new TextureRegion[rows*cols];
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }
        AnimManager = new ExplosionManager(walkFrames);

        Stack UIStack = new Stack();
        Table leftSideButton = new Table();
        lbl = new Label("Your Turn",asset.getSkin());
        leftSideButton.add(lbl);
        leftSideButton.right().top().pad(100f);
//        leftSideButton.setFillParent(true);
        UIStack.add(leftSideButton);
        UIStack.setFillParent(true);

        stage.addActor(UIStack);
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
                                Ship k = new Ship(sceneData.cell_false, sceneData.cell_false, cellSize, 1, false);
                                Vector2 pos = new Vector2();
                                Actor act = grid.getChild((9 - _y) * 10 + _x);
                                act.localToStageCoordinates(pos);
                                k.setPosition(pos.x, pos.y);
                                selfTableStack.addActor(k);

                                SpawnRocket(pos.x, pos.y,false);
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
                                if(game.end()) {
                                    throw new UnknownError();
                                }
                                BaseGame.ShootResult result = game.shoot(_x,9-_y);
                                if(!result.result) return;
                                Texture tx = result.m_hasShip?sceneData.cell_true:sceneData.cell_false;

                                Ship k = new Ship(tx,tx,cellSize,1,false);
                                final Vector2 pos = new Vector2();
                                act.localToStageCoordinates(pos);
                                k.setPosition(pos.x,pos.y);
                                OpponentTableStack.addActor(k);
                                SpawnRocket(pos.x,pos.y,true);
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
        AnimManager.render(batch,delta);
    }

    public void OpenOverLay(boolean isWin){
        String result = isWin?"You won":"You lose";
        TextButton txt = new TextButton("Retry",asset.getSkin());
        Table tbl = new Table();
        tbl.setFillParent(true);
        txt.setText(result);
        tbl.add(txt);
        txt.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((battleship)Gdx.app.getApplicationListener()).setScreen(new LoadingScreen<>(new HomeScreen()));
            }
        });
        for(Actor i :grid.getChildren())
        {
            i.setTouchable(Touchable.disabled);
        }
        for(Actor i :grid2.getChildren())
        {
            i.setTouchable(Touchable.disabled);
        }
        stage.addActor(tbl);
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
        public Texture cell_false;
        public Texture cell_true;
        public Texture explosionSheet;
        public Texture Overlay;
        public Texture rocket;
        ActionSceneData(){
            asset = new AssetManager();
            asset.load("empty_bg.jpg",Texture.class);
            asset.load("skin/GamePlaySkin/gameplay_skin.json", Skin.class);
            asset.load("ship5_v.png", Texture.class);
            asset.load("ship5_h.png", Texture.class);
            asset.load("cell_false.png", Texture.class);
            asset.load("cell_true.png", Texture.class);
            asset.load("expsheet.png",Texture.class);
            asset.load("overlay.png",Texture.class);
            asset.load("rocket.png",Texture.class);
        }

        public void onFinish(){
            empty_bg = asset.get("empty_bg.jpg");
            cell_false = asset.get("cell_false.png");
            cell_true=asset.get("cell_true.png");
            explosionSheet = asset.get("expsheet.png");
            Overlay = asset.get("overlay.png");
            rocket = asset.get("rocket.png");
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

    Rocket _k;
    boolean isWin = false;
    public void SpawnRocket(final float Rx, final float Ry, final boolean Who){
        Random generator = new Random();
        int Hposition = generator.nextInt(2); //0: top; 1: right; 2: bottom; 3:right
        int Vposition = generator.nextInt(2); //0: top; 1: right; 2: bottom; 3:right
        int x;
        int y;
        if (Hposition == 0){
            y = generator.nextInt(10) + Gdx.graphics.getHeight();
        } else {
            y = generator.nextInt(10)-10;
        }
        if (Vposition == 0){
            x = generator.nextInt(10) + Gdx.graphics.getWidth();
        } else {
            x = generator.nextInt(10)-10;
        }

//        if(_k == null)
//        {
//            _k = new Ship(sceneData.asset.get("ship5_h.png",Texture.class),sceneData.asset.get("ship5_v.png",Texture.class),cellSize,2,false);
//            stage.addActor(_k);
//        }
        if(_k == null)
        {
            _k = new Rocket(sceneData.rocket,34,160);
            stage.addActor(_k);
        }
        _k.setPosition(x,y);
        Vector2 start = new Vector2(_k.getX(),_k.getY());
        Vector2 target = new Vector2(Rx,Ry);
        target.sub(start);
        float angel = target.angleDeg();
        _k.setRotation(angel);
        _k.addAction(Actions.sequence(Actions.visible(true),
                Actions.moveTo(Rx,Ry,1f, Interpolation.exp5In),
                Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        asset.sound.play();
                        AnimManager.spawnAt(Rx-50,Ry-50,cellSize+100,0.05f);
                    }
                }),
                Actions.visible(false),
                Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        Pool<Rumble> action = ActionsFactory.getInstance().get(Rumble.class);
                        stage.addAction(Actions.sequence(
                                action.obtain(),
                                Actions.delay(0.5f),
                                Actions.run(new Runnable() {
                                    @Override
                                    public void run() {
                                        if(game.end()) {
                                            OpenOverLay(Who);
                                            return;
                                        }
                                        ToggleOpponentTable(Who);
                                    }
                                })
                        ));
                    }
                })));
    }

    private void ToggleOpponentTable(boolean Who){
        if(Who)
        {
            lbl.setText("Opponent Turn");
            OpponentTableStack.addAction(Actions.moveTo(-Gdx.graphics.getWidth(),0,0.5f));
            game.next();
        }
        else{
            lbl.setText("Your Turn");
            stage.addAction(Actions.sequence(Actions.delay(1.5f),
                    Actions.run(new Runnable() {
                        @Override
                        public void run() {
                            OpponentTableStack.addAction(Actions.moveTo(0, 0, 0.5f));
                        }
            })));
        }

    }
}

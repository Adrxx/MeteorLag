package mx.itesm.meteorlag;

import android.util.Log;

import mx.itesm.meteorlag.Interface.VerticalGameBackground;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.background.ParallaxBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder;
import org.andengine.opengl.texture.bitmap.AssetBitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.util.GLState;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import java.io.IOException;

/**
 * Created by Adrian on 2/5/15.
 */

public class ResourcesController
{
    // Instancia única de la clase
    private static final ResourcesController INSTANCE =
            new ResourcesController();

    public Engine engine;
    public GameControl gameControl;
    public Camera camara;
    public VertexBufferObjectManager vbom;

    public final VerticalGameBackground GAME_MENUS_BACKGROUND = new VerticalGameBackground(100.0f);
    public Text loadingText;
    // ** TEXTURAS **

    // Escena Splash (imagen estática)
    private ITexture texturaSplash;
    public ITextureRegion regionSplash;

    // Escena Menú
    private ITexture textureUniversalBG;
    public ITextureRegion regionUniversalBG;
    private ITexture textureStars;
    public ITextureRegion regionStars;

    // Botón jugar del menú
    public ITiledTextureRegion regionBtnJugar;
    private BuildableBitmapTextureAtlas btaBtnJugar;
    private ITexture logoT;
    public ITextureRegion logoR;


    // BOTON ACERCA DE
    public ITiledTextureRegion regionBtnAcercaDe;
    private BuildableBitmapTextureAtlas btaBtnAcercaDe;

    //UNIVERSAL
    public ITiledTextureRegion regionPauseButton;
    private BuildableBitmapTextureAtlas btaPauseButton;


    //LS
    public ITiledTextureRegion regionRightButton;
    private BuildableBitmapTextureAtlas btaRightButton;

    public ITiledTextureRegion regionLeftButton;
    private BuildableBitmapTextureAtlas btaLeftButton;

    public ITiledTextureRegion regionJugarNuvel;
    private BuildableBitmapTextureAtlas btaJugarNivel;

    // Escena Acerca de
    private ITexture texturaFondoCreditos;
    public ITextureRegion regionFondoCreditos;

    private BuildableBitmapTextureAtlas btaContButton;
    public ITiledTextureRegion regionContButton;
    private BuildableBitmapTextureAtlas btaMenButton;
    public ITiledTextureRegion regionMenButton;
    private BuildableBitmapTextureAtlas btaReinButton;
    public ITiledTextureRegion regionRainButton;
    public BuildableBitmapTextureAtlas btaBtnCred;
    public ITiledTextureRegion regionCred;


    private ITexture ta1;
    public ITextureRegion ra1;

    private ITexture ta2;
    public ITextureRegion ra2;

    private ITexture ta3;
    public ITextureRegion ra3;

    private ITexture ta4;
    public ITextureRegion ra4;

    public Font getMainFont(float size, int color)
    {
        final ITexture texture = new BitmapTextureAtlas(this.engine.getTextureManager(),1024,1024);

        Font f = FontFactory.createFromAsset(this.engine.getFontManager(),
                texture, this.gameControl.getAssets(), "bebas.ttf", size, true, color);
        f.load();

        return f;
    }

    public Text generateText(String text,float x,float y,float size, int color)
    {
        return new Text(x,y,getMainFont(size,color),text, this.vbom);
    }

    public static ResourcesController getInstance() {
        return INSTANCE;
    }

    public static void inicializarAdministrador(Engine engine,
                                                GameControl control, Camera camara, VertexBufferObjectManager vbom) {

        getInstance().engine = engine;
        getInstance().gameControl =control;
        getInstance().camara = camara;
        getInstance().vbom = vbom;
    }

    public void loadUniversalResources()
    {

        btaPauseButton = new BuildableBitmapTextureAtlas(gameControl.getTextureManager(),
                330,252);
        regionPauseButton = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(btaPauseButton, gameControl.getAssets(),
                "pause.png", 1, 2);
        try {
            btaPauseButton.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0,0,0));

        } catch(ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            Log.d("cargarRecursosMenu","No se puede cargar la imagen del botón jugar");
        }
        btaPauseButton.load();


        btaContButton = new BuildableBitmapTextureAtlas(gameControl.getTextureManager(),
                219,170);
        regionContButton = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(btaContButton, gameControl.getAssets(),
                "cont.png", 1, 2);
        try {
            btaContButton.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0,0,0));

        } catch(ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            Log.d("cargarRecursosMenu","No se puede cargar la imagen del botón continuar");
        }
        btaContButton.load();


        btaMenButton = new BuildableBitmapTextureAtlas(gameControl.getTextureManager(),
                219,170);
        regionMenButton = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(btaMenButton, gameControl.getAssets(),
                "men.png", 1, 2);
        try {
            btaMenButton.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0,0,0));

        } catch(ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            Log.d("cargarRecursosMenu","No se puede cargar la imagen del botón menu");
        }
        btaMenButton.load();


        btaReinButton = new BuildableBitmapTextureAtlas(gameControl.getTextureManager(),
                219,170);
        regionRainButton = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(btaReinButton, gameControl.getAssets(),
                "rein.png", 1, 2);
        try {
            btaReinButton.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0,0,0));

        } catch(ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            Log.d("cargarRecursosMenu","No se puede cargar la imagen del botón reintentar");
        }
        btaReinButton.load();

       //this.loadingText = generateText("Cargando...",GameControl.CAMERA_WIDTH/2,GameControl.CAMERA_HEIGHT/2,90,0xFFFFFFFF);
        try {
            // Carga la imagen de fondo de la pantalla del Menú
            textureUniversalBG = new AssetBitmapTexture(gameControl.getTextureManager(),
                    gameControl.getAssets(), "universal_bg.png");
            regionUniversalBG = TextureRegionFactory.extractFromTexture(textureUniversalBG);
            textureUniversalBG.load();

            // Carga la imagen de fondo de la pantalla del Menú
            textureStars = new AssetBitmapTexture(gameControl.getTextureManager(),
                    gameControl.getAssets(), "stars.png");
            regionStars = TextureRegionFactory.extractFromTexture(textureStars);
            textureStars.load();

        } catch (IOException e) {
            Log.d("cargarRecursosMenu","No se puede cargar los fondos universales");
        }


        btaRightButton = new BuildableBitmapTextureAtlas(gameControl.getTextureManager(),
                84,222);
        regionRightButton = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(btaRightButton, gameControl.getAssets(),
                "r.png", 1, 2);
        try {
            btaRightButton.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0,0,0));

        } catch(ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            Log.d("buttons","No se puede cargar la imagen del botón ");
        }
        btaRightButton.load();


        btaLeftButton = new BuildableBitmapTextureAtlas(gameControl.getTextureManager(),
                84,222);
        regionLeftButton = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(btaLeftButton, gameControl.getAssets(),
                "l.png", 1, 2);
        try {
            btaLeftButton.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0,0,0));

        } catch(ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            Log.d("buttons","No se puede cargar la imagen del botón ");
        }
        btaLeftButton.load();


        final float x = GameControl.CAMERA_WIDTH/2;

        Sprite rS= new Sprite(x,0, regionUniversalBG,this.vbom){
            @Override
            protected void preDraw(GLState pGLState, Camera pCamera) {
                super.preDraw(pGLState, pCamera);
                pGLState.enableDither();
            }
        };
        Sprite eS = new Sprite(x,0, regionStars,this.vbom){
            @Override
            protected void preDraw(GLState pGLState, Camera pCamera) {
                super.preDraw(pGLState, pCamera);
                pGLState.enableDither();
            }
        };

        ParallaxBackground.ParallaxEntity bg = new ParallaxBackground.ParallaxEntity(0.5f,rS);
        ParallaxBackground.ParallaxEntity stars = new ParallaxBackground.ParallaxEntity(1.0f,eS);

        this.GAME_MENUS_BACKGROUND.attachParallaxEntity(bg);
        this.GAME_MENUS_BACKGROUND.attachParallaxEntity(stars);

    }

    //*** Recursos de la pantalla de Splash
    public void cargarRecursosSplash() {
        try {
            // Carga la imagen de fondo de la pantalla Splash
            texturaSplash = new AssetBitmapTexture(gameControl.getTextureManager(),
                    gameControl.getAssets(), "LogoTec.png");
            regionSplash = TextureRegionFactory.extractFromTexture(texturaSplash);
            texturaSplash.load();
        } catch (IOException e) {
            Log.d("cargarRecursosSplash","No se puede cargar el fondo");
        }
    }

    public void liberarRecursosSplash() {
        texturaSplash.unload();
        regionSplash = null;
    }

    //*** Recursos de la pantalla de Menú
    public void cargarRecursosMenu() {


        try {
            // Carga la imagen de fondo de la pantalla Splash
            logoT = new AssetBitmapTexture(gameControl.getTextureManager(),
                    gameControl.getAssets(), "logo.png");
            logoR = TextureRegionFactory.extractFromTexture(logoT);
            logoT.load();
        } catch (IOException e) {
            Log.d("cargarRecursosAcercaDe","No se puede cargar el fondo");
        };


            // Carga la imagen para el botón jugar
            btaBtnJugar = new BuildableBitmapTextureAtlas(gameControl.getTextureManager(),
                    330,252);
            regionBtnJugar = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(btaBtnJugar, gameControl.getAssets(),
                    "btnJugar.png", 1, 2);
            try {
                btaBtnJugar.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0,0,0));

            } catch(ITextureAtlasBuilder.TextureAtlasBuilderException e) {
                Log.d("cargarRecursosMenu","No se puede cargar la imagen del botón jugar");
            }
            btaBtnJugar.load();

            // ACERCA DE

            // Carga la imagen para el botón jugar
            btaBtnAcercaDe = new BuildableBitmapTextureAtlas(gameControl.getTextureManager(),
                    219,170);
            regionBtnAcercaDe = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(btaBtnAcercaDe, gameControl.getAssets(),
                    "btnAcercaDe.png", 1, 2);
            try {
                btaBtnAcercaDe.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0,0,0));

            } catch(ITextureAtlasBuilder.TextureAtlasBuilderException e) {
                Log.d("cargarRecursosMenu","No se puede cargar la imagen del botón acerca de");
            }
            btaBtnAcercaDe.load();


        // Carga la imagen para el botón jugar
        btaBtnCred= new BuildableBitmapTextureAtlas(gameControl.getTextureManager(),
                219,170);
        regionCred = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(btaBtnCred, gameControl.getAssets(),
                "cred.png", 1, 2);
        try {
            btaBtnCred.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0,0,0));

        } catch(ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            Log.d("cargarRecursosMenu","No se puede cargar la imagen del botón acerca de");
        }
        btaBtnCred.load();
    }

    public void liberarRecursosMenu() {
        // botón jugar
        btaBtnCred.unload();
        regionCred = null;
        btaBtnJugar.unload();
        regionBtnJugar = null;
        btaBtnAcercaDe.unload();
        regionBtnAcercaDe = null;
    }

    //*** Recursos de la pantalla de Acerca De
    public void cargarRecursosAcercaDe() {

        try {
            // Carga la imagen de fondo de la pantalla Splash
            ta1 = new AssetBitmapTexture(gameControl.getTextureManager(),
                    gameControl.getAssets(), "a1.png");
            ra1 = TextureRegionFactory.extractFromTexture(ta1);
            ta1.load();
        } catch (IOException e) {
            Log.d("cargarRecursosCREDI","No se puede cargar el fondo");
        }

        try {
            // Carga la imagen de fondo de la pantalla Splash
            ta2 = new AssetBitmapTexture(gameControl.getTextureManager(),
                    gameControl.getAssets(), "a2.png");
            ra2 = TextureRegionFactory.extractFromTexture(ta2);
            ta2.load();
        } catch (IOException e) {
            Log.d("cargarRecursosCREDI","No se puede cargar el fondo");
        }
        try {
            // Carga la imagen de fondo de la pantalla Splash
            ta3 = new AssetBitmapTexture(gameControl.getTextureManager(),
                    gameControl.getAssets(), "a3.png");
            ra3 = TextureRegionFactory.extractFromTexture(ta3);
            ta3.load();
        } catch (IOException e) {
            Log.d("cargarRecursosCREDI","No se puede cargar el fondo");
        }
        try {
            // Carga la imagen de fondo de la pantalla Splash
            ta4 = new AssetBitmapTexture(gameControl.getTextureManager(),
                    gameControl.getAssets(), "a4.png");
            ra4 = TextureRegionFactory.extractFromTexture(ta4);
            ta4.load();
        } catch (IOException e) {
            Log.d("cargarRecursosCREDI","No se puede cargar el fondo");
        }

    }

    public void liberarRecursosAcercaDe() {

        ta1.unload();
        ra1 = null;

        ta2.unload();
        ra2 = null;

        ta3.unload();
        ra3 = null;

        ta4.unload();
        ra4 = null;
    }


    //*** Recursos de la pantalla de Acerca De
    public void cargarRecursosCreditos() {
        try {
            // Carga la imagen de fondo de la pantalla Splash
            texturaFondoCreditos = new AssetBitmapTexture(gameControl.getTextureManager(),
                    gameControl.getAssets(), "creditos.png");
            regionFondoCreditos = TextureRegionFactory.extractFromTexture(texturaFondoCreditos);
            texturaFondoCreditos.load();
        } catch (IOException e) {
            Log.d("cargarRecursosCREDI","No se puede cargar el fondo");
        }
    }

    public void liberarRecursosCreditos() {
        texturaFondoCreditos.unload();
        regionFondoCreditos = null;
    }




    //*** Recursos de la pantalla de SELECCION DE ESCENAS
    public void cargarRecursosLS() {


        btaJugarNivel = new BuildableBitmapTextureAtlas(gameControl.getTextureManager(),
                219,170);
        regionJugarNuvel = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(btaJugarNivel, gameControl.getAssets(),
                "jugarNivel.png", 1, 2);
        try {
            btaJugarNivel.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0,0,0));

        } catch(ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            Log.d("buttons","No se puede cargar la imagen del botón ");
        }
        btaJugarNivel.load();

    }

    public void liberarRecursosLS() {

        btaJugarNivel.unload();
        regionJugarNuvel = null;

    }

}


package jks.tools2d.parallax.editor.vue.edition;

import static jks.tools2d.parallax.editor.vue.edition.data.GVars_Vue_Edition.allImage;
import static jks.tools2d.parallax.editor.vue.edition.data.GVars_Vue_Edition.getAtlas;
import static jks.tools2d.parallax.editor.vue.edition.data.GVars_Vue_Edition.imageRef;
import static jks.tools2d.parallax.editor.vue.edition.data.GVars_Vue_Edition.isPause;
import static jks.tools2d.parallax.editor.vue.edition.data.GVars_Vue_Edition.parr_Pos_X;
import static jks.tools2d.parallax.editor.vue.edition.data.GVars_Vue_Edition.parr_Pos_Y;
import static jks.tools2d.parallax.editor.vue.edition.data.GVars_Vue_Edition.parr_Size_X;
import static jks.tools2d.parallax.editor.vue.edition.data.GVars_Vue_Edition.parr_Size_Y;
import static jks.tools2d.parallax.editor.vue.edition.data.GVars_Vue_Edition.screenSize;
import static jks.tools2d.parallax.editor.vue.edition.data.GVars_Vue_Edition.setDefaults;
import static jks.tools2d.parallax.editor.vue.edition.data.GVars_Vue_Edition.textureChange;
import static jks.tools2d.parallax.editor.vue.edition.data.GVars_Vue_Edition.*;
import static jks.tools2d.parallax.editor.gvars.FVars_Extensions.atlasMaxSize ; 

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.kotcrab.vis.ui.util.dialog.Dialogs;

import jks.tools2d.filewatch.FileWatching_Image;
import jks.tools2d.libgdxutils.Utils_Scene2D;
import jks.tools2d.parallax.ParallaxLayer;
import jks.tools2d.parallax.editor.gvars.GVars_Ui;
import jks.tools2d.parallax.editor.inputs.EditorInputProcessus;
import jks.tools2d.parallax.editor.inputs.GVars_Inputs;
import jks.tools2d.parallax.editor.vue.edition.data.GVars_Vue_Edition;
import jks.tools2d.parallax.editor.vue.edition.data.Outside_Source;
import jks.tools2d.parallax.editor.vue.edition.data.ParallaxDefaultValues;
import jks.tools2d.parallax.editor.vue.edition.data.Position_Infos;
import jks.tools2d.parallax.editor.vue.edition.data.Utils_LoadingImages;
import jks.tools2d.parallax.editor.vue.edition.data.Utils_Saving;
import jks.tools2d.parallax.editor.vue.edition.data.Utils_Texture;
import jks.tools2d.parallax.editor.vue.model.AVue_Model;
import jks.tools2d.parallax.heart.Parallax_Heart; 

public class Vue_Edition extends AVue_Model 
{

	public static Parallax_Heart parallax_Heart ;
	public ShapeRenderer shapeRender ;
	public Object preloadingValue ; 

	public void preload()
	{
		parallax_Heart = new Parallax_Heart(screenSize,new AssetManager());
		parallax_Heart.relativePath = GVars_Vue_Edition.relativePath ;
		
		GVars_Vue_Edition.buildSizes();
		shapeRender = new ShapeRenderer() ;
	}
	
	public Vue_Edition(Object preloadingValue)
	{
		preload() ; 
		this.preloadingValue = preloadingValue ;  	
	}

	public Vue_Edition() 
	{
		preload() ; 
	}

	@Override
	public void init()
	{	
		allImage = new ArrayList<>() ; 
		
		centerControl = VE_Center_ParallaxShow.build(preloadingValue) ; 
		
		setDefaults(new ParallaxDefaultValues()); 
		buildImageList() ; 
			
		tabControl = new VE_Tab_AControl() ; 
		optionsControl = new VE_Options() ; 
		
		GVars_Ui.mainUi.addActor(optionsControl) ;
		GVars_Ui.mainUi.addActor(tabControl); 
		GVars_Ui.mainUi.addActor(centerControl); 
		
		InputProcessor input = buildClickProcessor() ; 	
		Gdx.input.setInputProcessor(new InputMultiplexer(GVars_Ui.mainUi, new EditorInputProcessus(),input));
	}
	
	private void buildImageList() 
	{
		if(getAtlas() == null)
			return ; 
		
		for(AtlasRegion region : getAtlas().getRegions())
		{
			buildInsideData(region) ; 
		}
	}
	
	
	
	public void buildInsideData(AtlasRegion region)
	{
		int position ; 
		allImage.add(region) ;
		position = region.index ; 
		
		if(position == -1)
			position = 0 ; 
		else if(position > 0)
			position-- ;
		
		imageRef.put(region, new Position_Infos(region,position)) ; 
	}

	public void saveAllData()
	{	
		FileHandle handler ; 
		for(TextureRegion region : allImage)
		{
			handler = new FileHandle("../editor/Files/Parallax/test.png") ; 
			region.getTexture().getTextureData().prepare();
			PixmapIO.writePNG(handler, region.getTexture().getTextureData().consumePixmap()) ; 
		}
	}

	@Override
	public void destroy()
	{
		GVars_Ui.reset();
	}

	@Override
	public void restart()
	{

	}

	
	InputProcessor buildClickProcessor()
	{
		return new InputProcessor()
		{		
			@Override
			public boolean touchUp(int screenX, int screenY, int pointer, int button)
			{
				return false;
			}
			
			@Override
			public boolean touchDragged(int screenX, int screenY, int pointer)
			{
				return false;
			}
			
			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button)
			{
				if(button == 1)
				{
					GVars_Vue_Edition.colorPicked = null ;
				}
				
				if(GVars_Vue_Edition.colorPicked != null)
				{
					Pixmap map = getFrameBufferPixmap(screenX, screenY, 1, 1) ; 
					GVars_Vue_Edition.colorPicked.setColor(new Color(map.getPixel(0, 0)));
				}
				
				return false;
			}
			
			@Override
			public boolean scrolled(int amount)
			{
				return false;
			}
			
			@Override
			public boolean mouseMoved(int screenX, int screenY)
			{
//				if(GVars_Vue_Edition.colorPicked != null)
//				{
//					Pixmap map = ScreenUtils.getFrameBufferPixmap(screenX, screenY, 1, 1) ; 
//					System.out.println(map.getPixels());
//					
//					GVars_Vue_Edition.colorPicked.setColor(new Color(map.getPixel(0, 0)));
//				}
				return true;
			}
			
			@Override
			public boolean keyUp(int keycode)
			{
				
				return false;
			}
			
			@Override
			public boolean keyTyped(char character)
			{
				return false;
			}
			
			@Override
			public boolean keyDown(int keycode)
			{
				// TODO Auto-generated method stub
				return false;
			}
		};
	}
	
	public static Pixmap getFrameBufferPixmap (int x, int y, int w, int h) 
	{
		Gdx.gl.glPixelStorei(GL30.GL_PACK_ALIGNMENT, 2);
		final Pixmap pixmap = new Pixmap(w, h, Format.RGBA8888);
		ByteBuffer pixels = pixmap.getPixels();
		Gdx.gl.glReadPixels(x, Gdx.graphics.getHeight() - y, w, h, GL30.GL_RGBA, GL30.GL_UNSIGNED_BYTE, pixels);

		return pixmap;
	}
	
	private final float circlePosition = 10 ;
	private final float circleSize = 12 ;
	private float hideInterfaceQuota = 4 ; 
	private boolean changeTexture ;
	private FileWatching_Image currentFile ; 
	
	@Override
	public void update(float delta)
	{
		GVars_Ui.mainUi.act(delta);
		
		GVars_Inputs.updateInput(delta);
		
		if(!isPause)
		{
			// TODO OPTI
//			parallax_Heart.worldCamera.position.add(screenSpeed, 0, 0); 
//			parallax_Heart.worldCamera.update();
//			parallax_Heart.batch.setProjectionMatrix(parallax_Heart.worldCamera.combined);
			parallax_Heart.act(delta);
		}
		
		while(!textureChange.isEmpty())
		{		
			currentFile = textureChange.get(0) ; 
			Position_Infos pos = imageRef.get(currentFile.concernTexture) ; 
			TextureRegion region = Utils_Texture.getTextureRegionFromPath(pos.url) ; 
			ArrayList<ParallaxLayer> layers = GVars_Vue_Edition.textureLink.get(currentFile.concernTexture) ; 			
			
			imageRef.remove(currentFile.concernTexture) ; 
			imageRef.put(region, pos) ; 
			
			if(layers != null)
			{
				for(ParallaxLayer layer : layers)
				{
					layer.setTexRegion(region);
				}
			}
		
			textureLink.remove(currentFile) ; 
			textureLink.put(region, layers) ; 
			
			Collections.replaceAll(allImage, currentFile.concernTexture, region) ; 
			
			currentFile.concernTexture = region ; 
			textureChange.remove(0) ; 
			
			changeTexture = true ; 
		}
	
		if(changeTexture)
		{
			GVars_Vue_Edition.setItems(); changeTexture = false ; 
		}
		
		if(showParallaxFullScreen)
		{
			GVars_Vue_Edition.hideInterfaceTimmer += delta ; 
		}
		
		timeForAutoSaveTimmer += delta ; 
		if(timeForAutoSaveTimmer > timeForAutoSaveAt)
		{
			Utils_Saving.autoSave() ; 
			timeForAutoSaveTimmer = 0 ;
		}
	}

	public void resize(int width, int height) 
	{
		centerControl.resize();
	}
	
	@Override
	public void render()
	{
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling?GL20.GL_COVERAGE_BUFFER_BIT_NV:0)) ; 
		
		if(showParallaxFullScreen)
		{
			parallax_Heart.render() ; 
			if(GVars_Vue_Edition.hideInterfaceTimmer < hideInterfaceQuota)
			{GVars_Ui.mainUi.draw() ;}
			
		}
		else
		{
			Gdx.gl.glViewport(parr_Pos_X,parr_Pos_Y, parr_Size_X, parr_Size_Y);
			parallax_Heart.render() ; 
			
			
			Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			
			if(GVars_Vue_Edition.colorPicked != null)
			{
				Pixmap map = getFrameBufferPixmap(Gdx.input.getX(), Gdx.input.getY(), 1, 1) ; 
				Color color = new Color(map.getPixel(0, 0)) ;
				shapeRender.begin(ShapeType.Filled);
				shapeRender.setColor(color);
				shapeRender.circle(Gdx.input.getX() + circlePosition, Gdx.graphics.getHeight() - Gdx.input.getY() + circlePosition, circleSize);
				shapeRender.end();
				shapeRender.begin(ShapeType.Line);
				shapeRender.setColor(new Color(Math.abs(1 - color.r),Math.abs(1 - color.g),Math.abs(1 - color.b),1));
				shapeRender.circle(Gdx.input.getX() + circlePosition, Gdx.graphics.getHeight() - Gdx.input.getY() + circlePosition, circleSize);
				shapeRender.end();
			}
			
			GVars_Ui.mainUi.draw() ;
		
		}	
	}

	
	@Override
	public void reciveFiles(String[] files)
	{
		Utils_LoadingImages.fileReception(files);
	}
	
}
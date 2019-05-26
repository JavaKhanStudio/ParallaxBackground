package jks.tools2d.parallax.editor.vue.edition;

import static jks.tools2d.parallax.editor.gvars.GVars_Ui.baseSkin;
import static jks.tools2d.parallax.editor.vue.edition.data.GVars_Vue_Edition.*;
import static jks.tools2d.parallax.editor.vue.edition.data.GVars_Vue_Edition.parr_Pos_X;
import static jks.tools2d.parallax.editor.vue.edition.data.GVars_Vue_Edition.parr_Pos_Y;
import static jks.tools2d.parallax.editor.vue.edition.data.GVars_Vue_Edition.parr_Size_X;
import static jks.tools2d.parallax.editor.vue.edition.data.GVars_Vue_Edition.parr_Size_Y;
import static jks.tools2d.parallax.editor.vue.edition.data.GVars_Vue_Edition.showParallaxFullScreen;
import static jks.tools2d.parallax.editor.vue.edition.data.GVars_Vue_Edition.size_Bloc_Parallax;
import static jks.tools2d.parallax.editor.vue.edition.data.GVars_Vue_Edition.size_Bloc_Selection;
import static jks.tools2d.parallax.editor.vue.edition.data.GVars_Vue_Edition.size_Height_Bloc_Parallax_Controle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import jks.tools2d.libgdxutils.JksCheckBox;
import jks.tools2d.libgdxutils.Utils_Interface;
import jks.tools2d.parallax.editor.gvars.FVars_Extensions;
import jks.tools2d.parallax.editor.gvars.GVars_Ui;
import jks.tools2d.parallax.editor.vue.edition.data.GVars_Vue_Edition;
import jks.tools2d.parallax.editor.vue.edition.data.Project_Data;
import jks.tools2d.parallax.pages.WholePage_Model; 

public class VE_Center_ParallaxShow extends Table
{
	float decalX = 2 ; 
	float buttonSize ;
	
	public static VE_Center_ParallaxShow build(Object ref)
	{
		VE_Center_ParallaxShow show = null ;
		TextureAtlas atlas = null;
		
		if(ref instanceof TextureAtlas)
		{
			atlas = (TextureAtlas)ref ; 
			show = new VE_Center_ParallaxShow(atlas) ;
		}
		else if(ref instanceof WholePage_Model)
		{
			WholePage_Model page = (WholePage_Model)ref ; 
			atlas = new TextureAtlas(new FileHandle(GVars_Vue_Edition.relativePath + "/" + page.pageModel.atlasName));
			show =  new VE_Center_ParallaxShow(page) ; 
		}
		else if(ref instanceof Project_Data)
		{
			Project_Data project = (Project_Data)ref ; 
			atlas = new TextureAtlas(new FileHandle(GVars_Vue_Edition.relativePath + "/" + project.saving.pageModel.atlasName));
			show =  new VE_Center_ParallaxShow(project.saving) ;
			GVars_Vue_Edition.datas = project ;
		}
		
		GVars_Vue_Edition.atlas = atlas ; 
		
		return show ; 
	}
	
	
	VE_Center_ParallaxShow(TextureAtlas atlas)
	{
		resize() ; 
		
		WholePage_Model page = new WholePage_Model(); 
		page.pageModel.atlasName = GVars_Vue_Edition.infos.projectName + "." + FVars_Extensions.ATLAS ; 
		page.forceLoad(atlas);
		GVars_Vue_Edition.setPage(page) ; 
			
		buildOptions() ; 
	}
	
	VE_Center_ParallaxShow(WholePage_Model page)
	{
		resize() ; 
		GVars_Vue_Edition.setPage(page) ; 
		buildOptions() ; 
	}
	
	public void resize()
	{
		parr_Size_X = (int)(((Gdx.graphics.getWidth()/3) * 2) - decalX * 2) ; 
		parr_Size_Y = (parr_Size_X/16) * 9 ; 
		parr_Pos_X = size_Bloc_Selection + (Gdx.graphics.getWidth() - size_Bloc_Selection - parr_Size_X)/2; 
		parr_Pos_Y = size_Height_Bloc_Parallax_Controle ;
		
		buttonSize = size_Height_Bloc_Parallax_Controle/1.5f ; 
	}
	
	
	public void buildOptions()
	{
		setBounds(size_Bloc_Selection, 1, size_Bloc_Parallax, Gdx.graphics.getHeight() - parr_Pos_Y/2 - parr_Size_Y);
//		this.setDebug(true);
		// TODO Effacer et mettre dans le UISKIN
		CheckBoxStyle checkBoxStyle = new CheckBoxStyle() ;
		checkBoxStyle.checkboxOff = baseSkin.newDrawable(Utils_Interface.buildDrawingRegionTexture("editor/interfaces/button_play.png"));
		checkBoxStyle.checkboxOn = baseSkin.newDrawable(Utils_Interface.buildDrawingRegionTexture("editor/interfaces/button_pause.png"));
		checkBoxStyle.font = baseSkin.getFont("default-font");
		
		CheckBoxStyle extendScreenStyle = new CheckBoxStyle() ;
		extendScreenStyle.checkboxOff = baseSkin.newDrawable(Utils_Interface.buildDrawingRegionTexture("editor/interfaces/expand.png"));
		extendScreenStyle.checkboxOn = baseSkin.newDrawable(Utils_Interface.buildDrawingRegionTexture("editor/interfaces/contract.png"));
		extendScreenStyle.font = baseSkin.getFont("default-font");
		
		JksCheckBox startStop = new JksCheckBox("",checkBoxStyle,false) ; 
		startStop.addListener(new ChangeListener() 
		{
			public void changed (ChangeEvent event, Actor actor) 
			{isPause = !startStop.isChecked();}
		});

		startStop.setSize(buttonSize, buttonSize);
		startStop.setPosition(parr_Size_X/2, size_Height_Bloc_Parallax_Controle/2 - buttonSize/2);
		
		
		JksCheckBox extendScreen = new JksCheckBox("",extendScreenStyle,false) ; 
		extendScreen.addListener(new ChangeListener() 
		{
			public void changed (ChangeEvent event, Actor actor) 
			{
				showParallaxFullScreen = extendScreen.isChecked();
				
				if(showParallaxFullScreen)
				{
					tabControl.setVisible(false);
					optionsControl.setVisible(false);
				}
				else
				{
					tabControl.setVisible(true);
					optionsControl.setVisible(true);
				}
			}
		});


		extendScreen.setSize(buttonSize, buttonSize);
		extendScreen.setPosition(
				this.getWidth() - extendScreen.getWidth() ,
				this.getHeight() - extendScreen.getHeight()/2);
		extendScreen.setChecked(false);
		
		Slider parallaxSpeedSlider = new Slider(-10, 10, 0.2f, false, GVars_Ui.baseSkin) ; 
		parallaxSpeedSlider.setValue(0) ; 
		parallaxSpeedSlider.addListener(new InputListener()
		{		
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) 
			{
//				GVars_Vue_Edition.screenSpeed = parallaxSpeedSlider.getValue() ; 
				return true ;
			}
			
			@Override
			public void touchDragged(InputEvent event, float x, float y, int pointer)
			{
				Vue_Edition.parallax_Heart.screenSpeedConstant = parallaxSpeedSlider.getValue() * 100 ; 
			}
		}) ; 
		
		parallaxSpeedSlider.setSize(this.getWidth()/2 - buttonSize, buttonSize);
		parallaxSpeedSlider.setPosition(buttonSize/3, this.getHeight()/2 - parallaxSpeedSlider.getHeight()/2);
		
		
		this.addActor(startStop) ;
		this.addActor(parallaxSpeedSlider) ;
		this.addActor(extendScreen) ; 
	}
}
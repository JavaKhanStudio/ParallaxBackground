package jks.tools2d.parallax.editor.vue.edition;

import static jks.tools2d.parallax.editor.vue.edition.VE_Options.parallaxName;
import static jks.tools2d.parallax.editor.vue.edition.VE_Options.parallaxPath;
import static jks.tools2d.parallax.editor.vue.edition.Vue_Edition.parallax_Heart;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.kotcrab.vis.ui.util.dialog.Dialogs;
import com.kotcrab.vis.ui.util.dialog.Dialogs.OptionDialogType;
import com.kotcrab.vis.ui.util.dialog.OptionDialogAdapter;
import com.kotcrab.vis.ui.widget.VisCheckBox;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.kotcrab.vis.ui.widget.tabbedpane.Tab;

import jks.tools2d.parallax.editor.gvars.GVars_Heart_Editor;
import jks.tools2d.parallax.editor.gvars.GVars_Ui;
import jks.tools2d.parallax.editor.vue.Vue_Selection;
import jks.tools2d.parallax.editor.vue.edition.data.Utils_Saving; 

public class VE_Tab_ParallaxConfig_Configuration extends Tab
{

	private Table mainTable ; 
	VisCheckBox repeatOnX, repeatOnY ; 
	VisTextButton showOptionDialog ; 
	
	VE_Tab_ParallaxConfig_Configuration()
	{
		super(false, false);
		mainTable = new Table() ; 
		buildTextureSelector() ; 
	}
	
	public void buildTextureSelector()
	{
		mainTable = new Table() ; 
		
		repeatOnX = new VisCheckBox("Repeat On X") ;
		repeatOnX.addListener(new InputListener()
		{		
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) 
			{return true ;}
			
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button)
			{
				parallax_Heart.parallaxReader.setRepeatOnX(repeatOnX.isChecked());
				parallax_Heart.parallaxReader.resetPositions();
			}
		}) ; 
		
		repeatOnY = new VisCheckBox("Repeat On Y") ;
		repeatOnY.addListener(new InputListener()
		{		
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) 
			{return true ;}
			
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button)
			{
				parallax_Heart.parallaxReader.setRepeatOnY(repeatOnY.isChecked());
				parallax_Heart.parallaxReader.resetPositions();
			}
		}) ; 
		
		showOptionDialog = new VisTextButton("Return to selection");
		showOptionDialog.addListener(new ChangeListener() 
		{
			@Override
			public void changed (ChangeEvent event, Actor actor) 
			{
				Dialogs.showOptionDialog(GVars_Ui.mainUi, "option dialog", "Do you want to save the project before leaving?", OptionDialogType.YES_NO_CANCEL, new OptionDialogAdapter() {
					@Override
					public void yes () 
					{
						Utils_Saving.saving_Parallax_Project(parallaxPath.getText(), parallaxName.getText());
						GVars_Heart_Editor.changeVue(new Vue_Selection(),true) ; 
					}

					@Override
					public void no () 
					{
						GVars_Heart_Editor.changeVue(new Vue_Selection(),true) ; 
					}

					@Override
					public void cancel () 
					{}
				});
			}
		});
		
		mainTable.add(repeatOnX) ; 
		mainTable.add(repeatOnY) ; 
		mainTable.row();
		mainTable.add(showOptionDialog).colspan(2) ; 
	}
	
	public void update()
	{
		repeatOnX.setChecked(parallax_Heart.parallaxReader.isRepeatOnX());
		repeatOnY.setChecked(parallax_Heart.parallaxReader.isRepeatOnY());
	}

	@Override
	public String getTabTitle()
	{return "Configuration";}

	@Override
	public Table getContentTable()
	{
		update();
		return mainTable;
	}	
	
}
package jks.tools2d.parallax.editor.vue.edition;

import static jks.tools2d.parallax.editor.gvars.GVars_Ui.baseSkin;
import static jks.tools2d.parallax.editor.vue.edition.GVars_Vue_Edition.currentlySelectedParallax;
import static jks.tools2d.parallax.editor.vue.edition.Vue_Edition.parallax_Heart;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.kotcrab.vis.ui.widget.VisCheckBox;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.spinner.IntSpinnerModel;
import com.kotcrab.vis.ui.widget.spinner.Spinner;

public class Vue_Edition_SideBar_TextureConfig_Edit extends Table
{
	Slider 
	padX_Slider, padY_Slider,
	sizeRatio_Slider,
	staticSpeed_Slider,
	speedX_ration_Slider,speedY_ration_Slider; 
	TextField 
	padX_tf,padY_tf,
	sizeRatio_tf,
	staticSpeed_tf, 
	speedX_ration_tf,speedY_ration_tf; 
	
	IntSpinnerModel indexPositionSpinner ; 
	Spinner indexPositionSpinerBody ; 
	
	VisCheckBox flipX ; 
	
	public Vue_Edition_SideBar_TextureConfig_Edit()
	{
		indexPositionSpinner = new IntSpinnerModel(0,0,0); 
		indexPositionSpinerBody = new Spinner("Layer Position", indexPositionSpinner);
		indexPositionSpinerBody.addListener(new InputListener()
		{		
			int befaureValue = 0;
			
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) 
			{
				befaureValue = indexPositionSpinner.getValue();
				return true ;
			}
			
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button)
			{
				parallax_Heart.parallaxPage.layers.swap(befaureValue, indexPositionSpinner.getValue());	
			}
		}) ; 
		
		flipX = new VisCheckBox("Flip X") ;
		flipX.addListener(new InputListener()
		{		
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) 
			{return true ;}
			
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button)
			{
				currentlySelectedParallax.setFlipX(flipX.isChecked());
			}
		}) ; 
		
		padX_Slider = new Slider(-50, 50, 1, false,baseSkin); 
		padX_tf = new TextField("",baseSkin) ; 
		padX_Slider.addListener(new InputListener()
		{		
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) 
			{
				touchDragged(event, x,y,pointer) ;
				return true ;
			}
			
			@Override
			public void touchDragged(InputEvent event, float x, float y, int pointer)
			{
				currentlySelectedParallax.setPadPositionX(padX_Slider.getValue());
				padX_tf.setText(padX_Slider.getValue() + "");
			}
		}) ; 
			
		padY_Slider = new Slider(-150, 150, 1, false,baseSkin); 	
		padY_tf = new TextField("",baseSkin) ; 
		padY_Slider.addListener(new InputListener()
		{		
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) 
			{
				touchDragged(event, x,y,pointer) ;
				return true ;
			}
			
			@Override
			public void touchDragged(InputEvent event, float x, float y, int pointer)
			{
				currentlySelectedParallax.setPadPositionY(padY_Slider.getValue());
				padY_tf.setText(padY_Slider.getValue() + "");
			}
		}) ; 
			
		sizeRatio_Slider = new Slider(0.01f, 3, 0.01f, false,baseSkin); 
		sizeRatio_tf = new TextField("",baseSkin) ; 
		sizeRatio_Slider.addListener(new InputListener()
		{		
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) 
			{
				touchDragged(event, x,y,pointer) ;
				return true ;
			}
			
			@Override
			public void touchDragged(InputEvent event, float x, float y, int pointer)
			{
				currentlySelectedParallax.setSizeRatio(sizeRatio_Slider.getValue());
				sizeRatio_tf.setText(sizeRatio_Slider.getValue() + "");
			}
		}) ; 
		
		staticSpeed_Slider = new Slider(-10, 10, 0.5f, false,baseSkin); 
		staticSpeed_tf = new TextField("",baseSkin) ; 
		staticSpeed_Slider.addListener(new InputListener()
		{		
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) 
			{
				touchDragged(event, x,y,pointer) ;
				return true ;
			}
			
			@Override
			public void touchDragged(InputEvent event, float x, float y, int pointer)
			{
				currentlySelectedParallax.setSpeed(staticSpeed_Slider.getValue());
				staticSpeed_tf.setText(staticSpeed_Slider.getValue() + "");
			}
		}) ; 
		
		speedX_ration_Slider = new Slider(0.005f, 0.2f, 0.001f, false,baseSkin); 
		speedX_ration_tf = new TextField("",baseSkin) ; 
		speedX_ration_Slider.addListener(new InputListener()
		{		
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) 
			{
				touchDragged(event, x, y, pointer) ;
				return true ;
			}
			
			@Override
			public void touchDragged(InputEvent event, float x, float y, int pointer)
			{
				currentlySelectedParallax.getParallaxRatio().x = (speedX_ration_Slider.getValue());
				speedX_ration_tf.setText(speedX_ration_Slider.getValue() + "");
			}
		}) ; 
		
		
		this.add(new VisLabel("SECTION EDITION")).row();
		this.add(indexPositionSpinerBody).row() ; 
		this.add(flipX).row() ; 
		
		this.add(new VisLabel("Pad X")).row();
		this.add(padX_Slider).pad(10).expandX().fillX() ; 
		this.add(padX_tf).width(40).row(); ; 
		
		this.add(new VisLabel("Pad Y")).row();
		this.add(padY_Slider).pad(10).expandX().fillX() ; 
		this.add(padY_tf).width(50).row(); ; 
		
		this.add(new VisLabel("Size Ratio")).row();
		this.add(sizeRatio_Slider).pad(10).expandX().fillX() ; 
		this.add(sizeRatio_tf).width(50).row();
		
		this.add(new VisLabel("At rest Speed")).row();
		this.add(staticSpeed_Slider).pad(10).expandX().fillX() ; 
		this.add(staticSpeed_tf).width(40).row();
		
		this.add(new VisLabel("-- Speed ratio --")).row();
		this.add(new VisLabel("X")).row();;
		this.add(speedX_ration_Slider).pad(9).expandX().fillX() ; 
		this.add(speedX_ration_tf).width(40).row();
	}
	
	
	public void update()
	{
		
		flipX.setChecked(currentlySelectedParallax.isFlipX());
		
		//	//	//	//	//	//
		//	INDEX POSITION	//
		//	//	//	//	//	//
		indexPositionSpinner.setMax(parallax_Heart.parallaxPage.layers.size - 1);
		indexPositionSpinner.setMin(0);
		indexPositionSpinner.setValue(parallax_Heart.parallaxPage.layers.indexOf(currentlySelectedParallax, true)) ;
			
		// // // //
		// PAD X //
		// // // //	
		padX_Slider.setValue(currentlySelectedParallax.getPadPositionX()) ; 
		padX_tf.setText(padX_Slider.getValue() + "");
		
		// // // //
		// PAD Y //
		// // // //	
		padY_Slider.setValue(currentlySelectedParallax.getPadPositionY()) ; 
		padY_tf.setText(padY_Slider.getValue() + "");
		
		//	//	//	//	//
		//	SIZE RATIO	//
		//	//	//	//	//	
		sizeRatio_Slider.setValue(currentlySelectedParallax.getSizeRatio()) ; 
		sizeRatio_tf.setText(sizeRatio_Slider.getValue() + "");
		
		//	//	//	//	//
		//	SIZE RATIO	//
		//	//	//	//	//		
		staticSpeed_Slider.setValue(currentlySelectedParallax.getSpeed()) ; 
		staticSpeed_tf.setText(staticSpeed_Slider.getValue() + "");
		
		//	//	//	//	
		//	SPEED X	//
		//	//	//	//	
		speedX_ration_Slider.setValue(currentlySelectedParallax.getParallaxRatio().x) ; 
		speedX_ration_tf.setText(speedX_ration_Slider.getValue() + "");
		
	}
	
}

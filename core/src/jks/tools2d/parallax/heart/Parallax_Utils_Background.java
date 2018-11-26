package jks.tools2d.parallax.heart;

import static jks.tools2d.parallax.heart.Parallax_Heart.shapeRender;
import static jks.tools2d.parallax.heart.Parallax_Heart.square;
import static jks.tools2d.parallax.heart.Parallax_Heart.squarePercentage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType; 

public class Parallax_Utils_Background 
{

	static void drawBackgroundColor(float delta)
	{
		if(Parallax_Heart.square != null)
		{
			square.act(delta);
			square.draw(shapeRender);
		}
	}

	static void setBackground(ParallaxPageModel model)
	{
		square = model.buildSquareBackground(squarePercentage) ;
	}

	public static void drawBottomHalf() 
	{
		if(Parallax_Heart.currentPage.bottomHalf != null)
		{
			shapeRender.setColor(Parallax_Heart.currentPage.bottomHalf);
			shapeRender.rect(0, 0, Gdx.graphics.getWidth(),Parallax_Heart.bottomSquareSize );
		}
	}

	/*
	static void computeNextBackground_Color(ParallaxPage background)
	{
		if(Parrallax_Heart.nextColor.size() > 0)
		{
			Parrallax_Heart.parallaxBackground.set_newLayer_Color(Parrallax_Heart.nextColor.get(0));
			Parrallax_Heart.nextColor.remove(0) ; 
		}
	}
	*/
}
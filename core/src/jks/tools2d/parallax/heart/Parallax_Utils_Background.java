package jks.tools2d.parallax.heart;

import static jks.tools2d.parallax.heart.Parallax_Heart.bottomSquare;
import static jks.tools2d.parallax.heart.Parallax_Heart.shapeRender;
import static jks.tools2d.parallax.heart.Parallax_Heart.squarePercentage;
import static jks.tools2d.parallax.heart.Parallax_Heart.topSquare;

import jks.tools2d.parallax.pages.Page_Whole_Model;

public class Parallax_Utils_Background 
{

	static void drawBackground_TopColor()
	{
		if(topSquare != null)
			topSquare.draw(shapeRender);
	}
	
	public static void drawBackground_BottomColor() 
	{
		if(bottomSquare != null)
			bottomSquare.draw(shapeRender);
	}

	static void setBackground(Page_Whole_Model model)
	{
		topSquare = model.buildTopSquareBackground(squarePercentage) ;
		bottomSquare = model.buildBottomSquareBackground(squarePercentage) ;
	}

}

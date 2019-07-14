package jks.tools2d.parallax;

import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class Utils_Parralax 
{

	public static float calculateOtherDimension(boolean isWidth,float oneDimen,TextureRegion region)
	{
		float result = 0 ;
		
		if(isWidth)
		    result = region.getRegionHeight()*(oneDimen/region.getRegionWidth());
		else
    		result = region.getRegionWidth()*(oneDimen/region.getRegionHeight());
		
		return result;
	}
	
	public static float calculateOtherDimension(boolean isWidth,float oneDimen,float originalWidth, float originalHeight)
	{
		
		float result=0;
		if(isWidth)
    		    result = originalHeight*(oneDimen/originalWidth);
		else
	    		result = originalWidth*(oneDimen/originalHeight);
		
		return result;
	}
}
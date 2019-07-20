package jks.tools2d.parallax.inputs ;

import static jks.tools2d.parallax.gvars.GVars_Heart_Testing.parallax_Heart ;
import static jks.tools2d.parallax.gvars.GVars_Heart_Testing.screenMovementSpeed; 

public class GVars_Inputs 
{
	public static boolean touched, 
		upPressed, downPressed,
		leftPressed, rightPressed,
		zoomInPressed, zoomOutPressed 
		;

	public static void updateInput (float delta) 
	{
		if (leftPressed)
			parallax_Heart.worldCamera.position.add(-screenMovementSpeed, 0, 0); 
		
		if (rightPressed)
			parallax_Heart.worldCamera.position.add(screenMovementSpeed, 0, 0);
			
		if(upPressed)
			parallax_Heart.worldCamera.position.add(0,screenMovementSpeed, 0);
		
		if(downPressed)
			parallax_Heart.worldCamera.position.add(0,-screenMovementSpeed, 0);
		
		if(zoomInPressed)
			parallax_Heart.worldCamera.zoom *= 1.1f;
		
		if(zoomOutPressed)
			parallax_Heart.worldCamera.zoom /= 1.1f;

		parallax_Heart.worldCamera.update();
		parallax_Heart.batch.setProjectionMatrix(parallax_Heart.worldCamera.combined);
	}
}
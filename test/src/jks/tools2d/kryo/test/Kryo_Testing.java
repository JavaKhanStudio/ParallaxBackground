package jks.tools2d.kryo.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import jks.tools2d.parallax.pages.Color_Serializer;
import jks.tools2d.parallax.pages.Page_Model;
import jks.tools2d.parallax.pages.Page_Whole_Model;
import jks.tools2d.parallax.pages.Parallax_Model;

public class Kryo_Testing 
{
	private final static String path = "C:/Users/Simon/Documents/TestGDX/"; 
	private final static String colorName = "color" ; 
	private final static String parallaxName = "parallax" ; 
	private final static String pageName = "page" ; 
	private final static String pageWholeName = "whole" ; 
	private final static String format = ".bin" ;
	
    static public void main (String[] args) throws Exception
    {
	      Kryo kryo = new Kryo();
	      kryo.register(Color.class, new Color_Serializer());
	      kryo.register(Parallax_Model.class) ; 
	      kryo.register(Page_Model.class) ;
	      kryo.register(ArrayList.class) ; 
	      kryo.register(Page_Whole_Model.class) ; 
	      
	      System.out.println("hellow kryo");
	      testColor(kryo);
	      testParallaxModel(kryo) ; 
	      testPage(kryo) ; 
	      testWholePage(kryo);
	      System.out.println("Test Finish");
	 }
    
    static public void testColor(Kryo kryo) throws FileNotFoundException
    {
    	Color color = new Color();
    	color.a = 0.5f ; 
    	color.b = 0.6f ;
    	color.g = 0.7f ;
    	color.r = 0.8f ;
    	Output output = new Output(new FileOutputStream(path + colorName + format));
    	kryo.writeObject(output, color);
    	output.close();

    	Input input = new Input(new FileInputStream(path + colorName + format));
    	Color colorLoaded = kryo.readObject(input, Color.class);
    	input.close();   
    	System.out.println("testColor work " + colorLoaded.a + " " + colorLoaded.b + " " + colorLoaded.g + " " + colorLoaded.r + " " );     
    }
    
    static public void testParallaxModel(Kryo kryo) throws FileNotFoundException
    {
    	Parallax_Model parallaxPage = buildPageModel() ; 
    	
    	
    	Output output = new Output(new FileOutputStream(path + parallaxName + format));
    	kryo.writeObject(output, parallaxPage);
    	output.close();

    	Input input = new Input(new FileInputStream(path + parallaxName + format));
    	Parallax_Model model = kryo.readObject(input, Parallax_Model.class);
    	input.close();   
    	System.out.println("testParallaxModel work " + parallaxPage.region_Name + " " + 
			parallaxPage.region_Position + " " + 
			parallaxPage.ratioX + " " + 
			parallaxPage.ratioY + " " + 
			parallaxPage.speed + " " + 
			parallaxPage.pad_Bottom_Ratio + " ");
    }
    
    public static Parallax_Model buildPageModel()
    {
    	Parallax_Model parallaxPage = new Parallax_Model() ; 
    	parallaxPage.region_Name = "region_Name" ; 
    	parallaxPage.region_Position = "region_Position" ; 
    	parallaxPage.ratioX = 1 ; 
    	parallaxPage.ratioY = 2 ; 
    	parallaxPage.speed = 3 ; 
    	parallaxPage.pad_Bottom_Ratio = 4 ; 
    	return parallaxPage ; 
    }

    static public void testPage(Kryo kryo) throws FileNotFoundException
    {
    	Page_Model page = buildPage() ; 
    	
    	Output output = new Output(new FileOutputStream(path + pageName + format));
    	kryo.writeObject(output, page);
    	output.close();

    	Input input = new Input(new FileInputStream(path + pageName + format));
    	Page_Model model = kryo.readObject(input, Page_Model.class);
    	input.close();   
    	System.out.println("testPage work " + " Nuumber :" + page.pageList.size());
    }
    
    static public Page_Model buildPage()
    {
    	Page_Model page = new Page_Model() ; 
    	page.atlasPath = "atlasPath" ;
    	page.outside = false ; 
    	page.pageList = new ArrayList<Parallax_Model>() ; 
    	page.pageList.add(buildPageModel()) ; 
    	page.pageList.add(buildPageModel()) ; 
    	page.pageList.add(buildPageModel()) ; 
    	return page ; 
    }
    
    static public void testWholePage(Kryo kryo) throws FileNotFoundException
    {
    	Page_Whole_Model wholePage = new Page_Whole_Model() ; 
    	wholePage.colorSurronding = Color.BLACK ; 
    	wholePage.topHalf_top = Color.BLUE ; 
    	wholePage.topHalf_bottom = Color.CHARTREUSE ; 
    	wholePage.bottomHalf = Color.FOREST ; 
    	
    	wholePage.pageModel = buildPage() ; 
    	
    	Output output = new Output(new FileOutputStream(path + pageWholeName + format));
    	kryo.writeObject(output, wholePage);
    	output.close();

    	Input input = new Input(new FileInputStream(path + pageWholeName + format));
    	Page_Whole_Model model = kryo.readObject(input, Page_Whole_Model.class);
    	System.out.println("testWholePage work " + " Nuumber :" + model.bottomHalf);
    	   
    }
    
    
	   
    /*
     public Color colorSurronding ; 
	
	public Color topHalf_top ; 
	public Color topHalf_bottom ;
	public Color bottomHalf ;
	
	public Page_Model pageModel ; 
     */
    
   static public class SomeClass 
   {
      String value;
   }
}
package jks.tools2d.parallax.editor.gvars;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.esotericsoftware.kryo.Kryo;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import jks.tools2d.parallax.editor.vue.edition.data.ParallaxDefaultValues;
import jks.tools2d.parallax.editor.vue.edition.data.Position_Infos;
import jks.tools2d.parallax.editor.vue.edition.data.Project_Data;
import jks.tools2d.parallax.editor.vue.edition.data.WholePage_Editor;
import jks.tools2d.parallax.heart.GVars_Serialization;
import jks.tools2d.parallax.heart.MyMixInForIgnoreType;
import jks.tools2d.parallax.pages.Color_Serializer;
import jks.tools2d.parallax.pages.Page_Model;
import jks.tools2d.parallax.pages.Parallax_Model;
import jks.tools2d.parallax.pages.WholePage_Model;

public class GVars_Serialization_Editor 
{

	public static void init() 
	{
		GVars_Serialization.kryo = prepareKryo() ; 
		GVars_Serialization.objectMapper = prepareJson() ; 
	}

	private static Kryo prepareKryo()
	{
		Kryo kryo = new Kryo();
	    kryo.register(Color.class, new Color_Serializer());
	    kryo.register(Parallax_Model.class) ; 
	    kryo.register(Page_Model.class) ;
	    kryo.register(ArrayList.class) ; 
	    kryo.register(WholePage_Model.class) ;
	    kryo.register(WholePage_Editor.class) ;
	    kryo.register(ParallaxDefaultValues.class) ; 
	    kryo.register(Position_Infos.class) ; 
	    kryo.register(Project_Data.class) ; 
	    return kryo ; 
	}
	
	private static ObjectMapper prepareJson() 
	{
		ObjectMapper objectMapper = new ObjectMapper() ; 
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false) ; 
		objectMapper.addMixInAnnotations(TextureRegion.class, MyMixInForIgnoreType.class);
		return objectMapper ; 
	}
}

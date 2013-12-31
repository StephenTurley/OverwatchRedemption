/*******************************************************************************
 * Copyright 2013 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/
package core.level;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.UUID;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.Element;
import org.lwjgl.util.Point;

import core.Debug;
import core.Game;
import core.entity.Entity;
import core.entity.EntityRegistrar;
import core.exception.LevelFormatException;
import core.exception.TileSetLoadFailureException;


public class MapParser {
	
	private Document mapData;

	public MapParser(InputStream fileStream) {	
		parseMap(fileStream);
	}
	
	private void parseMap(InputStream fileStream)
	{
		SAXBuilder sb = new SAXBuilder();
		
		try {
			mapData = sb.build(fileStream);
		} catch (JDOMException | IOException e) {

			Debug.Trace(e.getMessage());
			Game.exit(-1);
		}
	}
	
	public int getWidth()
	{
		Element map = mapData.getRootElement();
		return Integer.parseInt(map.getAttribute("width").getValue());
	}
	public int getHeight()
	{
		Element map = mapData.getRootElement();
		return Integer.parseInt(map.getAttribute("height").getValue());
	}
	public int getTileWidth()
	{
		Element map = mapData.getRootElement();
		return Integer.parseInt(map.getAttribute("tilewidth").getValue());
	}
	public int getTileHeight()
	{
		Element map = mapData.getRootElement();
		return Integer.parseInt(map.getAttribute("tileheight").getValue());
	}
	
	public ArrayList<TileSet> getTileSets() throws TileSetLoadFailureException
	{
		ArrayList<TileSet> tileSets =  new ArrayList<TileSet>();
		
		Element map = mapData.getRootElement();
		for(Element ts : map.getChildren("tileset"))
		{
			int firstGID = Integer.parseInt(ts.getAttributeValue("firstgid"));		
			int tileWidth = Integer.parseInt(ts.getAttributeValue("tilewidth"));
			int tileHeight = Integer.parseInt(ts.getAttributeValue("tileheight"));
			
			Element image = ts.getChild("image");
			
			String path = image.getAttributeValue("source");
			int width = Integer.parseInt(image.getAttributeValue("width"));
			int height = Integer.parseInt(image.getAttributeValue("height"));
			
			try{
				tileSets.add(new TileSet(path,tileWidth,tileHeight, width, height, firstGID));
			}
			catch(Exception e)
			{
				throw new TileSetLoadFailureException("TileSet malformed or missing assets. Does this file exist?: " + path );
			}
		}
		return tileSets;
	}
	
	public ArrayList<Layer> getLayers() throws LevelFormatException
	{
		ArrayList<Layer> layers = new ArrayList<Layer>();
		
		Element map = mapData.getRootElement();
		for(Element layer : map.getChildren("layer"))
		{
			try
			{
				int key = Integer.parseInt(layer.getAttributeValue("name"));
				String data = layer.getChildText("data");
				data = data.replace("\n", "").replace("\r", "");
				layers.add(new Layer(data,getWidth(),getHeight(),key));
			}
			catch(NumberFormatException e)
			{
				throw new LevelFormatException("Layer names must be an integer value. (The value should coorispond to they layer's depth)");
			}
				
		}
		return layers;
	}
	public ArrayList<Entity> getEntities() throws Exception
	{
		ArrayList<Entity> entities = new ArrayList<Entity>();
		
		Element map = mapData.getRootElement();
		

		for(Element objectGroup : map.getChildren("objectgroup"))
		{
			if(objectGroup.getAttribute("name").getValue().equals("entity"))
			{
				for(Element object: objectGroup.getChildren("object"))
				{
					String className = object.getAttribute("type").getValue();
					int x = object.getAttribute("x").getIntValue();
					int y = object.getAttribute("y").getIntValue();
					int layer = 9999;
					UUID id = null;
					
					Element properties = object.getChild("properties");
					
					for(Element property: properties.getChildren())
					{
						if(property.getAttribute("name").getValue().equals("id"))
						{
							id = UUID.fromString(property.getAttribute("value").getValue());
						}
						else if(property.getAttribute("name").getValue().equals("layer"))
						{
							layer = property.getAttribute("value").getIntValue();
						}
					}
					if(id != null || layer != 9999)
					{
						entities.add(EntityRegistrar.createEntity(className, id, new Point(x,y), layer));
					}
					else
					{
						throw new LevelFormatException("Entity properties are malformed");
					}
				}
			}
		}


		
		return entities;
	}
}

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

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.Element;
import org.lwjgl.util.Point;

import core.Debug;
import core.Game;
import core.exception.LevelComponentsNotSatisfiedException;


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
	public ArrayList<Point>	getStartingPoints() throws LevelComponentsNotSatisfiedException
	{
		ArrayList<Point> startingPoints = new ArrayList<Point>();
		
		Element map = mapData.getRootElement();
		for(Element og : map.getChildren("objectgroup"))
		{
			for(Element e : og.getChildren())
			{
				if (e.getAttributeValue("type").equals("startingPoint"))
				{
					startingPoints.add(new Point(Integer.parseInt(e.getAttributeValue("x")),
												 Integer.parseInt(e.getAttributeValue("y"))));
				}
			}
		}
		if(startingPoints == null || startingPoints.size() < 2) 
			throw new LevelComponentsNotSatisfiedException("A map must contain at least two startingPoints");
		return startingPoints;
	}

	public ArrayList<TileSet> getTileSets()
	{
		ArrayList<TileSet> tileSets =  new ArrayList<TileSet>();
		
		Element map = mapData.getRootElement();
		for(Element og : map.getChildren("tileset"))
		{
			int firstGID = Integer.parseInt(og.getAttributeValue("firstgid"));		
			int tileWidth = Integer.parseInt(og.getAttributeValue("tilewidth"));
			int tileHeight = Integer.parseInt(og.getAttributeValue("tileheight"));
			
			Element image = og.getChild("image");
			
			String path = image.getAttributeValue("source");
			int width = Integer.parseInt(image.getAttributeValue("width"));
			int height = Integer.parseInt(image.getAttributeValue("height"));
			
			tileSets.add(new TileSet(path,tileWidth,tileHeight, width, height, firstGID));
		}
		return tileSets;
	}
}

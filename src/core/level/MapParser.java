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
	
}

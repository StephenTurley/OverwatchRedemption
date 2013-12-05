/*******************************************************************************
 * Copyright 2013 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/
package core.level;

import java.io.IOException;
import java.io.InputStream;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.Element;

import core.Debug;


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
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.starstuffgames.core.audio;

import com.starstuffgames.core.Debug;
import java.io.IOException;
import java.util.HashMap;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.util.ResourceLoader;


/**
 *
 * @author stephen
 */
public class MusicMap
{
	private static HashMap<String, Audio> musicMap;
	private static Audio current;
	
	
	public static void add(String musicKey, String path)
	{
		if(musicMap == null)
		{
			musicMap = new HashMap<>();
		}
		try
		{
			Audio music = AudioLoader.getStreamingAudio("OGG", ResourceLoader.getResource(path));
			musicMap.put(musicKey, music);
		}catch(IOException e)
		{
			Debug.Trace("IO Exception: Audio not loaded");
		}
		
		
	}
	
	public static void setVolume(float volume)
	{
		SoundStore.get().setCurrentMusicVolume(volume);
	}
	
	public static void play(String audioKey)
	{
		if(current != null)
		{
			current.stop();
		}
		current = musicMap.get(audioKey);
		
		if (current == null)
		{
			Debug.Trace("Music not found");
			return;
		}
		current.playAsMusic(1.0f, 1.0f, true);
			
	}
}

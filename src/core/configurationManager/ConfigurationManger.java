package core.configurationManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import core.Debug;
import core.Game;


public class ConfigurationManger {
	
	private static final String CONFIG_PATH = "res/config/GameConfig.cfg";
	
	public static GameConfig loadConfiguration()
	{
		GameConfig config = parseGameConfig();
		
		return config;
	}
	
	private static GameConfig parseGameConfig()
	{
		GameConfig config = new GameConfig();
		try{
			BufferedReader br = new BufferedReader(new FileReader(CONFIG_PATH));
			String line = null;
			while((line = br.readLine()) != null)
			{
				String[] keyValue = line.split("\\|");
				
				if(keyValue.length == 2)
				{
					
					String key = keyValue[0];
					String value = keyValue[1];
					
					switch (key)
					{
						case "displayWidth" :
							config.setDisplayWidth(Integer.parseInt(value));
							break;
						case "displayHeight" :
							config.setDisplayHeight(Integer.parseInt(value));
							break;
						case "displayName" :
							config.setDisplayName(value);
							break;
						case "fullScreen" :
							config.setFullScreen(Boolean.parseBoolean(value));
							break;
						case "debugLogging" :
							config.setDebugLogging(Boolean.parseBoolean(value));
							break;
						case "joyStickDeadZone":
							config.setJoyStickDeadZone(Float.parseFloat(value));
							break;
						case "serverUDP":
							config.setServerUDP(Integer.parseInt(value));
							break;
						case "serverTCP" :
							config.setServerTCP(Integer.parseInt(value));
							break;
						case "frameRate" :
							config.setFrameRate(Integer.parseInt(value));
							break;
						default: Debug.Trace("Invalid Config parameter: " + key);
					}
				}
			}
			br.close();
		}catch(IOException e)
		{
			e.printStackTrace();
			Game.exit(1);
		}catch(NumberFormatException e)
		{
			Debug.Trace("Invalid GameConfig.cfg");
			Game.exit(1);
		}catch(Exception e)
		{
			Debug.Trace("Something bad went wrong parsing the GameConfig.cfg. Did you delete a line?");
			Game.exit(1);
		}
		
		
		return config;
	}

}

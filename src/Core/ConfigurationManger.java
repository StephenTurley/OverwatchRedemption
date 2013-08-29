package Core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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
				String key = line.split("\\|")[0];
				String value = line.split("\\|")[1];
				
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
					default: Debug.Trace("Invalid Config parameter: " + key);
				}
			}
			br.close();
		}catch(IOException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		
		
		return config;
	}

}

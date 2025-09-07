package com.maniacalhunter;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("maniacalhunter")
public interface ManiacalHunterConfig extends Config
{
	@ConfigItem(
		keyName = "greeting",
		name = "Welcome Greeting",
		description = "The message to show to the user when they login"
	)
	default String greeting()
	{
		return "Hello";
	}

	@ConfigItem(
		keyName = "displayMode",
		name = "Display Mode",
		description = "Choose which stats to display in the overlay"
	)
	default DisplayMode displayMode()
	{
		return DisplayMode.SESSION_ONLY;
	}
}

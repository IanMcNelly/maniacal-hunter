package com.maniacalhunter;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

@ConfigGroup("maniacalhunter")
public interface ManiacalHunterConfig extends Config
{
	@ConfigSection(
			name = "Display",
			description = "Configure which stats to display",
			position = 0
	)
	String displaySection = "display";

	@ConfigItem(
			keyName = "showXpGained",
			name = "Show XP Gained",
			description = "Show the total XP gained",
			section = displaySection
	)
	default boolean showXpGained()
	{
		return true;
	}

	@ConfigItem(
			keyName = "showMonkeysCaught",
			name = "Show Monkeys Caught",
			description = "Show the total number of monkeys caught",
			section = displaySection
	)
	default boolean showMonkeysCaught()
	{
		return true;
	}

	@ConfigItem(
			keyName = "showTrapsLaid",
			name = "Show Traps Laid",
			description = "Show the total number of traps laid",
			section = displaySection
	)
	default boolean showTrapsLaid()
	{
		return true;
	}

	@ConfigItem(
			keyName = "showLastTrapStatus",
			name = "Show Last Trap Status",
			description = "Show the status of the last trap",
			section = displaySection
	)
	default boolean showLastTrapStatus()
	{
		return true;
	}

	@ConfigItem(
			keyName = "showSuccessRate",
			name = "Show Success Rate",
			description = "Show the success rate of catching monkeys",
			section = displaySection
	)
	default boolean showSuccessRate()
	{
		return true;
	}

	@ConfigItem(
			keyName = "showMonkeysPerHour",
			name = "Show Monkeys/Hour",
			description = "Show the number of monkeys caught per hour",
			section = displaySection
	)
	default boolean showMonkeysPerHour()
	{
		return true;
	}

	@ConfigItem(
			keyName = "showPerfectTails",
			name = "Show Perfect Tails",
			description = "Show the number of perfect tails received",
			section = displaySection
	)
	default boolean showPerfectTails()
	{
		return true;
	}

	@ConfigItem(
			keyName = "showDamagedTails",
			name = "Show Damaged Tails",
			description = "Show the number of damaged tails received",
			section = displaySection
	)
	default boolean showDamagedTails()
	{
		return true;
	}

	@ConfigItem(
			keyName = "showLuck",
			name = "Show Luck",
			description = "Show your current luck in receiving a perfect tail",
			section = displaySection
	)
	default boolean showLuck()
	{
		return true;
	}

	@ConfigItem(
			keyName = "showXpPerHour",
			name = "Show XP/Hour",
			description = "Show the average XP gained per hour",
			section = displaySection
	)
	default boolean showXpPerHour()
	{
		return true;
	}

	@ConfigItem(
			keyName = "showAvgCatchTime",
			name = "Show Avg. Catch Time",
			description = "Show the average time to catch a monkey",
			section = displaySection
	)
	default boolean showAvgCatchTime()
	{
		return true;
	}

	@ConfigItem(
			keyName = "displayMode",
			name = "Display Mode",
			description = "Choose how to display stats in the overlay",
			position = 1
	)
	default DisplayMode displayMode()
	{
		return DisplayMode.BOTH;
	}

	@ConfigSection(
			name = "Actions",
			description = "Perform actions on the session",
			position = 2
	)
	String actionsSection = "actions";

	@ConfigItem(
			keyName = "resetSessionButton",
			name = "Reset Session",
			description = "Reset the current session stats",
			section = actionsSection
	)
	default boolean resetSession()
	{
		return false;
	}

	@ConfigSection(
			name = "Notifications",
			description = "Configure notifications for milestones",
			position = 3
	)
	String notificationsSection = "notifications";

	@ConfigItem(
			keyName = "milestoneNotification",
			name = "Milestone Notification",
			description = "Notify when you reach a milestone of monkeys caught",
			section = notificationsSection
	)
	default boolean milestoneNotification()
	{
		return true;
	}

	@ConfigItem(
			keyName = "milestoneInterval",
			name = "Milestone Interval",
			description = "The interval at which to send a milestone notification",
			section = notificationsSection
	)
	default int milestoneInterval()
	{
		return 100;
	}
}

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
			keyName = "condensedMode",
			name = "Condensed Mode",
			description = "Display stats in an infobox.",
			section = displaySection
	)
	default boolean condensedMode()
	{
		return false;
	}

	@ConfigSection(
			name = "Auto Reset",
			description = "Automatically reset the session",
			position = 4
	)
	String autoResetSection = "autoReset";

	@ConfigItem(
			keyName = "autoResetMode",
			name = "Auto Reset Session",
			description = "Automatically reset the session upon entering or leaving the area",
			section = autoResetSection
	)
	default ResetMode autoResetMode()
	{
		return ResetMode.NEVER;
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
			position = 1,
			section = displaySection
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

	@ConfigItem(
			keyName = "sessionStartTime",
			name = "Session Start Time",
			description = "The time the session started",
			hidden = true
	)
	default long getSessionStartTime()
	{
		return 0L;
	}

	@ConfigItem(
			keyName = "sessionStartTime",
			name = "Session Start Time",
			description = "The time the session started"
	)
	void setSessionStartTime(long time);

	@ConfigItem(
			keyName = "duration",
			name = "Duration",
			description = "The session duration in milliseconds",
			hidden = true
	)
	default long getDuration()
	{
		return 0L;
	}

	@ConfigItem(
			keyName = "duration",
			name = "Duration",
			description = "The session duration in milliseconds"
	)
	void setDuration(long time);

	@ConfigItem(
			keyName = "monkeysCaught",
			name = "Monkeys Caught",
			description = "The number of monkeys caught",
			hidden = true
	)
	default int getMonkeysCaught()
	{
		return 0;
	}

	@ConfigItem(
			keyName = "monkeysCaught",
			name = "Monkeys Caught",
			description = "The number of monkeys caught"
	)
	void setMonkeysCaught(int count);

	@ConfigItem(
			keyName = "trapsLaid",
			name = "Traps Laid",
			description = "The number of traps laid",
			hidden = true
	)
	default int getTrapsLaid()
	{
		return 0;
	}

	@ConfigItem(
			keyName = "trapsLaid",
			name = "Traps Laid",
			description = "The number of traps laid"
	)
	void setTrapsLaid(int count);

	@ConfigItem(
			keyName = "lastTrapStatus",
			name = "Last Trap Status",
			description = "The status of the last trap",
			hidden = true
	)
	default String getLastTrapStatus()
	{
		return "N/A";
	}

	@ConfigItem(
			keyName = "lastTrapStatus",
			name = "Last Trap Status",
			description = "The status of the last trap"
	)
	void setLastTrapStatus(String status);

	@ConfigItem(
			keyName = "perfectTails",
			name = "Perfect Tails",
			description = "The number of perfect tails",
			hidden = true
	)
	default int getPerfectTails()
	{
		return 0;
	}

	@ConfigItem(
			keyName = "perfectTails",
			name = "Perfect Tails",
			description = "The number of perfect tails"
	)
	void setPerfectTails(int count);

	@ConfigItem(
			keyName = "damagedTails",
			name = "Damaged Tails",
			description = "The number of damaged tails",
			hidden = true
	)
	default int getDamagedTails()
	{
		return 0;
	}

	@ConfigItem(
			keyName = "damagedTails",
			name = "Damaged Tails",
			description = "The number of damaged tails"
	)
	void setDamagedTails(int count);

	@ConfigItem(
			keyName = "damagedTailsSincePerfect",
			name = "Damaged Tails Since Perfect",
			description = "The number of damaged tails since the last perfect tail",
			hidden = true
	)
	default int getDamagedTailsSincePerfect()
	{
		return 0;
	}

	@ConfigItem(
			keyName = "damagedTailsSincePerfect",
			name = "Damaged Tails Since Perfect",
			description = "The number of damaged tails since the last perfect tail"
	)
	void setDamagedTailsSincePerfect(int count);
}

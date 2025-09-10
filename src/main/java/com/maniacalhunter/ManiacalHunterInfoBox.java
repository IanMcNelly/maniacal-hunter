package com.maniacalhunter;

import java.awt.Color;
import java.awt.image.BufferedImage;
import net.runelite.client.ui.overlay.infobox.InfoBox;

public class ManiacalHunterInfoBox extends InfoBox
{
	private final ManiacalHunterPlugin plugin;
	private final ManiacalHunterConfig config;

	public ManiacalHunterInfoBox(ManiacalHunterPlugin plugin, ManiacalHunterConfig config, BufferedImage image)
	{
		super(image, plugin);
		this.plugin = plugin;
		this.config = config;
	}

	@Override
	public String getText()
	{
		return String.valueOf(plugin.getSession().getMonkeysCaught());
	}

	@Override
	public Color getTextColor()
	{
		return Color.WHITE;
	}

	@Override
	public String getTooltip()
	{
		ManiacalHunterSession session = plugin.getSession();
		ManiacalHunterSession aggregateSession = plugin.getAggregateSession();
		StringBuilder sb = new StringBuilder();
		sb.append("Maniacal Hunter");

		if (config.showMonkeysCaught()) {
			sb.append("</br>");
			sb.append("Monkeys Caught: ").append(ManiacalHunterFormatting.formatStat(session.getMonkeysCaught(), aggregateSession.getMonkeysCaught(), config.displayMode()));
		}

		if (config.showTrapsLaid()) {
			sb.append("</br>");
			sb.append("Traps Laid: ").append(ManiacalHunterFormatting.formatStat(session.getTrapsLaid(), aggregateSession.getTrapsLaid(), config.displayMode()));
		}

		if (config.showLastTrapStatus()) {
			sb.append("</br>");
			sb.append("Last Trap Status: ").append(session.getLastTrapStatus());
		}

		if (config.showSuccessRate()) {
			sb.append("</br>");
			sb.append("Success Rate: ").append(ManiacalHunterFormatting.formatPercentage(session.getSuccessRate(), aggregateSession.getSuccessRate(), config.displayMode()));
		}

		if (config.showMonkeysPerHour()) {
			sb.append("</br>");
			sb.append("Monkeys/Hour: ").append(ManiacalHunterFormatting.formatDouble(session.getMonkeysPerHour(), aggregateSession.getMonkeysPerHour(), config.displayMode()));
		}

		if (config.showPerfectTails()) {
			sb.append("</br>");
			sb.append("Perfect Tails: ").append(ManiacalHunterFormatting.formatStat(session.getPerfectTails(), aggregateSession.getPerfectTails(), config.displayMode()));
		}

		if (config.showDamagedTails()) {
			sb.append("</br>");
			sb.append("Damaged Tails: ").append(ManiacalHunterFormatting.formatStat(session.getDamagedTails(), aggregateSession.getDamagedTails(), config.displayMode()));
		}

		if (config.showLuck()) {
			sb.append("</br>");
			sb.append("Luck: ").append(ManiacalHunterFormatting.formatPercentage(session.getLuckPercentage(), aggregateSession.getLuckPercentage(), config.displayMode()));
		}

		if (config.showAvgCatchTime()) {
			sb.append("</br>");
			sb.append("Avg. Catch Time: ").append(String.format("%.2fs", session.getAverageTimePerCatch()));
		}

		return sb.toString();
	}
}

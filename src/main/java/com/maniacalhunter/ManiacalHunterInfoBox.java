package com.maniacalhunter;

import java.awt.Color;
import java.awt.image.BufferedImage;
import net.runelite.client.plugins.Plugin;
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
			sb.append("Monkeys Caught: ").append(formatStat(session.getMonkeysCaught(), aggregateSession.getMonkeysCaught()));
		}

		if (config.showTrapsLaid()) {
			sb.append("</br>");
			sb.append("Traps Laid: ").append(formatStat(session.getTrapsLaid(), aggregateSession.getTrapsLaid()));
		}

		if (config.showLastTrapStatus()) {
			sb.append("</br>");
			sb.append("Last Trap Status: ").append(session.getLastTrapStatus());
		}

		if (config.showSuccessRate()) {
			sb.append("</br>");
			sb.append("Success Rate: ").append(formatPercentage(session.getSuccessRate(), aggregateSession.getSuccessRate()));
		}

		if (config.showMonkeysPerHour()) {
			sb.append("</br>");
			sb.append("Monkeys/Hour: ").append(formatDouble(session.getMonkeysPerHour(), aggregateSession.getMonkeysPerHour()));
		}

		if (config.showPerfectTails()) {
			sb.append("</br>");
			sb.append("Perfect Tails: ").append(formatStat(session.getPerfectTails(), aggregateSession.getPerfectTails()));
		}

		if (config.showDamagedTails()) {
			sb.append("</br>");
			sb.append("Damaged Tails: ").append(formatStat(session.getDamagedTails(), aggregateSession.getDamagedTails()));
		}

		if (config.showLuck()) {
			sb.append("</br>");
			sb.append("Luck: ").append(formatPercentage(session.getLuckPercentage(), aggregateSession.getLuckPercentage()));
		}

		if (config.showAvgCatchTime()) {
			sb.append("</br>");
			sb.append("Avg. Catch Time: ").append(String.format("%.2fs", session.getAverageTimePerCatch()));
		}

		return sb.toString();
	}

	private String formatStat(int session, int aggregate)
	{
		switch (config.displayMode())
		{
			case SESSION_ONLY:
				return String.valueOf(session);
			case AGGREGATE_ONLY:
				return String.valueOf(aggregate);
			case BOTH:
				return String.format("%d (%d)", session, aggregate);
			default:
				return "";
		}
	}

	private String formatPercentage(double session, double aggregate)
	{
		switch (config.displayMode())
		{
			case SESSION_ONLY:
				return String.format("%.2f%%", session);
			case AGGREGATE_ONLY:
				return String.format("%.2f%%", aggregate);
			case BOTH:
				return String.format("%.2f%% (%.2f%%)", session, aggregate);
			default:
				return "";
		}
	}

	private String formatDouble(double session, double aggregate)
	{
		switch (config.displayMode())
		{
			case SESSION_ONLY:
				return String.format("%.2f", session);
			case AGGREGATE_ONLY:
				return String.format("%.2f", aggregate);
			case BOTH:
				return String.format("%.2f (%.2f)", session, aggregate);
			default:
				return "";
		}
	}
}

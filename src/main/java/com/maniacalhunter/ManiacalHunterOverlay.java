package com.maniacalhunter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import javax.inject.Inject;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.TitleComponent;

public class ManiacalHunterOverlay extends OverlayPanel
{
	private final ManiacalHunterPlugin plugin;
	private final ManiacalHunterConfig config;

	@Inject
	private ManiacalHunterOverlay(ManiacalHunterPlugin plugin, ManiacalHunterConfig config)
	{
		super(plugin);
		this.plugin = plugin;
		this.config = config;
		setPosition(OverlayPosition.TOP_LEFT);
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		ManiacalHunterSession session = plugin.getSession();
		ManiacalHunterSession aggregateSession = plugin.getAggregateSession();

		panelComponent.getChildren().clear();

		panelComponent.getChildren().add(TitleComponent.builder()
			.text("Maniacal Hunter")
			.color(Color.WHITE)
			.build());

		panelComponent.getChildren().add(LineComponent.builder()
			.left("XP Gained:")
			.right(formatStat(session.getXpGained(), aggregateSession.getXpGained()))
			.build());

		panelComponent.getChildren().add(LineComponent.builder()
			.left("Monkeys Caught:")
			.right(formatStat(session.getMonkeysCaught(), aggregateSession.getMonkeysCaught()))
			.build());

		panelComponent.getChildren().add(LineComponent.builder()
			.left("Traps Laid:")
			.right(formatStat(session.getTrapsLaid(), aggregateSession.getTrapsLaid()))
			.build());

		panelComponent.getChildren().add(LineComponent.builder()
			.left("Last Trap Status:")
			.right(session.getLastTrapStatus())
			.build());

		panelComponent.getChildren().add(LineComponent.builder()
			.left("Success Rate:")
			.right(formatPercentage(session.getSuccessRate(), aggregateSession.getSuccessRate()))
			.build());

		panelComponent.getChildren().add(LineComponent.builder()
			.left("Monkeys/Hour:")
			.right(formatDouble(session.getMonkeysPerHour(), aggregateSession.getMonkeysPerHour()))
			.build());

		panelComponent.getChildren().add(LineComponent.builder()
			.left("Perfect Tails:")
			.right(formatStat(session.getPerfectTails(), aggregateSession.getPerfectTails()))
			.build());

		panelComponent.getChildren().add(LineComponent.builder()
			.left("Damaged Tails:")
			.right(formatStat(session.getDamagedTails(), aggregateSession.getDamagedTails()))
			.build());

		panelComponent.getChildren().add(LineComponent.builder()
			.left("Luck:")
			.right(formatPercentage(session.getLuckPercentage(), aggregateSession.getLuckPercentage()))
			.build());

		return super.render(graphics);
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

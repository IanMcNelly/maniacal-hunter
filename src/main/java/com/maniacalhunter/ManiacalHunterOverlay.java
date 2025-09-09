package com.maniacalhunter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.inject.Inject;

import net.runelite.api.Client;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.ImageComponent;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.TitleComponent;

public class ManiacalHunterOverlay extends OverlayPanel
{
	private final ManiacalHunterPlugin plugin;
	private final ManiacalHunterConfig config;
    private final ImageComponent imageComponent;
	private final Client client;

	@Inject
	private ManiacalHunterOverlay(ManiacalHunterPlugin plugin, ManiacalHunterConfig config, Client client)
	{
		super(plugin);
		this.plugin = plugin;
		this.config = config;
		this.client = client;
        this.imageComponent = new ImageComponent(plugin.getIcon());
		setPosition(OverlayPosition.TOP_LEFT);
	}

	private boolean isInManiacalHunterArea()
	{
		return client.getLocalPlayer() != null && client.getLocalPlayer().getWorldLocation().getRegionID() == ManiacalHunterConstants.MANIACAL_HUNTER_REGION;
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		if (!isInManiacalHunterArea())
		{
			return null;
		}
		ManiacalHunterSession session = plugin.getSession();
		ManiacalHunterSession aggregateSession = plugin.getAggregateSession();

		panelComponent.getChildren().clear();

		if (config.condensedMode() && !getBounds().contains(new java.awt.Point(plugin.getMouseCanvasPosition().getX(), plugin.getMouseCanvasPosition().getY())))
		{
            panelComponent.getChildren().add(imageComponent);
			panelComponent.getChildren().add(LineComponent.builder()
				.left("Caught:")
				.right(formatStat(session.getMonkeysCaught(), aggregateSession.getMonkeysCaught()))
				.build());
			return super.render(graphics);
		}

		panelComponent.getChildren().add(TitleComponent.builder()
			.text("Maniacal Hunter")
			.color(Color.WHITE)
			.build());

		if (config.showMonkeysCaught()) {
			panelComponent.getChildren().add(LineComponent.builder()
				.left("Monkeys Caught:")
				.right(formatStat(session.getMonkeysCaught(), aggregateSession.getMonkeysCaught()))
				.build());
		}

		if (config.showTrapsLaid()) {
			panelComponent.getChildren().add(LineComponent.builder()
				.left("Traps Laid:")
				.right(formatStat(session.getTrapsLaid(), aggregateSession.getTrapsLaid()))
				.build());
		}

		if (config.showLastTrapStatus()) {
			panelComponent.getChildren().add(LineComponent.builder()
				.left("Last Trap Status:")
				.right(session.getLastTrapStatus())
				.build());
		}

		if (config.showSuccessRate()) {
			panelComponent.getChildren().add(LineComponent.builder()
				.left("Success Rate:")
				.right(formatPercentage(session.getSuccessRate(), aggregateSession.getSuccessRate()))
				.build());
		}

		if (config.showMonkeysPerHour()) {
			panelComponent.getChildren().add(LineComponent.builder()
				.left("Monkeys/Hour:")
				.right(formatDouble(session.getMonkeysPerHour(), aggregateSession.getMonkeysPerHour()))
				.build());
		}

		if (config.showPerfectTails()) {
			panelComponent.getChildren().add(LineComponent.builder()
				.left("Perfect Tails:")
				.right(formatStat(session.getPerfectTails(), aggregateSession.getPerfectTails()))
				.build());
		}

		if (config.showDamagedTails()) {
			panelComponent.getChildren().add(LineComponent.builder()
				.left("Damaged Tails:")
				.right(formatStat(session.getDamagedTails(), aggregateSession.getDamagedTails()))
				.build());
		}

		if (config.showLuck()) {
			panelComponent.getChildren().add(LineComponent.builder()
				.left("Luck:")
				.right(formatPercentage(session.getLuckPercentage(), aggregateSession.getLuckPercentage()))
				.build());
		}

		if (config.showAvgCatchTime()) {
			panelComponent.getChildren().add(LineComponent.builder()
				.left("Avg. Catch Time:")
				.right(String.format("%.2fs", session.getAverageTimePerCatch()))
				.build());
		}

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

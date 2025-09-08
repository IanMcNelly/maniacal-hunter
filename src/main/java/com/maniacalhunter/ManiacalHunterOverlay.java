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
	private final BufferedImage icon;
	private final Client client;

	@Inject
	private ManiacalHunterOverlay(ManiacalHunterPlugin plugin, ManiacalHunterConfig config, Client client)
	{
		super(plugin);
		this.plugin = plugin;
		this.config = config;
		this.icon = plugin.getIcon();
		this.client = client;
		setPosition(OverlayPosition.TOP_LEFT);
	}

	private boolean isInManiacalHunterArea()
	{
		return client.getLocalPlayer() != null && client.getLocalPlayer().getWorldLocation().getRegionID() == ManiacalHunterConstants.MANIACAL_HUNTER_REGION;
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		panelComponent.getChildren().clear();
		panelComponent.getChildren().add(TitleComponent.builder()
			.text("Region ID:")
			.color(Color.WHITE)
			.build());
		panelComponent.getChildren().add(LineComponent.builder()
			.left(String.valueOf(client.getLocalPlayer().getWorldLocation().getRegionID()))
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

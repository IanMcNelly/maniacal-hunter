package com.maniacalhunter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import javax.inject.Inject;

import net.runelite.api.Client;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.TitleComponent;

public class ManiacalHunterOverlay extends OverlayPanel
{
	private final ManiacalHunterPlugin plugin;
	private final ManiacalHunterConfig config;
	private final Client client;

	@Inject
	private ManiacalHunterOverlay(ManiacalHunterPlugin plugin, ManiacalHunterConfig config, Client client)
	{
		super(plugin);
		this.plugin = plugin;
		this.config = config;
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
		if (!isInManiacalHunterArea())
		{
			return null;
		}
		ManiacalHunterSession session = plugin.getSession();
		ManiacalHunterSession aggregateSession = plugin.getAggregateSession();

		panelComponent.getChildren().clear();

		panelComponent.getChildren().add(TitleComponent.builder()
			.text("Maniacal Hunter")
			.color(Color.WHITE)
			.build());

		if (config.showMonkeysCaught()) {
			panelComponent.getChildren().add(LineComponent.builder()
				.left("Monkeys Caught:")
				.right(ManiacalHunterFormatting.formatStat(session.getMonkeysCaught(), aggregateSession.getMonkeysCaught(), config.displayMode()))
				.build());
		}

		if (config.showTrapsLaid()) {
			panelComponent.getChildren().add(LineComponent.builder()
				.left("Traps Laid:")
				.right(ManiacalHunterFormatting.formatStat(session.getTrapsLaid(), aggregateSession.getTrapsLaid(), config.displayMode()))
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
				.right(ManiacalHunterFormatting.formatPercentage(session.getSuccessRate(), aggregateSession.getSuccessRate(), config.displayMode()))
				.build());
		}

		if (config.showMonkeysPerHour()) {
			panelComponent.getChildren().add(LineComponent.builder()
				.left("Monkeys/Hour:")
				.right(ManiacalHunterFormatting.formatDouble(session.getMonkeysPerHour(), aggregateSession.getMonkeysPerHour(), config.displayMode()))
				.build());
		}

		if (config.showPerfectTails()) {
			panelComponent.getChildren().add(LineComponent.builder()
				.left("Perfect Tails:")
				.right(ManiacalHunterFormatting.formatStat(session.getPerfectTails(), aggregateSession.getPerfectTails(), config.displayMode()))
				.build());
		}

		if (config.showDamagedTails()) {
			panelComponent.getChildren().add(LineComponent.builder()
				.left("Damaged Tails:")
				.right(ManiacalHunterFormatting.formatStat(session.getDamagedTails(), aggregateSession.getDamagedTails(), config.displayMode()))
				.build());
		}

		if (config.showLuck()) {
			panelComponent.getChildren().add(LineComponent.builder()
				.left("Luck:")
				.right(ManiacalHunterFormatting.formatPercentage(session.getLuckPercentage(), aggregateSession.getLuckPercentage(), config.displayMode()))
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
}

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
	private final ManiacalHunterConfig config;
	private final ManiacalHunterSession session;

	@Inject
	private ManiacalHunterOverlay(ManiacalHunterConfig config, ManiacalHunterSession session)
	{
		super(null);
		this.config = config;
		this.session = session;
		setPosition(OverlayPosition.TOP_LEFT);
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		panelComponent.getChildren().clear();

		panelComponent.getChildren().add(TitleComponent.builder()
			.text("Maniacal Hunter")
			.color(Color.WHITE)
			.build());

		panelComponent.getChildren().add(LineComponent.builder()
			.left("XP Gained:")
			.right(String.valueOf(session.getXpGained()))
			.build());

		panelComponent.getChildren().add(LineComponent.builder()
			.left("Monkeys Caught:")
			.right(String.valueOf(session.getMonkeysCaught()))
			.build());

		panelComponent.getChildren().add(LineComponent.builder()
			.left("Traps Laid:")
			.right(String.valueOf(session.getTrapsLaid()))
			.build());

		panelComponent.getChildren().add(LineComponent.builder()
			.left("Last Trap Status:")
			.right(session.getLastTrapStatus())
			.build());

		panelComponent.getChildren().add(LineComponent.builder()
			.left("Success Rate:")
			.right(String.format("%.2f%%", session.getSuccessRate()))
			.build());

		panelComponent.getChildren().add(LineComponent.builder()
			.left("Monkeys/Hour:")
			.right(String.format("%.2f", session.getMonkeysPerHour()))
			.build());

		panelComponent.getChildren().add(LineComponent.builder()
			.left("Perfect Tails:")
			.right(String.valueOf(session.getPerfectTails()))
			.build());

		panelComponent.getChildren().add(LineComponent.builder()
			.left("Damaged Tails:")
			.right(String.valueOf(session.getDamagedTails()))
			.build());

		panelComponent.getChildren().add(LineComponent.builder()
			.left("Luck:")
			.right(String.format("%.2f%%", session.getLuckPercentage()))
			.build());

		return super.render(graphics);
	}
}

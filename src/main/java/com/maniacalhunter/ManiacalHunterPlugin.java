package com.maniacalhunter;

import com.google.inject.Provides;
import java.time.Duration;
import java.time.Instant;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameObject;
import net.runelite.api.GameState;
import net.runelite.api.Skill;
import net.runelite.api.events.GameObjectDespawned;
import net.runelite.api.events.GameObjectSpawned;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.StatChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

@Slf4j
@PluginDescriptor(
	name = "Maniacal Hunter"
)
public class ManiacalHunterPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private ManiacalHunterConfig config;

	@Inject
	private ManiacalHunterSession session;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private ManiacalHunterOverlay overlay;

	private int lastPerfectTails = 0;
	private int lastDamagedTails = 0;

	private void reset()
	{
		session.reset();
		lastPerfectTails = 0;
		lastDamagedTails = 0;
	}

	@Override
	protected void startUp() throws Exception
	{
		log.info("Maniacal Hunter started!");
		reset();
		overlayManager.add(overlay);
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.info("Maniacal Hunter stopped!");
		overlayManager.remove(overlay);
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN)
		{
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Maniacal Hunter says " + config.greeting(), null);
		}
	}

	@Subscribe
	public void onItemContainerChanged(ItemContainerChanged event)
	{
		// This implementation is not perfect. It can be fooled by dropping or banking tails.
		// A more robust implementation would require tracking items across all containers, which is out of scope for this plugin.
		if (event.getContainerId() != 93) // Inventory
		{
			return;
		}

		int currentPerfectTails = event.getItemContainer().count(ManiacalHunterConstants.PERFECT_MONKEY_TAIL);
		int currentDamagedTails = event.getItemContainer().count(ManiacalHunterConstants.DAMAGED_MONKEY_TAIL);

		int perfectTailsGained = currentPerfectTails - lastPerfectTails;
		int damagedTailsGained = currentDamagedTails - lastDamagedTails;

		if (perfectTailsGained > 0)
		{
			session.incrementPerfectTails();
		}

		if (damagedTailsGained > 0)
		{
			session.incrementDamagedTails();
		}

		lastPerfectTails = currentPerfectTails;
		lastDamagedTails = currentDamagedTails;
	}

	@Subscribe
	public void onGameTick(GameTick tick)
	{
		if (session.getSessionStartTime() != null)
		{
			session.setDuration(Duration.between(session.getSessionStartTime(), Instant.now()));
		}
	}

	private boolean isInManiacalHunterArea()
	{
		return client.getLocalPlayer() != null && client.getLocalPlayer().getWorldLocation().getRegionID() == ManiacalHunterConstants.MANIACAL_HUNTER_REGION;
	}

	@Subscribe
	public void onStatChanged(StatChanged statChanged)
	{
		if (statChanged.getSkill() == Skill.HUNTER && isInManiacalHunterArea())
		{
			if (session.getStartXp() == 0)
			{
				session.startSession(statChanged.getXp());
			}
			session.setXpGained(statChanged.getXp() - session.getStartXp());
		}
	}

	private static final int MAX_DISTANCE = 10;

	@Subscribe
	public void onGameObjectSpawned(GameObjectSpawned event)
	{
		GameObject gameObject = event.getGameObject();
		if (gameObject == null
			|| !ManiacalHunterConstants.BOULDER_TRAP_IDS.contains(gameObject.getId())
			|| client.getLocalPlayer().getWorldLocation().distanceTo(gameObject.getWorldLocation()) > MAX_DISTANCE)
		{
			return;
		}
		int id = gameObject.getId();

		if (id == ManiacalHunterConstants.SET_BOULDER_TRAP)
		{
			session.setLastTrapStatus("Trap set");
		}
		else if (id == ManiacalHunterConstants.UNSET_BOULDER_TRAP)
		{
			session.setLastTrapStatus("Trap not set");
		}
		else if (id == ManiacalHunterConstants.TRIGGERED_BOULDER_TRAP_1 || id == ManiacalHunterConstants.TRIGGERED_BOULDER_TRAP_2)
		{
			session.setLastTrapStatus("Trap triggered");
		}
		else if (id == ManiacalHunterConstants.CAUGHT_MONKEY_BOULDER_1 || id == ManiacalHunterConstants.CAUGHT_MONKEY_BOULDER_2)
		{
			session.setLastTrapStatus("Monkey caught");
		}
	}

	@Subscribe
	public void onChatMessage(ChatMessage chatMessage)
	{
		String message = chatMessage.getMessage();
		if (message.equals("You get a tail from the monkey."))
		{
			session.incrementMonkeysCaught();
		}
		else if (message.equals("You set the boulder trap."))
		{
			session.incrementTrapsLaid();
		}
	}

	@Provides
	ManiacalHunterConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(ManiacalHunterConfig.class);
	}

	@Provides
	ManiacalHunterSession provideSession()
	{
		return new ManiacalHunterSession();
	}
}

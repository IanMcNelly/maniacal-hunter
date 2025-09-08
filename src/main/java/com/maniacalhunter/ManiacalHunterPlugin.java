package com.maniacalhunter;

import com.google.gson.Gson;
import com.google.inject.Provides;
import java.awt.image.BufferedImage;
import java.time.Duration;
import java.time.Instant;
import javax.inject.Inject;
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
import net.runelite.api.events.StatChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.Notifier;
import net.runelite.client.ui.ClientUI;
import net.runelite.client.util.ImageUtil;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;
import net.runelite.api.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@PluginDescriptor(
	name = "Maniacal Hunter"
)
public class ManiacalHunterPlugin extends Plugin
{
	private static final Logger log = LoggerFactory.getLogger(ManiacalHunterPlugin.class);
	private static final String RESET_BUTTON_KEY = "resetSessionButton";
	private static final String ICON_FILE = "condensed_icon.png";

	private BufferedImage icon;
	private Point mousePosition;

	@Inject
	private Client client;

	@Inject
	private Notifier notifier;

	@Inject
	private ManiacalHunterConfig config;

	@Inject
	private ConfigManager configManager;

	@Inject
	private Gson gson;

	@Inject
	private ManiacalHunterSession session;

	private ManiacalHunterSession aggregateSession = new ManiacalHunterSession();

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

	private static final String CONFIG_GROUP = "maniacalhunter";
	private static final String AGGREGATE_SESSION_KEY = "aggregateSession";

	@Override
	protected void startUp() throws Exception
	{
		log.info("Maniacal Hunter started!");
		loadSession();
		reset();
		icon = ImageUtil.loadImageResource(getClass(), ICON_FILE);
		overlayManager.add(overlay);
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.info("Maniacal Hunter stopped!");
		saveSession();
		overlayManager.remove(overlay);
	}

	private void saveSession()
	{
		String json = gson.toJson(aggregateSession);
		configManager.setConfiguration(CONFIG_GROUP, AGGREGATE_SESSION_KEY, json);
	}

	private void loadSession()
	{
		String json = configManager.getConfiguration(CONFIG_GROUP, AGGREGATE_SESSION_KEY);
		if (json != null)
		{
			aggregateSession = gson.fromJson(json, ManiacalHunterSession.class);
		}
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
	}

	@Subscribe
	public void onItemContainerChanged(ItemContainerChanged event)
	{
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
			aggregateSession.incrementPerfectTails();
			session.incrementMonkeysCaught();
			aggregateSession.incrementMonkeysCaught();
			if (config.milestoneNotification() && session.getMonkeysCaught() % config.milestoneInterval() == 0)
			{
				notifier.notify("Maniacal Hunter milestone: " + session.getMonkeysCaught() + " monkeys caught!");
			}
		}

		if (damagedTailsGained > 0)
		{
			session.incrementDamagedTails();
			aggregateSession.incrementDamagedTails();
			session.incrementMonkeysCaught();
			aggregateSession.incrementMonkeysCaught();
			if (config.milestoneNotification() && session.getMonkeysCaught() % config.milestoneInterval() == 0)
			{
				notifier.notify("Maniacal Hunter milestone: " + session.getMonkeysCaught() + " monkeys caught!");
			}
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
			aggregateSession.setDuration(Duration.between(aggregateSession.getSessionStartTime(), Instant.now()));
		}
		mousePosition = client.getMouseCanvasPosition();
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
				aggregateSession.startSession(statChanged.getXp());
			}
			session.setXpGained(statChanged.getXp() - session.getStartXp());
			aggregateSession.setXpGained(statChanged.getXp() - aggregateSession.getStartXp());
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
			session.incrementTrapsLaid();
			aggregateSession.incrementTrapsLaid();
			session.setLastTrapStatus("Trap set");
			aggregateSession.setLastTrapStatus("Trap set");
		}
		else if (id == ManiacalHunterConstants.UNSET_BOULDER_TRAP)
		{
			session.setLastTrapStatus("Trap not set");
			aggregateSession.setLastTrapStatus("Trap not set");
		}
		else if (id == ManiacalHunterConstants.TRIGGERED_BOULDER_TRAP_1 || id == ManiacalHunterConstants.TRIGGERED_BOULDER_TRAP_2)
		{
			session.setLastTrapStatus("Trap triggered");
			aggregateSession.setLastTrapStatus("Trap triggered");
		}
		else if (id == ManiacalHunterConstants.CAUGHT_MONKEY_BOULDER_1 || id == ManiacalHunterConstants.CAUGHT_MONKEY_BOULDER_2)
		{
			session.setLastTrapStatus("Monkey caught");
			aggregateSession.setLastTrapStatus("Monkey caught");
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

	public ManiacalHunterSession getSession()
	{
		return session;
	}

	public ManiacalHunterSession getAggregateSession()
	{
		return aggregateSession;
	}

	public BufferedImage getIcon()
	{
		return icon;
	}

	public Point getMouseCanvasPosition()
	{
		return mousePosition;
	}

	public void setSession(ManiacalHunterSession session)
	{
		this.session = session;
	}

	public Notifier getNotifier()
	{
		return notifier;
	}

	public void setNotifier(Notifier notifier)
	{
		this.notifier = notifier;
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged event)
	{
		if (event.getGroup().equals(CONFIG_GROUP) && event.getKey().equals(RESET_BUTTON_KEY))
		{
			if (config.resetSession())
			{
				reset();
				configManager.setConfiguration(CONFIG_GROUP, RESET_BUTTON_KEY, false);
			}
		}
	}
}

package com.maniacalhunter;

import java.time.Duration;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManiacalHunterSession
{
	private Instant sessionStartTime;
	private Duration duration;
	private int startXp;
	private int xpGained;
	private int monkeysCaught;
	private int trapsLaid;
	private String lastTrapStatus;
	private int perfectTails;
	private int damagedTails;
	private int damagedTailsSincePerfect;

	public void startSession(int startXp)
	{
		this.sessionStartTime = Instant.now();
		this.startXp = startXp;
	}

	public void reset()
	{
		this.sessionStartTime = null;
		this.duration = null;
		this.startXp = 0;
		this.xpGained = 0;
		this.monkeysCaught = 0;
		this.trapsLaid = 0;
		this.lastTrapStatus = "N/A";
		this.perfectTails = 0;
		this.damagedTails = 0;
		this.damagedTailsSincePerfect = 0;
	}

	public void incrementMonkeysCaught()
	{
		this.monkeysCaught++;
	}

	public void incrementTrapsLaid()
	{
		this.trapsLaid++;
	}

	public void incrementPerfectTails()
	{
		this.perfectTails++;
		this.damagedTailsSincePerfect = 0;
	}

	/**
	 * Increments the number of damaged tails and the dryness.
	 * Dryness is considered the number of damaged tails since the last perfect tail.
	 */
	public void incrementDamagedTails()
	{
		this.damagedTails++;
		this.damagedTailsSincePerfect++;
	}

	public double getLuckPercentage()
	{
		if (damagedTailsSincePerfect == 0)
		{
			return 0;
		}
		// Formula for probability of getting at least one drop is 1 - (1 - 1/droprate)^kills
		return (1.0 - Math.pow(1.0 - (1.0 / 5000.0), damagedTailsSincePerfect)) * 100;
	}

	public double getSuccessRate()
	{
		if (trapsLaid == 0)
		{
			return 0;
		}
		return (double) monkeysCaught / trapsLaid * 100;
	}

	public double getMonkeysPerHour()
	{
		if (duration == null || duration.isZero())
		{
			return 0;
		}
		return (double) monkeysCaught / (duration.toMillis() / 3600000.0);
	}
}

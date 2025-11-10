package com.maniacalhunter;

import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.Instant;

public class ManiacalHunterSession
{
	private static final int PERFECT_TAIL_DROP_CHANCE = 5000;
	private static final double PERFECT_TAIL_DROP_RATE = 1.0 / PERFECT_TAIL_DROP_CHANCE;
	private static final double MILLIS_PER_HOUR = 3_600_000.0;
	private static final double MILLIS_PER_SECOND = 1_000.0;

    @Getter
	@Setter
    private long sessionStartTimeMillis;
	@Getter
	@Setter
    private long durationMillis;
	@Getter
	@Setter
    private int monkeysCaught;
	@Getter
	@Setter
    private int trapsLaid;
	@Getter
    @Setter
    private String lastTrapStatus;
	@Getter
	@Setter
    private int perfectTails;
	@Getter
	@Setter
    private int damagedTails;
	@Getter
	@Setter
	private int damagedTailsSincePerfect;

	public void startSession()
	{
		this.sessionStartTimeMillis = Instant.now().toEpochMilli();
	}

	public void reset()
	{
		this.sessionStartTimeMillis = 0;
		this.durationMillis = 0;
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
		// This calculates the probability of getting at least one perfect tail in N trials,
		// which is 1 minus the probability of getting zero perfect tails.
		return (1.0 - Math.pow(1.0 - PERFECT_TAIL_DROP_RATE, damagedTailsSincePerfect)) * 100;
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
		if (durationMillis == 0)
		{
			return 0;
		}
		return (double) monkeysCaught / (durationMillis / MILLIS_PER_HOUR);
	}

	public double getAverageTimePerCatch()
	{
		if (monkeysCaught == 0 || durationMillis == 0)
		{
			return 0;
		}
		return (double) durationMillis / monkeysCaught / MILLIS_PER_SECOND;
	}

	public Instant getSessionStartTime()
	{
		return sessionStartTimeMillis == 0 ? null : Instant.ofEpochMilli(sessionStartTimeMillis);
	}

	public Duration getDuration()
	{
		return durationMillis == 0 ? null : Duration.ofMillis(durationMillis);
	}

	public void setDuration(Duration duration)
	{
		this.durationMillis = duration.toMillis();
	}
}

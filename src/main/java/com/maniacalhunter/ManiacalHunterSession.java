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
    private Instant sessionStartTime;
	@Getter
	@Setter
    private Duration duration;
	@Getter
    private int monkeysCaught;
	@Getter
    private int trapsLaid;
	@Getter
    @Setter
    private String lastTrapStatus;
	@Getter
    private int perfectTails;
	@Getter
    private int damagedTails;
	@Getter
	private int damagedTailsSincePerfect;

	public void startSession()
	{
		this.sessionStartTime = Instant.now();
	}

	public void reset()
	{
		this.sessionStartTime = null;
		this.duration = null;
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
		if (duration == null || duration.isZero())
		{
			return 0;
		}
		return (double) monkeysCaught / (duration.toMillis() / MILLIS_PER_HOUR);
	}

	public double getAverageTimePerCatch()
	{
		if (monkeysCaught == 0 || duration == null || duration.isZero())
		{
			return 0;
		}
		return (double) duration.toMillis() / monkeysCaught / MILLIS_PER_SECOND;
	}

}

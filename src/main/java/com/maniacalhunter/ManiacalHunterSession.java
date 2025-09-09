package com.maniacalhunter;

import java.time.Duration;
import java.time.Instant;

public class ManiacalHunterSession
{
	private Instant sessionStartTime;
	private Duration duration;
	private int monkeysCaught;
	private int trapsLaid;
	private String lastTrapStatus;
	private int perfectTails;
	private int damagedTails;
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

	public double getAverageTimePerCatch()
	{
		if (monkeysCaught == 0 || duration == null || duration.isZero())
		{
			return 0;
		}
		return (double) duration.toMillis() / monkeysCaught / 1000.0;
	}

	// Getters
	public Instant getSessionStartTime() { return sessionStartTime; }
	public Duration getDuration() { return duration; }
	public int getMonkeysCaught() { return monkeysCaught; }
	public int getTrapsLaid() { return trapsLaid; }
	public String getLastTrapStatus() { return lastTrapStatus; }
	public int getPerfectTails() { return perfectTails; }
	public int getDamagedTails() { return damagedTails; }
	public int getDamagedTailsSincePerfect() { return damagedTailsSincePerfect; }

	// Setters
	public void setSessionStartTime(Instant sessionStartTime) { this.sessionStartTime = sessionStartTime; }
	public void setDuration(Duration duration) { this.duration = duration; }
	public void setMonkeysCaught(int monkeysCaught) { this.monkeysCaught = monkeysCaught; }
	public void setTrapsLaid(int trapsLaid) { this.trapsLaid = trapsLaid; }
	public void setLastTrapStatus(String lastTrapStatus) { this.lastTrapStatus = lastTrapStatus; }
	public void setPerfectTails(int perfectTails) { this.perfectTails = perfectTails; }
	public void setDamagedTails(int damagedTails) { this.damagedTails = damagedTails; }
	public void setDamagedTailsSincePerfect(int damagedTailsSincePerfect) { this.damagedTailsSincePerfect = damagedTailsSincePerfect; }
}

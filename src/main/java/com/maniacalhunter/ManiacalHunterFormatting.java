package com.maniacalhunter;

public final class ManiacalHunterFormatting
{
	private ManiacalHunterFormatting()
	{
	}

	public static String formatStat(int session, int aggregate, DisplayMode displayMode)
	{
		switch (displayMode)
		{
			case SESSION_ONLY:
				return String.valueOf(session);
			case AGGREGATE_ONLY:
				return String.valueOf(aggregate);
			case BOTH:
			default:
				return String.format("%d (%d)", session, aggregate);
		}
	}

	public static String formatPercentage(double session, double aggregate, DisplayMode displayMode)
	{
		switch (displayMode)
		{
			case SESSION_ONLY:
				return String.format("%.2f%%", session);
			case AGGREGATE_ONLY:
				return String.format("%.2f%%", aggregate);
			case BOTH:
			default:
				return String.format("%.2f%% (%.2f%%)", session, aggregate);
		}
	}

	public static String formatDouble(double session, double aggregate, DisplayMode displayMode)
	{
		switch (displayMode)
		{
			case SESSION_ONLY:
				return String.format("%.2f", session);
			case AGGREGATE_ONLY:
				return String.format("%.2f", aggregate);
			case BOTH:
			default:
				return String.format("%.2f (%.2f)", session, aggregate);
		}
	}
}
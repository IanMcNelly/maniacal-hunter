package com.maniacalhunter;

import com.google.common.collect.ImmutableSet;
import java.util.Set;

public class ManiacalHunterConstants
{
	// Item IDs
	public static final int DAMAGED_MONKEY_TAIL = 19665;
	public static final int PERFECT_MONKEY_TAIL = 19610;

	// Object IDs
	public static final int UNSET_BOULDER_TRAP = 28824;
	public static final int SETTING_BOULDER_TRAP = 28825;
	public static final int SET_BOULDER_TRAP = 28827;
	public static final int TRIGGERED_BOULDER_TRAP_1 = 28828;
	public static final int TRIGGERED_BOULDER_TRAP_2 = 28829;
	public static final int CAUGHT_MONKEY_BOULDER_1 = 28830;
	public static final int CAUGHT_MONKEY_BOULDER_2 = 28831;
	public static final int LIFTED_BOULDER = 28833;

	// Region IDs
	public static final int MANIACAL_HUNTER_REGION = 11662;

	public static final Set<Integer> BOULDER_TRAP_IDS = ImmutableSet.of(
		UNSET_BOULDER_TRAP,
		SETTING_BOULDER_TRAP,
		SET_BOULDER_TRAP,
		TRIGGERED_BOULDER_TRAP_1,
		TRIGGERED_BOULDER_TRAP_2,
		CAUGHT_MONKEY_BOULDER_1,
		CAUGHT_MONKEY_BOULDER_2,
		LIFTED_BOULDER
	);
}

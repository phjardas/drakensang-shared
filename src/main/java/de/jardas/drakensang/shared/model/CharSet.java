package de.jardas.drakensang.shared.model;

import java.util.Collection;
import java.util.HashSet;

import org.apache.commons.lang.ArrayUtils;

public enum CharSet implements Identified {
	archetype_mi_scharlatan(false, Sex.female, Sex.male), archetype_mi_kampfmagier(
			false, Sex.female, Sex.male), archetype_mi_taschendieb(false,
			Sex.female, Sex.male), archetype_mi_soldat(false, Sex.female,
			Sex.male), archetype_mi_krieger(false, Sex.female, Sex.male), archetype_mi_einbrecher(
			false, Sex.female, Sex.male), archetype_mi_streuner(false,
			Sex.female, Sex.male), archetype_mi_bogenschuetze(false,
			Sex.female, Sex.male), archetype_mi_heilmagier(false, Sex.female,
			Sex.male), archetype_el_zauberweber(false, Sex.female, Sex.male), archetype_el_kaempfer(
			false, Sex.female, Sex.male), archetype_el_waldlaeufer(false,
			Sex.female, Sex.male), archetype_tu_alchemist(false, Sex.female,
			Sex.male), archetype_tu_antimagier(false, Sex.female, Sex.male), archetype_tu_elementarist(
			false, Sex.female, Sex.male), archetype_tw_pirat(false, Sex.female,
			Sex.male),

	// male only
	archetype_gj_kraeftig(false, Sex.male), archetype_gj_schmal(false, Sex.male), archetype_gj_barbar(
			false, Sex.male), archetype_zw_soeldner(true, Sex.male), archetype_zw_sappeur(
			true, Sex.male), archetype_zw_prospektor(true, Sex.male), archetype_zw_geode(
			true, Sex.male),

	// female only
	archetype_tw_pirat_schlank(false, Sex.female), archetype_tw_pirat_kraeftig(
			false, Sex.female), archetype_tw_stammeskrieger(false, Sex.female), archetype_tw_stammeskrieger_kraeftig(
			false, Sex.female), archetype_am_krieger(false, Sex.female), archetype_am_krieger_klein(
			false, Sex.female), archetype_am_krieger_normal(false, Sex.female), ;

	private final boolean dwarvish;
	private final Sex[] sexes;

	private CharSet(boolean dwarvish, Sex... sexes) {
		this.sexes = sexes;
		this.dwarvish = dwarvish;
	}

	public static CharSet[] values(Sex sex, Race race) {
		final Collection<CharSet> ret = new HashSet<CharSet>();

		for (CharSet hair : values()) {
			if (ArrayUtils.contains(hair.getSexes(), sex)
					&& ((race == Race.Zwerg) == hair.isDwarvish())) {
				ret.add(hair);
			}
		}

		return ret.toArray(new CharSet[ret.size()]);
	}

	public String getId() {
		return name();
	}

	public Sex[] getSexes() {
		return sexes;
	}

	public boolean isDwarvish() {
		return dwarvish;
	}
}

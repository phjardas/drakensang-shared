package de.jardas.drakensang.shared.model;

import java.util.Collection;
import java.util.HashSet;

import org.apache.commons.lang.ArrayUtils;

public enum Hair implements Identified {
	haare_kit_thorwaler_voll(false, Sex.male), haare_streuner_voll(false,
			Sex.male), haare_kit_thorwaler_kurzhaar_offen_voll(false, Sex.male), haare_kit_thorwaler_zopf_voll(
			false, Sex.male), haare_kit_elfen_rot_zoepfe_voll(false, Sex.male), haare_kit_elfen_blond_voll(
			false, Sex.male), haare_kit_taschendieb_voll(false, Sex.male), haare_glatze_voll(
			false, Sex.male), haare_kit_thorwaler_haare_offen_stirnband_voll(
			false, Sex.male), haare_kit_thorwaler_schnauzer_voll(false,
			Sex.male), haare_kit_tulamide_alchemist_voll(false, Sex.male), haare_kit_khan_zopf_voll(
			false, Sex.male), haare_kit_thorwaler_kurzbart_zopf_voll(false,
			Sex.male), haare_kit_elfen_braun_zopf_voll(false, Sex.male), haare_kit_thorwaler_langbart_locken_voll(
			false, Sex.male), haare_garether_01_voll(false, Sex.male), haare_horasischer_einbrecher_voll(
			false, Sex.male), haare_kit_thorwaler_kurzbart_locken_voll(false,
			Sex.male), haare_kit_elfen_longhair_darkbrown_pearls_voll(false,
			Sex.male), haare_kit_tulamide_antimagier_voll(false, Sex.male), haare_kit_thorwaler_langhaar_offen_voll(
			false, Sex.male), haare_scharlatan_voll(false, Sex.male), haare_kit_elfen_blau_voll(
			false, Sex.male),

	haare_kit_zwerge_soeldner_voll(true, Sex.male), haare_kit_zwerge_arom_voll(
			true, Sex.male), haare_kit_zwerge_sappeur_voll(true, Sex.male), haare_kit_zwerge_geode_voll(
			true, Sex.male), haare_kit_zwerge_prospektor_voll(true, Sex.male),

	haare_kit_scharlatanin_voll(false, Sex.female), haare_kit_gwendala_voll(
			false, Sex.female), haare_kit_thorwalerin_voll(false, Sex.female), haare_kit_glatze_voll(
			false, Sex.female), haare_kit_tulamide_antimagierin_voll(false,
			Sex.female), haare_kit_elfe_blond_ponytail_voll(false, Sex.female), haare_kit_tulamidin_01_voll(
			false, Sex.female), haare_kit_taschendiebin_voll(false, Sex.female), haare_kit_elfe_blond_zoepfe_voll(
			false, Sex.female), haare_kit_elfe_mittel_graublau_voll(false,
			Sex.female), haare_kit_thorwalerin_haare_zopf_voll(false,
			Sex.female), haare_kit_amazonen_amazone_voll(false, Sex.female), haare_kit_thorwalerin_locken_lang_voll(
			false, Sex.female), haare_kit_horasische_einbrecherin_voll(false,
			Sex.female), haare_kit_elfe_lang_perlen_voll(false, Sex.female), haare_kit_tulamidischer_beatle_voll(
			false, Sex.female), haare_kit_amazonen_cleopatra_voll(false,
			Sex.female), haare_kit_amazonen_magierin_typ1_voll(false,
			Sex.female), haare_kit_amazonen_gwendala_voll(false, Sex.female), haare_kit_amazonen_lang_braun_voll(
			false, Sex.female), haare_kit_soldatin_voll(false, Sex.female), haare_kit_amazone_voll(
			false, Sex.female), haare_kit_thorwalerin_locken_kurz_voll(false,
			Sex.female), haare_kit_elfe_blond_weit_zoepfe_voll(false,
			Sex.female),

	haare_kit_gjals_dreads_gebunden_voll(false, Sex.female, Sex.male), haare_kit_gjals_dreads_zopf_voll(
			false, Sex.female, Sex.male), haare_kit_gjals_dreads_lang_voll(
			false, Sex.female, Sex.male), ;

	private final boolean dwarvish;
	private final Sex[] sexes;

	private Hair(boolean dwarvish, Sex... sexes) {
		this.sexes = sexes;
		this.dwarvish = dwarvish;
	}

	public static Hair[] values(Sex sex, Race race) {
		final Collection<Hair> ret = new HashSet<Hair>();

		for (Hair hair : values()) {
			if (ArrayUtils.contains(hair.getSexes(), sex)
					&& ((race == Race.Zwerg) == hair.isDwarvish())) {
				ret.add(hair);
			}
		}

		return ret.toArray(new Hair[ret.size()]);
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

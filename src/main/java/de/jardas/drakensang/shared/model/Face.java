package de.jardas.drakensang.shared.model;

import java.util.Collection;
import java.util.HashSet;

public enum Face implements Identified {
	kopf_kit_elf_zauberweber(false, Sex.male), kopf_kit_garethischer_krieger(
			false, Sex.male), kopf_kit_tulamidischer_elementarist(false,
			Sex.male), kopf_kit_tulamide_alchemist(false, Sex.male), kopf_kit_tulamide_antimagier(
			false, Sex.male), kopf_kit_streuner(false, Sex.male), kopf_kit_thorwaler_schmal(
			false, Sex.male), kopf_kit_thorwaler_kraeftig(false, Sex.male), kopf_kit_elf_waldlaeufer(
			false, Sex.male), kopf_kit_thorwaler_normal(false, Sex.male), kopf_kit_elf_kaempfer(
			false, Sex.male), kopf_kit_horasischer_einbrecher(false, Sex.male), kopf_kit_zwerge_soeldner(
			true, Sex.male), kopf_kit_zwerge_prospektor(true, Sex.male), kopf_kit_zwerge_normal(
			true, Sex.male), kopf_kit_amazone_schlank(false, Sex.female), kopf_kit_tulamidische_elementaristin(
			false, Sex.female), kopf_kit_thorwalerin_schlank(false, Sex.female), kopf_kit_thorwalerin_normal(
			false, Sex.female), kopf_kit_elfe_waldlaeufer(false, Sex.female), kopf_kit_thorwalerin_kraeftig(
			false, Sex.female), kopf_kit_elfe_elfenaelteste(false, Sex.female), kopf_kit_tulamide_alchemistin(
			false, Sex.female), kopf_mittellaender_bogenschuetzin(false,
			Sex.female), kopf_taschendiebin(false, Sex.female), kopf_amazone(
			false, Sex.female), kopf_kit_elfe_zauberweber(false, Sex.female), kopf_kit_amazone_normal(
			false, Sex.female), kopf_kit_tulamide_antimagierin(false,
			Sex.female), kopf_kit_amazone_kraeftig(false, Sex.female), ;

	private final boolean dwarvish;
	private final Sex sex;

	private Face(boolean dwarvish, Sex sex) {
		this.sex = sex;
		this.dwarvish = dwarvish;
	}

	public static Face[] values(Sex sex, Race race) {
		final Collection<Face> ret = new HashSet<Face>();

		for (Face face : values()) {
			if (sex == face.getSex()
					&& ((race == Race.Zwerg) == face.isDwarvish())) {
				ret.add(face);
			}
		}

		return ret.toArray(new Face[ret.size()]);
	}

	public String getId() {
		return name();
	}

	public Sex getSex() {
		return sex;
	}

	public boolean isDwarvish() {
		return dwarvish;
	}
}

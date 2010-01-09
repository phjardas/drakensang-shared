package de.jardas.drakensang.shared.model;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;

import de.jardas.drakensang.shared.db.Messages;
import de.jardas.drakensang.shared.db.Static;

public enum Advantage implements Identified {
	advantage_dolche,
	advantage_fechtwaffen, advantage_hiebwaffen,
	advantage_saebel, advantage_schwerter, advantage_speere,
	advantage_staebe, advantage_zwhiebwaffen,
	advantage_zwschwerter, advantage_raufen, advantage_nahkampf,
	advantage_armbrust, advantage_bogen, advantage_wurfwaffen,
	advantage_fernkampf, advantage_schleichen,
	advantage_selbstbeherrschung, advantage_sinnenschaerfe,
	advantage_taschendiebstahl, advantage_zwergennase,
	advantage_koerper, advantage_fallenstellen,
	advantage_wildnisleben, advantage_pflanzenkunde,
	advantage_tierkunde, advantage_natur, advantage_magiekunde,
	advantage_heilkundewunden, advantage_heilkundegift,
	advantage_gassenwissen, advantage_wissen, advantage_alchimie,
	advantage_bogenbau, advantage_schmieden,
	advantage_schloesser, advantage_fallenentschaerfen,
	advantage_handwerk, advantage_betoeren, advantage_ueberreden,
	advantage_feilschen, advantage_menschenkenntnis,
	advantage_etikette, disadvantage_dolche,
	disadvantage_fechtwaffen, disadvantage_hiebwaffen,
	disadvantage_saebel, disadvantage_schwerter,
	disadvantage_speere, disadvantage_staebe,
	disadvantage_zwhiebwaffen, disadvantage_zwschwerter,
	disadvantage_raufen, disadvantage_nahkampf,
	disadvantage_armbrust, disadvantage_bogen,
	disadvantage_wurfwaffen, disadvantage_fernkampf,
	disadvantage_schleichen, disadvantage_selbstbeherrschung,
	disadvantage_sinnenschaerfe, disadvantage_taschendiebstahl,
	disadvantage_zwergennase, disadvantage_koerper,
	disadvantage_fallenstellen, disadvantage_wildnisleben,
	disadvantage_pflanzenkunde, disadvantage_tierkunde,
	disadvantage_natur, disadvantage_magiekunde,
	disadvantage_heilkundewunden, disadvantage_heilkundegift,
	disadvantage_gassenwissen, disadvantage_wissen,
	disadvantage_alchimie, disadvantage_bogenbau,
	disadvantage_schmieden, disadvantage_schloesser,
	disadvantage_fallenentschaerfen, disadvantage_handwerk,
	disadvantage_betoeren, disadvantage_ueberreden,
	disadvantage_feilschen, disadvantage_menschenkenntnis,
	disadvantage_etikette, disadvantage_gesellschaft,
	advantage_aussehen, advantage_herausragende_sinne,
	disadvantage_eingeschraenkte_sinne, advantage_le_bonus,
	advantage_ae_bonus, advantage_au_bonus,
	disadvantage_le_malus, disadvantage_ae_malus,
	disadvantage_au_malus, advantage_magieresistenz,
	disadvantage_niedrige_magieresistenz,
	advantage_schnelle_heilung, advantage_astrale_regeneration,
	advantage_ausdauernd, disadvantage_langsame_heilung,
	disadvantage_kurzatmig, disadvantage_elfische_weltsicht,
	disadvantage_weltfremd, advantage_gefahreninstinkt,
	advantage_soziale_anpassungsfaehigkeit,
	advantage_entfernungssinn, advantage_kraeftig,
	disadvantage_schwach, advantage_intelligent,
	disadvantage_dumm, advantage_mutig, disadvantage_feigling,
	advantage_intuitiv, disadvantage_unsensibel,
	disadvantage_abstossend, advantage_geschickt,
	disadvantage_ungeschickt, advantage_katzengleich,
	disadvantage_trampel, advantage_zaeh, disadvantage_zart,
	advantage_ausruestungsbonus,
	;
	
	private final String nameKey;
	private final String infoKey;
	private final Effect[] effects;

	private Advantage() {
		this.nameKey = Static.get("Name", name(), "Id", "_Template_Advantages");
		this.infoKey = Static.get("Description", name(), "Id",
				"_Template_Advantages");
		this.effects = loadEffects(name());
	}

	private static Effect[] loadEffects(String name) {
		try {
			final String[] tokens = Static.get("AttributeModifier", name, "Id",
					"_Template_Advantages").trim().split("\\s*;\\s*");
			List<Effect> effects = new ArrayList<Effect>(tokens.length);

			for (String token : tokens) {
				if (token.trim().length() == 0) {
					continue;
				}

				final String[] nameAndMod = token.split(":");

				String targetName = nameAndMod[0];
				EffectTarget targetType = EffectTarget
						.getTargetType(targetName);
				int modifier = Integer
						.valueOf(nameAndMod[1].startsWith("+") ? nameAndMod[1]
								.substring(1) : nameAndMod[1]);
				effects.add(new Effect(targetType, targetName, modifier));
			}

			return effects.toArray(new Effect[effects.size()]);
		} catch (MissingResourceException e) {
			System.err.println("Error loading effects for advantage " + name
					+ ": " + e);

			return new Effect[0];
		}
	}

	public String getNameKey() {
		return nameKey;
	}

	public String getInfoKey() {
		return infoKey;
	}

	public boolean isUnknownModification() {
		try {
			Messages.get(getNameKey());

			return false;
		} catch (MissingResourceException e) {
			return true;
		}
	}

	public String getName() {
		try {
			return Messages.get(getNameKey());
		} catch (MissingResourceException e) {
			final String pattern = Messages.get("advantage.unknown");

			return MessageFormat.format(pattern, name());
		}
	}

	public String getInfo() {
		try {
			return Messages.get(getInfoKey());
		} catch (MissingResourceException e) {
			return null;
		}
	}
	
	public String getId() {
		return name();
	}

	public Effect[] getEffects() {
		return this.effects;
	}
}

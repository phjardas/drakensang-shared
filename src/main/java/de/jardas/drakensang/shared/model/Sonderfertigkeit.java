package de.jardas.drakensang.shared.model;

public enum Sonderfertigkeit implements Identified {
	SF_Ausweichen1, SF_Ausweichen2,
	SF_Ausweichen3, SF_Ausdauernd1, SF_Ausdauernd2,
	SF_Ausdauernd3, SF_Ruestungsgewoehnung1,
	SF_Ruestungsgewoehnung2, SF_Ruestungsgewoehnung3,
	SF_Schildkampf1, SF_Schildkampf2, SF_Schildkampf3,
	SF_DefensiverKampfstil1, SF_DefensiverKampfstil2,
	SF_DefensiverKampfstil3, SF_OffensiverKampfstil1,
	SF_OffensiverKampfstil2, SF_OffensiverKampfstil3,
	SF_Meisterparade, SF_Klingenwand, SF_Windmuehle,
	SF_Finte, SF_GezielterStich, SF_Todesstoss,
	SF_Klingensturm, SF_Umreissen, SF_Wuchtschlag,
	SF_Niederwerfen, SF_Befreiungsschlag, SF_Hammerschlag,
	SF_Spott, SF_Raserei, SF_Blutung, SF_GezielterSchuss,
	SF_Scharfschuetze, SF_Meisterschuetze, SF_Eisenhagel,
	SF_Pfeilhagel, SF_Lademeister, SF_GezielterWurf,
	SF_Kraftwurf, SF_Meisterwurf,
	;
	
	public String getId() {
		return name();
	}
}

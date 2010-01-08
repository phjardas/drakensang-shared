package de.jardas.drakensang.shared.model;

import java.util.Comparator;

public interface Identified {
	Comparator<? super Identified> COMPARATOR_ASC = new Comparator<Identified>() {
		public int compare(Identified o1, Identified o2) {
			return o1.getId().compareToIgnoreCase(o2.getId());
		};
	};

	String getId();
}

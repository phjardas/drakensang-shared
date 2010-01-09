package de.jardas.drakensang.shared.model;

import java.util.HashSet;
import java.util.Set;

public class Archetype extends Person {
	private ArchetypeId archetypeId;
	private String icon;
	private final Set<Face> selectableFaces = new HashSet<Face>();
	private final Set<Hair> selectableHairs = new HashSet<Hair>();
	private final Set<CharSet> selectableCharSets = new HashSet<CharSet>();
	private String backgroundInfo;

	public Archetype() {
		super(false, false);
	}

	public ArchetypeId getArchetypeId() {
		return archetypeId;
	}

	public void setArchetypeId(ArchetypeId archetypeId) {
		firePropertyChange("archetypeId", this.archetypeId,
				this.archetypeId = archetypeId);
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		firePropertyChange("icon", this.icon, this.icon = icon);
	}

	public Set<Face> getSelectableFaces() {
		return selectableFaces;
	}

	public Set<Hair> getSelectableHairs() {
		return selectableHairs;
	}

	public Set<CharSet> getSelectableCharSets() {
		return selectableCharSets;
	}

	public String getBackgroundInfo() {
		return backgroundInfo;
	}

	public void setBackgroundInfo(String backgroundInfo) {
		firePropertyChange("backgroundInfo", this.backgroundInfo,
				this.backgroundInfo = backgroundInfo);
	}
}

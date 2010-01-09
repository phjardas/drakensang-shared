package de.jardas.drakensang.shared.model;

import java.util.HashSet;
import java.util.Set;

public class Archetype extends Person {
	private ArchetypeId archetypeId;
	private String icon;
	private Set<Face> selectableFaces = new HashSet<Face>();
	private Set<Hair> selectableHairs = new HashSet<Hair>();
	private Set<CharSet> selectableCharSets = new HashSet<CharSet>();
	private String backgroundInfo;

	public Archetype() {
		super(false, false);
	}

	public ArchetypeId getArchetypeId() {
		return archetypeId;
	}

	public void setArchetypeId(ArchetypeId archetypeId) {
		this.archetypeId = archetypeId;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Set<Face> getSelectableFaces() {
		return selectableFaces;
	}

	public void setSelectableFaces(Set<Face> selectableFaces) {
		this.selectableFaces = selectableFaces;
	}

	public Set<Hair> getSelectableHairs() {
		return selectableHairs;
	}

	public void setSelectableHairs(Set<Hair> selectableHairs) {
		this.selectableHairs = selectableHairs;
	}

	public Set<CharSet> getSelectableCharSets() {
		return selectableCharSets;
	}

	public void setSelectableCharSets(Set<CharSet> selectableCharSets) {
		this.selectableCharSets = selectableCharSets;
	}

	public String getBackgroundInfo() {
		return backgroundInfo;
	}

	public void setBackgroundInfo(String backgroundInfo) {
		this.backgroundInfo = backgroundInfo;
	}
}

package de.jardas.drakensang.shared.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class ArchetypeId implements Identified {
	private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory
			.getLogger(ArchetypeId.class);
	private static final Map<String, ArchetypeId> VALUES = new HashMap<String, ArchetypeId>();

	private String id;

	private ArchetypeId(String id) {
		super();
		this.id = id;
	}

	public static ArchetypeId forId(String id) {
		ArchetypeId item = VALUES.get(id);

		if (item == null) {
			item = create(id);
			VALUES.put(id, item);
		}

		return item;
	}

	public static Collection<ArchetypeId> values() {
		return VALUES.values();
	}

	private static ArchetypeId create(String id) {
		LOG.debug("New ArchetypeId: {}", id);
		return new ArchetypeId(id);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof ArchetypeId))
			return false;
		final ArchetypeId a = (ArchetypeId) obj;
		return new EqualsBuilder().append(getId(), a.getId()).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	@Override
	public String toString() {
		return getId();
	}
}

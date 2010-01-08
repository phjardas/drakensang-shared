package de.jardas.drakensang.shared.model.inventory;

public abstract class EquipableItem extends InventoryItem {
	private EquipmentType equipmentType;
	private EquipmentSlot slot;

	public EquipableItem() {
		super();
	}

	public EquipableItem(boolean countable) {
		super(countable);
	}

	public EquipmentSlot getSlot() {
		return slot;
	}

	public void setSlot(EquipmentSlot slot) {
		this.slot = slot;
	}

	public EquipmentType getEquipmentType() {
		return equipmentType;
	}

	public void setEquipmentType(EquipmentType equipmentType) {
		this.equipmentType = equipmentType;
	}
}

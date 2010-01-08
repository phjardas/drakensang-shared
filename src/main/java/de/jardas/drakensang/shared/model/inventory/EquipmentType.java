package de.jardas.drakensang.shared.model.inventory;


public enum EquipmentType {
	TwoHandWeapon(new EquipmentSlot[] { EquipmentSlot.LeftHand,EquipmentSlot.RightHand, }),
	OneHandWeapon(new EquipmentSlot[] { EquipmentSlot.RightHand, }),
	LeftHandWeapon(new EquipmentSlot[] { EquipmentSlot.LeftHand, }),
	Shield(new EquipmentSlot[] { EquipmentSlot.LeftHand, }),
	HeadArmor(new EquipmentSlot[] { EquipmentSlot.Head, }),
	HeadWithShoulder(new EquipmentSlot[] { EquipmentSlot.Head,EquipmentSlot.Shoulder, }),
	Shirt(new EquipmentSlot[] { EquipmentSlot.Shirt, }),
	ChestArmor(new EquipmentSlot[] { EquipmentSlot.Body,EquipmentSlot.Shoulder, }),
	PlateArmor(new EquipmentSlot[] { EquipmentSlot.Body_Plate, }),
	ArmArmor(new EquipmentSlot[] { EquipmentSlot.ArmArmor, }),
	ArmArmorWithShoulder(new EquipmentSlot[] { EquipmentSlot.ArmArmor,EquipmentSlot.Shoulder, }),
	LegArmor(new EquipmentSlot[] { EquipmentSlot.LegArmor, }),
	FullBodyArmor(new EquipmentSlot[] { EquipmentSlot.Body,EquipmentSlot.ArmArmor,EquipmentSlot.LegArmor,EquipmentSlot.Shoulder,EquipmentSlot.Head,EquipmentSlot.Shoes,EquipmentSlot.Gloves,EquipmentSlot.Trousers, }),
	Belt(new EquipmentSlot[] { EquipmentSlot.Belt, }),
	Shoes(new EquipmentSlot[] { EquipmentSlot.Shoes, }),
	Trousers(new EquipmentSlot[] { EquipmentSlot.Trousers, }),
	Amulett(new EquipmentSlot[] { EquipmentSlot.Neck, }),
	Ring(new EquipmentSlot[] { EquipmentSlot.Ring, }),
	Ammo(new EquipmentSlot[] { EquipmentSlot.Quiver, }),
	Gloves(new EquipmentSlot[] { EquipmentSlot.Gloves, }),
	Shoulder(new EquipmentSlot[] { EquipmentSlot.Shoulder, }),
	Knee(new EquipmentSlot[] { EquipmentSlot.Knee, }),
	Decoration(new EquipmentSlot[] { EquipmentSlot.Decoration, }),
	LowerBody(new EquipmentSlot[] { EquipmentSlot.Shoes,EquipmentSlot.Trousers, }),
	TwoHandWeaponLeft(new EquipmentSlot[] { EquipmentSlot.RightHand,EquipmentSlot.LeftHand, }),
	GlovesWithArmArmor(new EquipmentSlot[] { EquipmentSlot.Gloves,EquipmentSlot.ArmArmor, }),
	TrousersWithLegArmor(new EquipmentSlot[] { EquipmentSlot.Trousers,EquipmentSlot.LegArmor, }),
	ChestArmorWithoutShoulder(new EquipmentSlot[] { EquipmentSlot.Body, }),
	ChestArmorWithHead(new EquipmentSlot[] { EquipmentSlot.Body,EquipmentSlot.Shoulder,EquipmentSlot.Head, }),
	ShoesWithLegArmor(new EquipmentSlot[] { EquipmentSlot.Shoes,EquipmentSlot.LegArmor, }),
	ChestArmsLegs(new EquipmentSlot[] { EquipmentSlot.Body,EquipmentSlot.Shoulder,EquipmentSlot.ArmArmor,EquipmentSlot.LegArmor,EquipmentSlot.Trousers, }),
	BodyShoulderArms(new EquipmentSlot[] { EquipmentSlot.Body,EquipmentSlot.Shoulder,EquipmentSlot.ArmArmor, }),
	BodyArms(new EquipmentSlot[] { EquipmentSlot.Body,EquipmentSlot.ArmArmor, }),
	BodyLegs(new EquipmentSlot[] { EquipmentSlot.Body,EquipmentSlot.LegArmor, }),
	LongBodyShoulderArms(new EquipmentSlot[] { EquipmentSlot.Body,EquipmentSlot.Shoulder,EquipmentSlot.ArmArmor,EquipmentSlot.LegArmor, }),
	;
	
	private final EquipmentSlot[] slots;

	private EquipmentType(EquipmentSlot[] slots) {
		this.slots = slots;
	}

	public EquipmentSlot[] getSlots() {
		return slots;
	}
}

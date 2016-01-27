package main.astraeus.game.model;

public class Hit {

	public enum HitType {
		BLOCKED(0), NORMAL(1), POISON(2), VENOM(3);

		private int id;

		private HitType(int id) {
			this.id = id;
		}

		public int getId() {
			return id;
		}
	}

	private int damage;

	private HitType type;

	public Hit(int damage) {
		this(damage, HitType.NORMAL);
	}

	public Hit(int damage, HitType type) {
		this.damage = damage;
		this.type = type;
		if (this.damage == 0 && this.type == HitType.NORMAL) {
			this.type = HitType.BLOCKED;
		} else if (this.damage > 0 && this.type == HitType.BLOCKED) {
			this.damage = 0;
		} else if (this.damage < 0) {
			this.damage = 0;
		}
	}

	@Override
	public Hit clone() {
		return new Hit(damage, type);
	}

	@Override
	public boolean equals(Object other) {
		if (other == null || other.getClass() != Hit.class) {
			return false;
		}

		final Hit hit = (Hit) other;
		return hit.damage == damage && hit.type == type;
	}

	public int getDamage() {
		return damage;
	}

	public HitType getType() {
		return type;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	@Override
	public String toString() {
		return "[HIT] - Damage: " + getDamage() + " Type: " + getType().name();
	}

}

package net.minecraft.src;

class HashEntry {
	final int field_841_a;
	Object field_840_b;
	HashEntry field_843_c;
	final int field_842_d;

	HashEntry(int var1, int var2, Object var3, HashEntry var4) {
		this.field_840_b = var3;
		this.field_843_c = var4;
		this.field_841_a = var2;
		this.field_842_d = var1;
	}

	public final int func_559_a() {
		return this.field_841_a;
	}

	public final Object func_558_b() {
		return this.field_840_b;
	}

	public final boolean equals(Object var1) {
		if(!(var1 instanceof HashEntry)) {
			return false;
		} else {
			HashEntry var2 = (HashEntry)var1;
			Integer var3 = Integer.valueOf(this.func_559_a());
			Integer var4 = Integer.valueOf(var2.func_559_a());
			if(var3 == var4 || var3 != null && var3.equals(var4)) {
				Object var5 = this.func_558_b();
				Object var6 = var2.func_558_b();
				if(var5 == var6 || var5 != null && var5.equals(var6)) {
					return true;
				}
			}

			return false;
		}
	}

	public final int hashCode() {
		return MCHashTable.getHash(this.field_841_a);
	}

	public final String toString() {
		return this.func_559_a() + "=" + this.func_558_b();
	}
}

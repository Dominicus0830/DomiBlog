package net.minecraft.src;

public class TileEntityMobSpawner extends TileEntity {
	public int delay = -1;
	public String entityID = "Pig";
	public double field_491_g;
	public double field_490_h = 0.0D;

	public TileEntityMobSpawner() {
		this.delay = 20;
	}

	public boolean func_195_a() {
		return this.worldObj.getClosestPlayer((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D, 16.0D) != null;
	}

	public void updateEntity() {
		this.field_490_h = this.field_491_g;
		if(this.func_195_a()) {
			double var1 = (double)((float)this.xCoord + this.worldObj.rand.nextFloat());
			double var3 = (double)((float)this.yCoord + this.worldObj.rand.nextFloat());
			double var5 = (double)((float)this.zCoord + this.worldObj.rand.nextFloat());
			this.worldObj.spawnParticle("smoke", var1, var3, var5, 0.0D, 0.0D, 0.0D);
			this.worldObj.spawnParticle("flame", var1, var3, var5, 0.0D, 0.0D, 0.0D);

			for(this.field_491_g += (double)(1000.0F / ((float)this.delay + 200.0F)); this.field_491_g > 360.0D; this.field_490_h -= 360.0D) {
				this.field_491_g -= 360.0D;
			}

			if(this.delay == -1) {
				this.updateDelay();
			}

			if(this.delay > 0) {
				--this.delay;
			} else {
				byte var7 = 4;

				for(int var8 = 0; var8 < var7; ++var8) {
					EntityLiving var9 = (EntityLiving)((EntityLiving)EntityList.func_567_a(this.entityID, this.worldObj));
					if(var9 == null) {
						return;
					}

					int var10 = this.worldObj.getEntitiesWithinAABB(var9.getClass(), AxisAlignedBB.getBoundingBoxFromPool((double)this.xCoord, (double)this.yCoord, (double)this.zCoord, (double)(this.xCoord + 1), (double)(this.yCoord + 1), (double)(this.zCoord + 1)).expands(8.0D, 4.0D, 8.0D)).size();
					if(var10 >= 6) {
						this.updateDelay();
						return;
					}

					if(var9 != null) {
						double var11 = (double)this.xCoord + (this.worldObj.rand.nextDouble() - this.worldObj.rand.nextDouble()) * 4.0D;
						double var13 = (double)(this.yCoord + this.worldObj.rand.nextInt(3) - 1);
						double var15 = (double)this.zCoord + (this.worldObj.rand.nextDouble() - this.worldObj.rand.nextDouble()) * 4.0D;
						var9.func_107_c(var11, var13, var15, this.worldObj.rand.nextFloat() * 360.0F, 0.0F);
						if(var9.getCanSpawnHere()) {
							this.worldObj.entityJoinedWorld(var9);

							for(int var17 = 0; var17 < 20; ++var17) {
								var1 = (double)this.xCoord + 0.5D + ((double)this.worldObj.rand.nextFloat() - 0.5D) * 2.0D;
								var3 = (double)this.yCoord + 0.5D + ((double)this.worldObj.rand.nextFloat() - 0.5D) * 2.0D;
								var5 = (double)this.zCoord + 0.5D + ((double)this.worldObj.rand.nextFloat() - 0.5D) * 2.0D;
								this.worldObj.spawnParticle("smoke", var1, var3, var5, 0.0D, 0.0D, 0.0D);
								this.worldObj.spawnParticle("flame", var1, var3, var5, 0.0D, 0.0D, 0.0D);
							}

							var9.func_156_D();
							this.updateDelay();
						}
					}
				}

				super.updateEntity();
			}
		}
	}

	private void updateDelay() {
		this.delay = 200 + this.worldObj.rand.nextInt(600);
	}

	public void readFromNBT(NBTTagCompound var1) {
		super.readFromNBT(var1);
		this.entityID = var1.getString("EntityId");
		this.delay = var1.getShort("Delay");
	}

	public void writeToNBT(NBTTagCompound var1) {
		super.writeToNBT(var1);
		var1.setString("EntityId", this.entityID);
		var1.setShort("Delay", (short)this.delay);
	}
}

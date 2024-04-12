package net.minecraft.src;

import java.util.List;

public class EntityMinecart extends Entity implements IInventory {
	private ItemStack[] cargoItems;
	public int field_9168_a;
	public int field_9167_b;
	public int field_477_ad;
	private boolean field_469_aj;
	public int minecartType;
	public int field_9165_e;
	public double field_9164_f;
	public double field_9166_aj;
	private static final int[][][] field_468_ak = new int[][][]{{{0, 0, -1}, {0, 0, 1}}, {{-1, 0, 0}, {1, 0, 0}}, {{-1, -1, 0}, {1, 0, 0}}, {{-1, 0, 0}, {1, -1, 0}}, {{0, 0, -1}, {0, -1, 1}}, {{0, -1, -1}, {0, 0, 1}}, {{0, 0, 1}, {1, 0, 0}}, {{0, 0, 1}, {-1, 0, 0}}, {{0, 0, -1}, {-1, 0, 0}}, {{0, 0, -1}, {1, 0, 0}}};
	private int field_9163_an;
	private double field_9162_ao;
	private double field_9161_ap;
	private double field_9160_aq;
	private double field_9159_ar;
	private double field_9158_as;

	public EntityMinecart(World var1) {
		super(var1);
		this.cargoItems = new ItemStack[36];
		this.field_9168_a = 0;
		this.field_9167_b = 0;
		this.field_477_ad = 1;
		this.field_469_aj = false;
		this.field_329_e = true;
		this.setSize(0.98F, 0.7F);
		this.yOffset = this.height / 2.0F;
		this.entityWalks = false;
	}

	public AxisAlignedBB func_89_d(Entity var1) {
		return var1.boundingBox;
	}

	public AxisAlignedBB func_93_n() {
		return null;
	}

	public boolean func_124_r() {
		return true;
	}

	public EntityMinecart(World var1, double var2, double var4, double var6, int var8) {
		this(var1);
		this.setPosition(var2, var4 + (double)this.yOffset, var6);
		this.motionX = 0.0D;
		this.motionY = 0.0D;
		this.motionZ = 0.0D;
		this.prevPosX = var2;
		this.prevPosY = var4;
		this.prevPosZ = var6;
		this.minecartType = var8;
	}

	public double func_130_h() {
		return (double)this.height * 0.0D - (double)0.3F;
	}

	public boolean attackEntity(Entity var1, int var2) {
		if(!this.worldObj.multiplayerWorld && !this.field_304_B) {
			this.field_477_ad = -this.field_477_ad;
			this.field_9167_b = 10;
			this.func_9060_u();
			this.field_9168_a += var2 * 10;
			if(this.field_9168_a > 40) {
				this.dropItemWithOffset(Item.minecartEmpty.swiftedIndex, 1, 0.0F);
				if(this.minecartType == 1) {
					this.dropItemWithOffset(Block.crate.blockID, 1, 0.0F);
				} else if(this.minecartType == 2) {
					this.dropItemWithOffset(Block.stoneOvenIdle.blockID, 1, 0.0F);
				}

				this.setEntityDead();
			}

			return true;
		} else {
			return true;
		}
	}

	public boolean func_129_c_() {
		return !this.field_304_B;
	}

	public void setEntityDead() {
		for(int var1 = 0; var1 < this.func_83_a(); ++var1) {
			ItemStack var2 = this.getStackInSlot(var1);
			if(var2 != null) {
				float var3 = this.field_9064_W.nextFloat() * 0.8F + 0.1F;
				float var4 = this.field_9064_W.nextFloat() * 0.8F + 0.1F;
				float var5 = this.field_9064_W.nextFloat() * 0.8F + 0.1F;

				while(var2.stackSize > 0) {
					int var6 = this.field_9064_W.nextInt(21) + 10;
					if(var6 > var2.stackSize) {
						var6 = var2.stackSize;
					}

					var2.stackSize -= var6;
					EntityItem var7 = new EntityItem(this.worldObj, this.posX + (double)var3, this.posY + (double)var4, this.posZ + (double)var5, new ItemStack(var2.itemID, var6, var2.itemDamage));
					float var8 = 0.05F;
					var7.motionX = (double)((float)this.field_9064_W.nextGaussian() * var8);
					var7.motionY = (double)((float)this.field_9064_W.nextGaussian() * var8 + 0.2F);
					var7.motionZ = (double)((float)this.field_9064_W.nextGaussian() * var8);
					this.worldObj.entityJoinedWorld(var7);
				}
			}
		}

		super.setEntityDead();
	}

	public void onUpdate() {
		if(this.field_9167_b > 0) {
			--this.field_9167_b;
		}

		if(this.field_9168_a > 0) {
			--this.field_9168_a;
		}

		double var7;
		if(this.worldObj.multiplayerWorld && this.field_9163_an > 0) {
			if(this.field_9163_an > 0) {
				double var41 = this.posX + (this.field_9162_ao - this.posX) / (double)this.field_9163_an;
				double var42 = this.posY + (this.field_9161_ap - this.posY) / (double)this.field_9163_an;
				double var5 = this.posZ + (this.field_9160_aq - this.posZ) / (double)this.field_9163_an;

				for(var7 = this.field_9159_ar - (double)this.rotationYaw; var7 < -180.0D; var7 += 360.0D) {
				}

				while(var7 >= 180.0D) {
					var7 -= 360.0D;
				}

				this.rotationYaw = (float)((double)this.rotationYaw + var7 / (double)this.field_9163_an);
				this.rotationPitch = (float)((double)this.rotationPitch + (this.field_9158_as - (double)this.rotationPitch) / (double)this.field_9163_an);
				--this.field_9163_an;
				this.setPosition(var41, var42, var5);
				this.setRotation(this.rotationYaw, this.rotationPitch);
			} else {
				this.setPosition(this.posX, this.posY, this.posZ);
				this.setRotation(this.rotationYaw, this.rotationPitch);
			}

		} else {
			this.prevPosX = this.posX;
			this.prevPosY = this.posY;
			this.prevPosZ = this.posZ;
			this.motionY -= (double)0.04F;
			int var1 = MathHelper.floor_double(this.posX);
			int var2 = MathHelper.floor_double(this.posY);
			int var3 = MathHelper.floor_double(this.posZ);
			if(this.worldObj.getBlockId(var1, var2 - 1, var3) == Block.minecartTrack.blockID) {
				--var2;
			}

			double var4 = 0.4D;
			boolean var6 = false;
			var7 = 1.0D / 128.0D;
			if(this.worldObj.getBlockId(var1, var2, var3) == Block.minecartTrack.blockID) {
				Vec3D var9 = this.func_182_g(this.posX, this.posY, this.posZ);
				int var10 = this.worldObj.getBlockMetadata(var1, var2, var3);
				this.posY = (double)var2;
				if(var10 >= 2 && var10 <= 5) {
					this.posY = (double)(var2 + 1);
				}

				if(var10 == 2) {
					this.motionX -= var7;
				}

				if(var10 == 3) {
					this.motionX += var7;
				}

				if(var10 == 4) {
					this.motionZ += var7;
				}

				if(var10 == 5) {
					this.motionZ -= var7;
				}

				int[][] var11 = field_468_ak[var10];
				double var12 = (double)(var11[1][0] - var11[0][0]);
				double var14 = (double)(var11[1][2] - var11[0][2]);
				double var16 = Math.sqrt(var12 * var12 + var14 * var14);
				double var18 = this.motionX * var12 + this.motionZ * var14;
				if(var18 < 0.0D) {
					var12 = -var12;
					var14 = -var14;
				}

				double var20 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
				this.motionX = var20 * var12 / var16;
				this.motionZ = var20 * var14 / var16;
				double var22 = 0.0D;
				double var24 = (double)var1 + 0.5D + (double)var11[0][0] * 0.5D;
				double var26 = (double)var3 + 0.5D + (double)var11[0][2] * 0.5D;
				double var28 = (double)var1 + 0.5D + (double)var11[1][0] * 0.5D;
				double var30 = (double)var3 + 0.5D + (double)var11[1][2] * 0.5D;
				var12 = var28 - var24;
				var14 = var30 - var26;
				double var32;
				double var34;
				double var36;
				if(var12 == 0.0D) {
					this.posX = (double)var1 + 0.5D;
					var22 = this.posZ - (double)var3;
				} else if(var14 == 0.0D) {
					this.posZ = (double)var3 + 0.5D;
					var22 = this.posX - (double)var1;
				} else {
					var32 = this.posX - var24;
					var34 = this.posZ - var26;
					var36 = (var32 * var12 + var34 * var14) * 2.0D;
					var22 = var36;
				}

				this.posX = var24 + var12 * var22;
				this.posZ = var26 + var14 * var22;
				this.setPosition(this.posX, this.posY + (double)this.yOffset, this.posZ);
				var32 = this.motionX;
				var34 = this.motionZ;
				if(this.field_328_f != null) {
					var32 *= 0.75D;
					var34 *= 0.75D;
				}

				if(var32 < -var4) {
					var32 = -var4;
				}

				if(var32 > var4) {
					var32 = var4;
				}

				if(var34 < -var4) {
					var34 = -var4;
				}

				if(var34 > var4) {
					var34 = var4;
				}

				this.moveEntity(var32, 0.0D, var34);
				if(var11[0][1] != 0 && MathHelper.floor_double(this.posX) - var1 == var11[0][0] && MathHelper.floor_double(this.posZ) - var3 == var11[0][2]) {
					this.setPosition(this.posX, this.posY + (double)var11[0][1], this.posZ);
				} else if(var11[1][1] != 0 && MathHelper.floor_double(this.posX) - var1 == var11[1][0] && MathHelper.floor_double(this.posZ) - var3 == var11[1][2]) {
					this.setPosition(this.posX, this.posY + (double)var11[1][1], this.posZ);
				}

				if(this.field_328_f != null) {
					this.motionX *= (double)0.997F;
					this.motionY *= 0.0D;
					this.motionZ *= (double)0.997F;
				} else {
					if(this.minecartType == 2) {
						var36 = (double)MathHelper.sqrt_double(this.field_9164_f * this.field_9164_f + this.field_9166_aj * this.field_9166_aj);
						if(var36 > 0.01D) {
							var6 = true;
							this.field_9164_f /= var36;
							this.field_9166_aj /= var36;
							double var38 = 0.04D;
							this.motionX *= (double)0.8F;
							this.motionY *= 0.0D;
							this.motionZ *= (double)0.8F;
							this.motionX += this.field_9164_f * var38;
							this.motionZ += this.field_9166_aj * var38;
						} else {
							this.motionX *= (double)0.9F;
							this.motionY *= 0.0D;
							this.motionZ *= (double)0.9F;
						}
					}

					this.motionX *= (double)0.96F;
					this.motionY *= 0.0D;
					this.motionZ *= (double)0.96F;
				}

				Vec3D var46 = this.func_182_g(this.posX, this.posY, this.posZ);
				if(var46 != null && var9 != null) {
					double var37 = (var9.yCoord - var46.yCoord) * 0.05D;
					var20 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
					if(var20 > 0.0D) {
						this.motionX = this.motionX / var20 * (var20 + var37);
						this.motionZ = this.motionZ / var20 * (var20 + var37);
					}

					this.setPosition(this.posX, var46.yCoord, this.posZ);
				}

				int var47 = MathHelper.floor_double(this.posX);
				int var48 = MathHelper.floor_double(this.posZ);
				if(var47 != var1 || var48 != var3) {
					var20 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
					this.motionX = var20 * (double)(var47 - var1);
					this.motionZ = var20 * (double)(var48 - var3);
				}

				if(this.minecartType == 2) {
					double var39 = (double)MathHelper.sqrt_double(this.field_9164_f * this.field_9164_f + this.field_9166_aj * this.field_9166_aj);
					if(var39 > 0.01D && this.motionX * this.motionX + this.motionZ * this.motionZ > 0.001D) {
						this.field_9164_f /= var39;
						this.field_9166_aj /= var39;
						if(this.field_9164_f * this.motionX + this.field_9166_aj * this.motionZ < 0.0D) {
							this.field_9164_f = 0.0D;
							this.field_9166_aj = 0.0D;
						} else {
							this.field_9164_f = this.motionX;
							this.field_9166_aj = this.motionZ;
						}
					}
				}
			} else {
				if(this.motionX < -var4) {
					this.motionX = -var4;
				}

				if(this.motionX > var4) {
					this.motionX = var4;
				}

				if(this.motionZ < -var4) {
					this.motionZ = -var4;
				}

				if(this.motionZ > var4) {
					this.motionZ = var4;
				}

				if(this.onGround) {
					this.motionX *= 0.5D;
					this.motionY *= 0.5D;
					this.motionZ *= 0.5D;
				}

				this.moveEntity(this.motionX, this.motionY, this.motionZ);
				if(!this.onGround) {
					this.motionX *= (double)0.95F;
					this.motionY *= (double)0.95F;
					this.motionZ *= (double)0.95F;
				}
			}

			this.rotationPitch = 0.0F;
			double var43 = this.prevPosX - this.posX;
			double var44 = this.prevPosZ - this.posZ;
			if(var43 * var43 + var44 * var44 > 0.001D) {
				this.rotationYaw = (float)(Math.atan2(var44, var43) * 180.0D / Math.PI);
				if(this.field_469_aj) {
					this.rotationYaw += 180.0F;
				}
			}

			double var13;
			for(var13 = (double)(this.rotationYaw - this.prevRotationYaw); var13 >= 180.0D; var13 -= 360.0D) {
			}

			while(var13 < -180.0D) {
				var13 += 360.0D;
			}

			if(var13 < -170.0D || var13 >= 170.0D) {
				this.rotationYaw += 180.0F;
				this.field_469_aj = !this.field_469_aj;
			}

			this.setRotation(this.rotationYaw, this.rotationPitch);
			List var15 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expands((double)0.2F, 0.0D, (double)0.2F));
			if(var15 != null && var15.size() > 0) {
				for(int var45 = 0; var45 < var15.size(); ++var45) {
					Entity var17 = (Entity)var15.get(var45);
					if(var17 != this.field_328_f && var17.func_124_r() && var17 instanceof EntityMinecart) {
						var17.applyEntityCollision(this);
					}
				}
			}

			if(this.field_328_f != null && this.field_328_f.field_304_B) {
				this.field_328_f = null;
			}

			if(var6 && this.field_9064_W.nextInt(4) == 0) {
				--this.field_9165_e;
				if(this.field_9165_e < 0) {
					this.field_9164_f = this.field_9166_aj = 0.0D;
				}

				this.worldObj.spawnParticle("largesmoke", this.posX, this.posY + 0.8D, this.posZ, 0.0D, 0.0D, 0.0D);
			}

		}
	}

	public Vec3D func_182_g(double var1, double var3, double var5) {
		int var7 = MathHelper.floor_double(var1);
		int var8 = MathHelper.floor_double(var3);
		int var9 = MathHelper.floor_double(var5);
		if(this.worldObj.getBlockId(var7, var8 - 1, var9) == Block.minecartTrack.blockID) {
			--var8;
		}

		if(this.worldObj.getBlockId(var7, var8, var9) == Block.minecartTrack.blockID) {
			int var10 = this.worldObj.getBlockMetadata(var7, var8, var9);
			var3 = (double)var8;
			if(var10 >= 2 && var10 <= 5) {
				var3 = (double)(var8 + 1);
			}

			int[][] var11 = field_468_ak[var10];
			double var12 = 0.0D;
			double var14 = (double)var7 + 0.5D + (double)var11[0][0] * 0.5D;
			double var16 = (double)var8 + 0.5D + (double)var11[0][1] * 0.5D;
			double var18 = (double)var9 + 0.5D + (double)var11[0][2] * 0.5D;
			double var20 = (double)var7 + 0.5D + (double)var11[1][0] * 0.5D;
			double var22 = (double)var8 + 0.5D + (double)var11[1][1] * 0.5D;
			double var24 = (double)var9 + 0.5D + (double)var11[1][2] * 0.5D;
			double var26 = var20 - var14;
			double var28 = (var22 - var16) * 2.0D;
			double var30 = var24 - var18;
			if(var26 == 0.0D) {
				var1 = (double)var7 + 0.5D;
				var12 = var5 - (double)var9;
			} else if(var30 == 0.0D) {
				var5 = (double)var9 + 0.5D;
				var12 = var1 - (double)var7;
			} else {
				double var32 = var1 - var14;
				double var34 = var5 - var18;
				double var36 = (var32 * var26 + var34 * var30) * 2.0D;
				var12 = var36;
			}

			var1 = var14 + var26 * var12;
			var3 = var16 + var28 * var12;
			var5 = var18 + var30 * var12;
			if(var28 < 0.0D) {
				++var3;
			}

			if(var28 > 0.0D) {
				var3 += 0.5D;
			}

			return Vec3D.createVector(var1, var3, var5);
		} else {
			return null;
		}
	}

	protected void writeEntityToNBT(NBTTagCompound var1) {
		var1.setInteger("Type", this.minecartType);
		if(this.minecartType == 2) {
			var1.setDouble("PushX", this.field_9164_f);
			var1.setDouble("PushZ", this.field_9166_aj);
			var1.setShort("Fuel", (short)this.field_9165_e);
		} else if(this.minecartType == 1) {
			NBTTagList var2 = new NBTTagList();

			for(int var3 = 0; var3 < this.cargoItems.length; ++var3) {
				if(this.cargoItems[var3] != null) {
					NBTTagCompound var4 = new NBTTagCompound();
					var4.setByte("Slot", (byte)var3);
					this.cargoItems[var3].writeToNBT(var4);
					var2.setTag(var4);
				}
			}

			var1.setTag("Items", var2);
		}

	}

	protected void readEntityFromNBT(NBTTagCompound var1) {
		this.minecartType = var1.getInteger("Type");
		if(this.minecartType == 2) {
			this.field_9164_f = var1.getDouble("PushX");
			this.field_9166_aj = var1.getDouble("PushZ");
			this.field_9165_e = var1.getShort("Fuel");
		} else if(this.minecartType == 1) {
			NBTTagList var2 = var1.getTagList("Items");
			this.cargoItems = new ItemStack[this.func_83_a()];

			for(int var3 = 0; var3 < var2.tagCount(); ++var3) {
				NBTTagCompound var4 = (NBTTagCompound)var2.tagAt(var3);
				int var5 = var4.getByte("Slot") & 255;
				if(var5 >= 0 && var5 < this.cargoItems.length) {
					this.cargoItems[var5] = new ItemStack(var4);
				}
			}
		}

	}

	public void applyEntityCollision(Entity var1) {
		if(!this.worldObj.multiplayerWorld) {
			if(var1 != this.field_328_f) {
				if(var1 instanceof EntityLiving && !(var1 instanceof EntityPlayer) && this.minecartType == 0 && this.motionX * this.motionX + this.motionZ * this.motionZ > 0.01D && this.field_328_f == null && var1.field_327_g == null) {
					var1.func_6094_e(this);
				}

				double var2 = var1.posX - this.posX;
				double var4 = var1.posZ - this.posZ;
				double var6 = var2 * var2 + var4 * var4;
				if(var6 >= (double)1.0E-4F) {
					var6 = (double)MathHelper.sqrt_double(var6);
					var2 /= var6;
					var4 /= var6;
					double var8 = 1.0D / var6;
					if(var8 > 1.0D) {
						var8 = 1.0D;
					}

					var2 *= var8;
					var4 *= var8;
					var2 *= (double)0.1F;
					var4 *= (double)0.1F;
					var2 *= (double)(1.0F - this.field_286_P);
					var4 *= (double)(1.0F - this.field_286_P);
					var2 *= 0.5D;
					var4 *= 0.5D;
					if(var1 instanceof EntityMinecart) {
						double var10 = var1.motionX + this.motionX;
						double var12 = var1.motionZ + this.motionZ;
						if(((EntityMinecart)var1).minecartType == 2 && this.minecartType != 2) {
							this.motionX *= (double)0.2F;
							this.motionZ *= (double)0.2F;
							this.addVelocity(var1.motionX - var2, 0.0D, var1.motionZ - var4);
							var1.motionX *= (double)0.7F;
							var1.motionZ *= (double)0.7F;
						} else if(((EntityMinecart)var1).minecartType != 2 && this.minecartType == 2) {
							var1.motionX *= (double)0.2F;
							var1.motionZ *= (double)0.2F;
							var1.addVelocity(this.motionX + var2, 0.0D, this.motionZ + var4);
							this.motionX *= (double)0.7F;
							this.motionZ *= (double)0.7F;
						} else {
							var10 /= 2.0D;
							var12 /= 2.0D;
							this.motionX *= (double)0.2F;
							this.motionZ *= (double)0.2F;
							this.addVelocity(var10 - var2, 0.0D, var12 - var4);
							var1.motionX *= (double)0.2F;
							var1.motionZ *= (double)0.2F;
							var1.addVelocity(var10 + var2, 0.0D, var12 + var4);
						}
					} else {
						this.addVelocity(-var2, 0.0D, -var4);
						var1.addVelocity(var2 / 4.0D, 0.0D, var4 / 4.0D);
					}
				}

			}
		}
	}

	public int func_83_a() {
		return 27;
	}

	public ItemStack getStackInSlot(int var1) {
		return this.cargoItems[var1];
	}

	public boolean func_6092_a(EntityPlayer var1) {
		if(this.minecartType == 0) {
			if(this.field_328_f != null && this.field_328_f instanceof EntityPlayer && this.field_328_f != var1) {
				return true;
			}

			if(!this.worldObj.multiplayerWorld) {
				var1.func_6094_e(this);
			}
		} else if(this.minecartType == 1) {
			var1.func_166_a(this);
		} else if(this.minecartType == 2) {
			ItemStack var2 = var1.inventory.getCurrentItem();
			if(var2 != null && var2.itemID == Item.coal.swiftedIndex) {
				if(--var2.stackSize == 0) {
					var1.inventory.setInventorySlotContents(var1.inventory.currentItem, (ItemStack)null);
				}

				this.field_9165_e += 1200;
			}

			this.field_9164_f = this.posX - var1.posX;
			this.field_9166_aj = this.posZ - var1.posZ;
		}

		return true;
	}
}

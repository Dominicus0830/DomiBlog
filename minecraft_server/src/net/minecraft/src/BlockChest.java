package net.minecraft.src;

import java.util.Random;

public class BlockChest extends BlockContainer {
	private Random field_656_a = new Random();

	protected BlockChest(int var1) {
		super(var1, Material.wood);
		this.blockIndexInTexture = 26;
	}

	public int getBlockTextureFromSide(int var1) {
		return var1 == 1 ? this.blockIndexInTexture - 1 : (var1 == 0 ? this.blockIndexInTexture - 1 : (var1 == 3 ? this.blockIndexInTexture + 1 : this.blockIndexInTexture));
	}

	public boolean canPlaceBlockAt(World var1, int var2, int var3, int var4) {
		int var5 = 0;
		if(var1.getBlockId(var2 - 1, var3, var4) == this.blockID) {
			++var5;
		}

		if(var1.getBlockId(var2 + 1, var3, var4) == this.blockID) {
			++var5;
		}

		if(var1.getBlockId(var2, var3, var4 - 1) == this.blockID) {
			++var5;
		}

		if(var1.getBlockId(var2, var3, var4 + 1) == this.blockID) {
			++var5;
		}

		return var5 > 1 ? false : (this.isThereANeighborChest(var1, var2 - 1, var3, var4) ? false : (this.isThereANeighborChest(var1, var2 + 1, var3, var4) ? false : (this.isThereANeighborChest(var1, var2, var3, var4 - 1) ? false : !this.isThereANeighborChest(var1, var2, var3, var4 + 1))));
	}

	private boolean isThereANeighborChest(World var1, int var2, int var3, int var4) {
		return var1.getBlockId(var2, var3, var4) != this.blockID ? false : (var1.getBlockId(var2 - 1, var3, var4) == this.blockID ? true : (var1.getBlockId(var2 + 1, var3, var4) == this.blockID ? true : (var1.getBlockId(var2, var3, var4 - 1) == this.blockID ? true : var1.getBlockId(var2, var3, var4 + 1) == this.blockID)));
	}

	public void onBlockRemoval(World var1, int var2, int var3, int var4) {
		TileEntityChest var5 = (TileEntityChest)var1.getBlock(var2, var3, var4);

		for(int var6 = 0; var6 < var5.func_83_a(); ++var6) {
			ItemStack var7 = var5.getStackInSlot(var6);
			if(var7 != null) {
				float var8 = this.field_656_a.nextFloat() * 0.8F + 0.1F;
				float var9 = this.field_656_a.nextFloat() * 0.8F + 0.1F;
				float var10 = this.field_656_a.nextFloat() * 0.8F + 0.1F;

				while(var7.stackSize > 0) {
					int var11 = this.field_656_a.nextInt(21) + 10;
					if(var11 > var7.stackSize) {
						var11 = var7.stackSize;
					}

					var7.stackSize -= var11;
					EntityItem var12 = new EntityItem(var1, (double)((float)var2 + var8), (double)((float)var3 + var9), (double)((float)var4 + var10), new ItemStack(var7.itemID, var11, var7.itemDamage));
					float var13 = 0.05F;
					var12.motionX = (double)((float)this.field_656_a.nextGaussian() * var13);
					var12.motionY = (double)((float)this.field_656_a.nextGaussian() * var13 + 0.2F);
					var12.motionZ = (double)((float)this.field_656_a.nextGaussian() * var13);
					var1.entityJoinedWorld(var12);
				}
			}
		}

		super.onBlockRemoval(var1, var2, var3, var4);
	}

	public boolean blockActivated(World var1, int var2, int var3, int var4, EntityPlayer var5) {
		Object var6 = (TileEntityChest)var1.getBlock(var2, var3, var4);
		if(var1.doesBlockAllowAttachment(var2, var3 + 1, var4)) {
			return true;
		} else if(var1.getBlockId(var2 - 1, var3, var4) == this.blockID && var1.doesBlockAllowAttachment(var2 - 1, var3 + 1, var4)) {
			return true;
		} else if(var1.getBlockId(var2 + 1, var3, var4) == this.blockID && var1.doesBlockAllowAttachment(var2 + 1, var3 + 1, var4)) {
			return true;
		} else if(var1.getBlockId(var2, var3, var4 - 1) == this.blockID && var1.doesBlockAllowAttachment(var2, var3 + 1, var4 - 1)) {
			return true;
		} else if(var1.getBlockId(var2, var3, var4 + 1) == this.blockID && var1.doesBlockAllowAttachment(var2, var3 + 1, var4 + 1)) {
			return true;
		} else {
			if(var1.getBlockId(var2 - 1, var3, var4) == this.blockID) {
				var6 = new InventoryLargeChest("Large chest", (TileEntityChest)var1.getBlock(var2 - 1, var3, var4), (IInventory)var6);
			}

			if(var1.getBlockId(var2 + 1, var3, var4) == this.blockID) {
				var6 = new InventoryLargeChest("Large chest", (IInventory)var6, (TileEntityChest)var1.getBlock(var2 + 1, var3, var4));
			}

			if(var1.getBlockId(var2, var3, var4 - 1) == this.blockID) {
				var6 = new InventoryLargeChest("Large chest", (TileEntityChest)var1.getBlock(var2, var3, var4 - 1), (IInventory)var6);
			}

			if(var1.getBlockId(var2, var3, var4 + 1) == this.blockID) {
				var6 = new InventoryLargeChest("Large chest", (IInventory)var6, (TileEntityChest)var1.getBlock(var2, var3, var4 + 1));
			}

			var5.func_166_a((IInventory)var6);
			return true;
		}
	}

	protected TileEntity func_294_a_() {
		return new TileEntityChest();
	}
}

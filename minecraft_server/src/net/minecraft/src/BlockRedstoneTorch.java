package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockRedstoneTorch extends BlockTorch {
	private boolean torchActive = false;
	private static List torchUpdates = new ArrayList();

	private boolean func_280_a(World var1, int var2, int var3, int var4, boolean var5) {
		if(var5) {
			torchUpdates.add(new RedstoneUpdateInfo(var2, var3, var4, var1.worldTime));
		}

		int var6 = 0;

		for(int var7 = 0; var7 < torchUpdates.size(); ++var7) {
			RedstoneUpdateInfo var8 = (RedstoneUpdateInfo)torchUpdates.get(var7);
			if(var8.field_775_a == var2 && var8.field_774_b == var3 && var8.field_777_c == var4) {
				++var6;
				if(var6 >= 8) {
					return true;
				}
			}
		}

		return false;
	}

	protected BlockRedstoneTorch(int var1, int var2, boolean var3) {
		super(var1, var2);
		this.torchActive = var3;
		this.setTickOnLoad(true);
	}

	public int tickRate() {
		return 2;
	}

	public void onBlockAdded(World var1, int var2, int var3, int var4) {
		if(var1.getBlockMetadata(var2, var3, var4) == 0) {
			super.onBlockAdded(var1, var2, var3, var4);
		}

		if(this.torchActive) {
			var1.notifyBlocksOfNeighborChange(var2, var3 - 1, var4, this.blockID);
			var1.notifyBlocksOfNeighborChange(var2, var3 + 1, var4, this.blockID);
			var1.notifyBlocksOfNeighborChange(var2 - 1, var3, var4, this.blockID);
			var1.notifyBlocksOfNeighborChange(var2 + 1, var3, var4, this.blockID);
			var1.notifyBlocksOfNeighborChange(var2, var3, var4 - 1, this.blockID);
			var1.notifyBlocksOfNeighborChange(var2, var3, var4 + 1, this.blockID);
		}

	}

	public void onBlockRemoval(World var1, int var2, int var3, int var4) {
		if(this.torchActive) {
			var1.notifyBlocksOfNeighborChange(var2, var3 - 1, var4, this.blockID);
			var1.notifyBlocksOfNeighborChange(var2, var3 + 1, var4, this.blockID);
			var1.notifyBlocksOfNeighborChange(var2 - 1, var3, var4, this.blockID);
			var1.notifyBlocksOfNeighborChange(var2 + 1, var3, var4, this.blockID);
			var1.notifyBlocksOfNeighborChange(var2, var3, var4 - 1, this.blockID);
			var1.notifyBlocksOfNeighborChange(var2, var3, var4 + 1, this.blockID);
		}

	}

	public boolean isPoweringTo(IBlockAccess var1, int var2, int var3, int var4, int var5) {
		if(!this.torchActive) {
			return false;
		} else {
			int var6 = var1.getBlockMetadata(var2, var3, var4);
			return var6 == 5 && var5 == 1 ? false : (var6 == 3 && var5 == 3 ? false : (var6 == 4 && var5 == 2 ? false : (var6 == 1 && var5 == 5 ? false : var6 != 2 || var5 != 4)));
		}
	}

	private boolean func_15001_g(World var1, int var2, int var3, int var4) {
		int var5 = var1.getBlockMetadata(var2, var3, var4);
		return var5 == 5 && var1.isBlockIndirectlyProvidingPowerTo(var2, var3 - 1, var4, 0) ? true : (var5 == 3 && var1.isBlockIndirectlyProvidingPowerTo(var2, var3, var4 - 1, 2) ? true : (var5 == 4 && var1.isBlockIndirectlyProvidingPowerTo(var2, var3, var4 + 1, 3) ? true : (var5 == 1 && var1.isBlockIndirectlyProvidingPowerTo(var2 - 1, var3, var4, 4) ? true : var5 == 2 && var1.isBlockIndirectlyProvidingPowerTo(var2 + 1, var3, var4, 5))));
	}

	public void updateTick(World var1, int var2, int var3, int var4, Random var5) {
		boolean var6 = this.func_15001_g(var1, var2, var3, var4);

		while(torchUpdates.size() > 0 && var1.worldTime - ((RedstoneUpdateInfo)torchUpdates.get(0)).field_776_d > 100L) {
			torchUpdates.remove(0);
		}

		if(this.torchActive) {
			if(var6) {
				var1.func_507_b(var2, var3, var4, Block.torchRedstoneIdle.blockID, var1.getBlockMetadata(var2, var3, var4));
				if(this.func_280_a(var1, var2, var3, var4, true)) {
					var1.playSoundEffect((double)((float)var2 + 0.5F), (double)((float)var3 + 0.5F), (double)((float)var4 + 0.5F), "random.fizz", 0.5F, 2.6F + (var1.rand.nextFloat() - var1.rand.nextFloat()) * 0.8F);

					for(int var7 = 0; var7 < 5; ++var7) {
						double var8 = (double)var2 + var5.nextDouble() * 0.6D + 0.2D;
						double var10 = (double)var3 + var5.nextDouble() * 0.6D + 0.2D;
						double var12 = (double)var4 + var5.nextDouble() * 0.6D + 0.2D;
						var1.spawnParticle("smoke", var8, var10, var12, 0.0D, 0.0D, 0.0D);
					}
				}
			}
		} else if(!var6 && !this.func_280_a(var1, var2, var3, var4, false)) {
			var1.func_507_b(var2, var3, var4, Block.torchRedstoneActive.blockID, var1.getBlockMetadata(var2, var3, var4));
		}

	}

	public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5) {
		super.onNeighborBlockChange(var1, var2, var3, var4, var5);
		var1.scheduleBlockUpdate(var2, var3, var4, this.blockID);
	}

	public boolean isIndirectlyPoweringTo(World var1, int var2, int var3, int var4, int var5) {
		return var5 == 0 ? this.isPoweringTo(var1, var2, var3, var4, var5) : false;
	}

	public int idDropped(int var1, Random var2) {
		return Block.torchRedstoneActive.blockID;
	}

	public boolean canProvidePower() {
		return true;
	}
}

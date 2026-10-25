package com.crazyhacks.tabsort.mixin;

import com.crazyhacks.tabsort.TabSortModule;
import net.minecraft.client.gui.components.PlayerTabOverlay;
import net.minecraft.client.multiplayer.PlayerInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.Comparator;

@Mixin(PlayerTabOverlay.class)
public class MixinPlayerTabOverlay {
	// sorts before vanilla cuts the list at 80 players, so friends and enemies are never cut off
	@ModifyArg(method = "getPlayerInfos", at = @At(value = "INVOKE",
			target = "Ljava/util/stream/Stream;sorted(Ljava/util/Comparator;)Ljava/util/stream/Stream;"))
	private Comparator<PlayerInfo> crazyTabSort$friendsFirst(Comparator<PlayerInfo> original) {
		return TabSortModule.wrap(original);
	}
}

package com.crazyhacks.tabsort;

import net.minecraft.client.multiplayer.PlayerInfo;
import org.rusherhack.client.api.RusherHackAPI;
import org.rusherhack.client.api.feature.module.ModuleCategory;
import org.rusherhack.client.api.feature.module.ToggleableModule;
import org.rusherhack.client.api.system.IRelationManager;
import org.rusherhack.core.setting.BooleanSetting;

import java.util.Comparator;

/**
 * Reorders the tab list: RusherHack friends first, then enemies, then everyone else.
 * Inside each group the vanilla order (team, name) is kept.
 */
public class TabSortModule extends ToggleableModule {
	public static TabSortModule INSTANCE;

	private final BooleanSetting friends = new BooleanSetting("Friends", "Put friends at the top", true);
	private final BooleanSetting enemies = new BooleanSetting("Enemies", "Put enemies right under the friends", true);

	public TabSortModule() {
		super("TabSort", "Friends at the top of the tab list, enemies right under them", ModuleCategory.RENDER);
		this.registerSettings(this.friends, this.enemies);
	}

	public static Comparator<PlayerInfo> wrap(Comparator<PlayerInfo> original) {
		final TabSortModule module = INSTANCE;
		if(module == null || !module.isToggled()) {
			return original;
		}
		final IRelationManager relations = RusherHackAPI.getRelationManager();
		return Comparator.<PlayerInfo>comparingInt(info -> module.group(relations, info.getProfile().name()))
						 .thenComparing(original);
	}

	private int group(IRelationManager relations, String name) {
		if(this.friends.getValue() && relations.isFriend(name)) {
			return 0;
		}
		if(this.enemies.getValue() && relations.isEnemy(name)) {
			return 1;
		}
		return 2;
	}
}

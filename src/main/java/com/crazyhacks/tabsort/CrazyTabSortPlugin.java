package com.crazyhacks.tabsort;

import org.rusherhack.client.api.RusherHackAPI;
import org.rusherhack.client.api.plugin.Plugin;

/**
 * CrazyTabSort: friends at the top of the tab list, enemies under them, as a RusherHack plugin. Made by lurda55.
 */
public class CrazyTabSortPlugin extends Plugin {
	@Override
	public void onLoad() {
		TabSortModule.INSTANCE = new TabSortModule();
		RusherHackAPI.getModuleManager().registerFeature(TabSortModule.INSTANCE);
	}

	@Override
	public void onUnload() {
		TabSortModule.INSTANCE = null;
	}
}

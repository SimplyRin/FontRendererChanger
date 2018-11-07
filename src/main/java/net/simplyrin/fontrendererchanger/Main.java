package net.simplyrin.fontrendererchanger;

import org.joor.Reflect;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.simplyrin.fontrendererchanger.utils.CustomFontRenderer;
import net.simplyrin.fontrendererchanger.utils.ThreadPool;

/**
 * Created by SimplyRin on 2018/11/07.
 *
 * Copyright (c) 2018 SimplyRin
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
@Mod(modid = "FontRendererChanger", version = "1.0")
public class Main {

	@EventHandler
	public void init(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(this);
	}

	private ResourceLocation resourceLocation;
	private TextureManager textureManager;
	private boolean unicodeFlag;

	@SubscribeEvent
	public void onConnected(FMLNetworkEvent.ClientConnectedToServerEvent event) {
		ThreadPool.run(() -> {
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}

			Minecraft mc = Minecraft.getMinecraft();
			mc.addScheduledTask(() -> {
				FontRenderer fontRenderer = mc.fontRendererObj;

				try {
					this.resourceLocation = Reflect.on(fontRenderer).field("field_111273_g").get();
				} catch (Exception e) {
					this.resourceLocation = Reflect.on(fontRenderer).field("locationFontTexture").get();
				}
				System.out.println("resourceLocation: " + (this.resourceLocation == null ? "null" : "valid"));

				try {
					this.textureManager = Reflect.on(fontRenderer).field("field_78298_i").get();
				} catch (Exception e) {
					this.textureManager = Reflect.on(fontRenderer).field("renderEngine").get();
				}
				System.out.println("textureManager: " + (this.textureManager == null ? "null" : "valid"));

				try {
					this.unicodeFlag = Reflect.on(fontRenderer).field("field_78293_l").get();
				} catch (Exception e) {
					this.unicodeFlag = Reflect.on(fontRenderer).field("unicodeFlag").get();
				}
				System.out.println("unicodeFlag: " + (this.unicodeFlag ? "true" : "false"));

				Minecraft.getMinecraft().fontRendererObj = new CustomFontRenderer(mc.gameSettings, this.resourceLocation, textureManager, true);
				System.out.println("Replaced fontRendererObj");
			});
		});
	}

}

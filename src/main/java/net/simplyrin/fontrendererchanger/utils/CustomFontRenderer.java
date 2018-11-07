package net.simplyrin.fontrendererchanger.utils;

import org.joor.Reflect;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ResourceLocation;

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
public class CustomFontRenderer extends FontRenderer {

	public CustomFontRenderer(GameSettings gameSettingsIn, ResourceLocation location, TextureManager textureManagerIn, boolean unicode) {
		super(gameSettingsIn, location, textureManagerIn, unicode);
	}

	@Override
	public int drawString(String text, float x, float y, int color, boolean dropShadow) {
		// this.enableAlpha();
		try {
			Reflect.on(this).call("func_78265_b");
		} catch (Exception e) {
			Reflect.on(this).call("resetStyles");
		}

		int i;
		if (dropShadow) {
			try {
				i = Reflect.on(this).call("func_180455_b", text, x + 1.0F, y + 1.0F, color, true).get();
			} catch (Exception e) {
				i = Reflect.on(this).call("renderString", text, x + 1.0F, y + 1.0F, color, true).get();
			}
			int temp;
			try {
				temp = Reflect.on(this).call("func_180455_b", text, x, y, color, false).get();
			} catch (Exception e) {
				temp = Reflect.on(this).call("renderString", text, x, y, color, false).get();
			}
			i = Math.max(i, temp);
		} else {
			try {
				i = Reflect.on(this).call("func_180455_b", text, x, y, color, false).get();
			} catch (Exception e) {
				i = Reflect.on(this).call("renderString", text, x, y, color, false).get();
			}
		}

		return i;
	}

}

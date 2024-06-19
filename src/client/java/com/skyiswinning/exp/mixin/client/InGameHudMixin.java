package com.skyiswinning.exp.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
  @Shadow
  @Final
  private MinecraftClient client;

  @Shadow
  public abstract TextRenderer getTextRenderer();

  @Shadow
  public abstract boolean shouldRenderExperience();

  private int getColor(int alpha, int red, int green, int blue) {
    return (alpha << 24) | (red << 16) | (green << 8) | blue;
  }

  @Overwrite
  private void renderExperienceLevel(DrawContext context, RenderTickCounter tickCounter) {
    int i = this.client.player.experienceLevel;
    if (this.shouldRenderExperience() && i > 0) {
      this.client.getProfiler().push("expLevel");
      String string = "" + i;
      int j = (context.getScaledWindowWidth() - this.getTextRenderer().getWidth(string)) / 2;
      int k = context.getScaledWindowHeight() - 31 - 4;
      int outlineColor = getColor(255, 1, 36, 10);
      context.drawText(this.getTextRenderer(), string, j + 1, k, outlineColor, false);
      context.drawText(this.getTextRenderer(), string, j - 1, k, outlineColor, false);
      context.drawText(this.getTextRenderer(), string, j, k + 1, outlineColor, false);
      context.drawText(this.getTextRenderer(), string, j, k - 1, outlineColor, false);
      context.drawText(this.getTextRenderer(), string, j, k, 8453920, false); // Original color for text
      this.client.getProfiler().pop();
    }
  }
}

package ng.skyiswinni.exp.mixin.client;

import net.minecraft.client.gui.contextualbar.ContextualBarRenderer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.Font;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ContextualBarRenderer.class)
public interface InGameHudMixin {

  @Unique
  private static int getColor(int alpha, int red, int green, int blue) {
    return (alpha << 24) | (red << 16) | (green << 8) | blue;
  }

  /**
   * @author Sky
   * @reason Change EXP Bar Colour
   */
  @Overwrite
  static void renderExperienceLevel(GuiGraphics guiGraphics, Font font, int level) {

    Component component = Component.translatable("gui.experience.level", level);

    int x = (guiGraphics.guiWidth() - font.width(component)) / 2;
    int y = guiGraphics.guiHeight() - 24 - 9 - 2;

    int outlineColor = getColor(255, 1, 36, 10);

    guiGraphics.drawString(font, component, x + 1, y, outlineColor, false);
    guiGraphics.drawString(font, component, x - 1, y, outlineColor, false);
    guiGraphics.drawString(font, component, x, y + 1, outlineColor, false);
    guiGraphics.drawString(font, component, x, y - 1, outlineColor, false);

    guiGraphics.drawString(font, component, x, y, -8323296, false);
  }
}
package fuzs.easyshulkerboxes.mixin;

import fuzs.easyshulkerboxes.world.item.ContainerItemHelper;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

// run before Charm does as it has the same feature, but our implementation is more polished
// feature must be disabled in Charm's config as some parts cannot be overridden
@Mixin(value = Item.class, priority = 900)
public abstract class ItemMixin {
    @Inject(method = "overrideStackedOnOther", at = @At("HEAD"), cancellable = true)
    public void overrideStackedOnOther(ItemStack stack, Slot slot, ClickAction clickAction, Player player, CallbackInfoReturnable<Boolean> callbackInfo) {
        if (Block.byItem(stack.getItem()) instanceof ShulkerBoxBlock) {
            boolean success = ContainerItemHelper.overrideStackedOnOther(stack, BlockEntityType.SHULKER_BOX, 3, slot, clickAction, player, s -> s.getItem().canFitInsideContainerItems(), SoundEvents.BUNDLE_INSERT, SoundEvents.BUNDLE_REMOVE_ONE);
            callbackInfo.setReturnValue(success);
        }
    }

    @Inject(method = "overrideOtherStackedOnMe", at = @At("HEAD"), cancellable = true)
    public void overrideOtherStackedOnMe(ItemStack stack, ItemStack stackOnMe, Slot slot, ClickAction clickAction, Player player, SlotAccess slotAccess, CallbackInfoReturnable<Boolean> callbackInfo) {
        if (Block.byItem(stack.getItem()) instanceof ShulkerBoxBlock) {
            boolean success = ContainerItemHelper.overrideOtherStackedOnMe(stack, BlockEntityType.SHULKER_BOX, 3, stackOnMe, slot, clickAction, player, slotAccess, s -> s.getItem().canFitInsideContainerItems(), SoundEvents.BUNDLE_INSERT, SoundEvents.BUNDLE_REMOVE_ONE);
            callbackInfo.setReturnValue(success);
        }
    }

    @Inject(method = "getTooltipImage", at = @At("HEAD"), cancellable = true)
    public void getTooltipImage(ItemStack stack, CallbackInfoReturnable<Optional<TooltipComponent>> callbackInfo) {
        if (Block.byItem(stack.getItem()) instanceof ShulkerBoxBlock) {
            DyeColor color = ShulkerBoxBlock.getColorFromItem(stack.getItem());
            Optional<TooltipComponent> component = ContainerItemHelper.getTooltipImage(stack, BlockEntityType.SHULKER_BOX, 3, color);
            callbackInfo.setReturnValue(component);
        }
    }
}

package cn.grainalcohol.mixin;

import cn.grainalcohol.AbsorptionAccessor;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    @Inject(
            method = "applyDamage",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;setAbsorptionAmount(F)V",
                    shift = At.Shift.AFTER,
                    ordinal = 0
            ),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void modifyWhenDamage(DamageSource source, float amount, CallbackInfo ci, float f) {
        AbsorptionAccessor accessor = (AbsorptionAccessor)this;
        accessor.smartAbsorption$addStatusEffectAbsorptionAmount(-(f - amount));
        if (accessor.smartAbsorption$getStatusEffectAbsorptionAmount() <= 0) {
            ((LivingEntity) (Object) this).removeStatusEffect(StatusEffects.ABSORPTION);
        }
    }
}

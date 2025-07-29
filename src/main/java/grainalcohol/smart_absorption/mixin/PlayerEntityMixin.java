package grainalcohol.smart_absorption.mixin;

import grainalcohol.smart_absorption.AbsorptionAccessor;
import grainalcohol.smart_absorption.SmartAbsorption;
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
        SmartAbsorption.LOGGER.error("受伤前：{}", accessor.smartAbsorption$getStatusEffectAbsorptionAmount());
        accessor.smartAbsorption$addStatusEffectAbsorptionAmount(-(f - amount));
        SmartAbsorption.LOGGER.error("受伤后：{}", accessor.smartAbsorption$getStatusEffectAbsorptionAmount());
        if (accessor.smartAbsorption$getStatusEffectAbsorptionAmount() <= 0) {
            ((LivingEntity) (Object) this).removeStatusEffect(StatusEffects.ABSORPTION);
        }
    }
}

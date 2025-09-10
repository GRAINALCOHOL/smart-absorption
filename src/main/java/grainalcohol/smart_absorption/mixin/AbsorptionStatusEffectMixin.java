package grainalcohol.smart_absorption.mixin;

import grainalcohol.smart_absorption.AbsorptionAccessor;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.AbsorptionStatusEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AbsorptionStatusEffect.class)
public class AbsorptionStatusEffectMixin {
    @Redirect(
            method = "onRemoved",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;setAbsorptionAmount(F)V"
            )
    )
    private void redirectSetAbsorptionAmount(LivingEntity entity, float amount) {
        if (entity instanceof AbsorptionAccessor accessor) {
            float current = accessor.smartAbsorption$getStatusEffectAbsorptionAmount();
            if (current > 0) {
                entity.setAbsorptionAmount(entity.getAbsorptionAmount() - current);
            }
        } else {
            entity.setAbsorptionAmount(amount);
        }
    }
}

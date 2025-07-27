package cn.grainalcohol.mixin;

import cn.grainalcohol.AbsorptionAccessor;
import cn.grainalcohol.SmartAbsorption;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.AbsorptionStatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin implements AbsorptionAccessor {
    @Unique private float statusEffectAbsorptionAmount = 0;

    @Override
    public float smartAbsorption$getStatusEffectAbsorptionAmount() {
        return this.statusEffectAbsorptionAmount;
    }

    @Override
    public void smartAbsorption$setStatusEffectAbsorptionAmount(float amount) {
        this.statusEffectAbsorptionAmount = Math.max(0, amount);
    }

    @Override
    public void smartAbsorption$addStatusEffectAbsorptionAmount(float amount) {
        smartAbsorption$setStatusEffectAbsorptionAmount(smartAbsorption$getStatusEffectAbsorptionAmount() + amount);
    }

    @Inject(method = "applyDamage", at = @At("HEAD"))
    private void onDamageHead(DamageSource source, float amount, CallbackInfo ci) {
        SmartAbsorption.LOGGER.info("伤害处理开始 - 来源: {}, 伤害值: {}", source.getName(), amount);
    }

    @Inject(
            method = "applyDamage",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;setAbsorptionAmount(F)V",
                    shift = At.Shift.AFTER,
                    ordinal = 0
            ),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void modifyWhenDamage(DamageSource source, float amount, CallbackInfo ci, float f) {
        smartAbsorption$addStatusEffectAbsorptionAmount(-(f - amount));
        SmartAbsorption.LOGGER.error("受伤后: {}({})", this.smartAbsorption$getStatusEffectAbsorptionAmount(), f);
        if (this.smartAbsorption$getStatusEffectAbsorptionAmount() <= 0) {
            ((LivingEntity) (Object) this).removeStatusEffect(StatusEffects.ABSORPTION);
        }
    }

    @Inject(method = "onStatusEffectApplied", at = @At("HEAD"))
    private void modifyWhenApplied(StatusEffectInstance effect, Entity source, CallbackInfo ci) {
        if (!((LivingEntity)(Object)this).getWorld().isClient() && effect.getEffectType() instanceof AbsorptionStatusEffect) {
            System.out.println("添加前: " + this.smartAbsorption$getStatusEffectAbsorptionAmount());
            smartAbsorption$setStatusEffectAbsorptionAmount((effect.getAmplifier() + 1) * 4);
            System.out.println("添加后: " + this.smartAbsorption$getStatusEffectAbsorptionAmount());
        }
    }

    @Inject(method = "onStatusEffectRemoved", at = @At("HEAD"))
    private void modifyWhenRemoved(StatusEffectInstance effect, CallbackInfo ci) {
        if (!((LivingEntity)(Object)this).getWorld().isClient() && effect.getEffectType() instanceof AbsorptionStatusEffect) {
            smartAbsorption$setStatusEffectAbsorptionAmount(0);
            System.out.println("移除后: " + this.smartAbsorption$getStatusEffectAbsorptionAmount());
        }
    }

    @Inject(method = "onStatusEffectUpgraded", at = @At("HEAD"))
    private void modifyWhenUpgraded(StatusEffectInstance effect, boolean reapplyEffect, Entity source, CallbackInfo ci){
        if (!((LivingEntity)(Object)this).getWorld().isClient() && effect.getEffectType() instanceof AbsorptionStatusEffect) {
            System.out.println("升级前: " + this.smartAbsorption$getStatusEffectAbsorptionAmount());
            smartAbsorption$setStatusEffectAbsorptionAmount((effect.getAmplifier() + 1) * 4);
            System.out.println("升级后: " + this.smartAbsorption$getStatusEffectAbsorptionAmount());
        }
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void writeStatusEffectAbsorptionToNbt(NbtCompound nbt, CallbackInfo ci) {
        nbt.putFloat("StatusEffectAbsorption", smartAbsorption$getStatusEffectAbsorptionAmount());
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void readStatusEffectAbsorptionFromNbt(NbtCompound nbt, CallbackInfo ci) {
        if (nbt.contains("StatusEffectAbsorption")) {
            smartAbsorption$setStatusEffectAbsorptionAmount(nbt.getFloat("StatusEffectAbsorption"));
        }
    }
}

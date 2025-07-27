# Smart Absorption

**| >English< | [简体中文](README-zh_cn.md) |**

![Banner](smart_absorption_title.png)

## 📑 Introduction
In vanilla Minecraft, when players have absorption health (commonly called **golden hearts**) from both status effects and other sources (like some mods), all golden hearts are calculated together. This causes an issue:

> **When having an Absorption status effect, if you gain additional golden hearts through other means, that Absorption effect will persist until all golden hearts are depleted, preventing you from obtaining new Absorption effects during this period.**

This problem isn't very noticeable in vanilla Minecraft, but can be critical in RPG-style modpacks where every golden heart matters.

This mod perfectly solves this issue by separating golden hearts from different sources!

## 📌 Key Features
✅ **Bug Fixes**
- Fixes the issue where Absorption status effects wouldn't be removed when golden hearts are depleted in **some** versions.

✅ **Separates Golden Hearts by Source**
- Sources include **status effects** and **other methods**.
- Prioritizes consuming golden hearts from status effects when taking damage.

✅ **High Compatibility**
- Non-intrusive modifications, should work perfectly with most mods.

## 💭 FAQ
Q: Does this mod significantly affect vanilla mechanics?  
A: Not at all. You might not even notice its presence, as the modified behavior actually feels more intuitive.

Q: Which Minecraft versions are supported?
A: Primarily maintained for 1.20.1, but will continue updating for other versions. Please submit an issue if you have urgent needs for other versions.

Q: Forge/neoForge/Quilt versions?
A: Only Fabric is officially supported, but third-party ports are encouraged.

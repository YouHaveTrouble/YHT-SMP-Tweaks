# Yht SMP Tweaks

> [!CAUTION]
> Options that modify items are not reversible. Items will be permanently changed.

Most features are intended to be set and forget when starting the world. Adding items to lists will change existing
items, but removing them will not revert items back to their original state.

## Preset config

This is a configuration preset that I use for my SMP.

```yaml
# Only add items here if you have custom enchantments on items that are not normally enchantable.
enchantable-items:
- totem_of_undying

# On my server the end is disabled, so this is primary method of obtaining shulkers.
endermen-spawn-with-shulker-chance: 0.05 # 5% chance

# I removed mending and multiplicative repair cost, so repairing items is more viable.
repairable-items:
  trident:
  - nautilus_shell
  shield:
  - iron_ingot
  bow:
  - string
  crossbow:
  - string
  fishing_rod:
  - string
  shears:
  - iron_ingot
  chainmail_helmet:
  - iron_ingot
  chainmail_chestplate:
  - iron_ingot
  chainmail_leggings:
  - iron_ingot
  chainmail_boots:
  - iron_ingot
```

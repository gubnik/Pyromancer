package net.team_prometheus.pyromancer.init;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.team_prometheus.pyromancer.PyromancerMod;
import net.team_prometheus.pyromancer.pyromancer_table.PyromancerTableMenu;

public class ModMenus {
    public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.MENU_TYPES, PyromancerMod.MOD_ID);
    public static final RegistryObject<MenuType<PyromancerTableMenu>> PYROMANCER_TABLE_GUI = REGISTRY.register("pyromancer_table_gui", () -> IForgeMenuType.create(PyromancerTableMenu::new));
}

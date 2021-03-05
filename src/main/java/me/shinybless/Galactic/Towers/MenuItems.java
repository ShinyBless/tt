package me.shinybless.Galactic.Towers;

import me.shinybless.Galactic.Commands.Comandos;
import me.shinybless.Galactic.Commands.Staff;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class MenuItems {

    public static ItemStack staffmenuitem1(){
        ItemStack smi1 = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta smi1meta = smi1.getItemMeta();
        smi1meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        smi1meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        smi1meta.setDisplayName("§7Scenarios");
        smi1meta.setLore(Arrays.asList(
                "§7Abre el menu de scenarios"
        ));
        smi1.setItemMeta(smi1meta);
        return smi1;
    }

    public static ItemStack staffmenuitem2(){
        ItemStack smi2 = new ItemStack(Material.NETHER_STAR);
        ItemMeta smi2meta = smi2.getItemMeta();
        smi2meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        smi2meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        smi2meta.setDisplayName("§7Config");
        smi2meta.setLore(Arrays.asList(
                "§7Abre el menu de config"
        ));
        smi2.setItemMeta(smi2meta);
        return smi2;
    }

    public static ItemStack staffscenitem1(){
        ItemStack ssi1 = new ItemStack(Material.IRON_PICKAXE);
        ItemMeta ssi1meta = ssi1.getItemMeta();
        ssi1meta.setDisplayName("§bHasteyBoys");
        ssi1meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        ssi1meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        ssi1meta.setLore(Arrays.asList(
                "§7Activa/desactiva HasteyBoys"
        ));
        ssi1.setItemMeta(ssi1meta);
        return ssi1;
    }

    public static ItemStack staffscenitem2(){
        ItemStack ssi2 = new ItemStack(Material.IRON_INGOT);
        ItemMeta ssi2meta = ssi2.getItemMeta();
        ssi2meta.setDisplayName("§eCutClean");
        ssi2meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        ssi2meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        ssi2meta.setLore(Arrays.asList(
                "§7Activa/desactiva CutClean"
        ));
        ssi2.setItemMeta(ssi2meta);
        return ssi2;
    }

    public static ItemStack staffscenitem3(){
        ItemStack ssi3 = new ItemStack(Material.DIAMOND_BOOTS);
        ItemMeta ssi3meta = ssi3.getItemMeta();
        ssi3meta.setDisplayName("§fNoFall");
        ssi3meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        ssi3meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        ssi3meta.setLore(Arrays.asList(
                "§7Activa/desactiva NoFall"
        ));
        ssi3.setItemMeta(ssi3meta);
        return ssi3;
    }

    public static ItemStack staffscenitem4(){
        ItemStack ssi4 = new ItemStack(Material.ARROW);
        ItemMeta ssi4meta = ssi4.getItemMeta();
        ssi4meta.setDisplayName("§aSwitcheroo");
        ssi4meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        ssi4meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        ssi4meta.setLore(Arrays.asList(
                "§7Activa/desactiva Switcheroo"
        ));
        ssi4.setItemMeta(ssi4meta);
        return ssi4;
    }

    public static ItemStack staffscenitem5(){
        ItemStack ssi5 = new ItemStack(Material.BEACON);
        ItemMeta ssi5meta = ssi5.getItemMeta();
        ssi5meta.setDisplayName("§6SuperHeroes");
        ssi5meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        ssi5meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        ssi5meta.setLore(Arrays.asList(
                "§7Activa/desactiva SuperHeroes"
        ));
        ssi5.setItemMeta(ssi5meta);
        return ssi5;
    }

    public static ItemStack staffscenitem6(){
        ItemStack ssi6 = new ItemStack(Material.BOW);
        ItemMeta ssi6meta = ssi6.getItemMeta();
        ssi6meta.setDisplayName("§6Bowless");
        ssi6meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        ssi6meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        ssi6meta.setLore(Arrays.asList(
                "§7Activa/desactiva Bowless"
        ));
        ssi6.setItemMeta(ssi6meta);
        return ssi6;
    }

    public static ItemStack staffscenitem7(){
        ItemStack ssi7 = new ItemStack(Material.GHAST_TEAR);
        ItemMeta ssi7meta = ssi7.getItemMeta();
        ssi7meta.setDisplayName("§3Vengeful Spirits");
        ssi7meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        ssi7meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        ssi7meta.setLore(Arrays.asList(
                "§7Activa/desactiva VengefulSpirits"
        ));
        ssi7.setItemMeta(ssi7meta);
        return ssi7;
    }

    public static ItemStack staffscenitem8(){
        ItemStack ssi8 = new ItemStack(Material.ENDER_PEARL);
        ItemMeta ssi8meta = ssi8.getItemMeta();
        ssi8meta.setDisplayName("§2TeamSwap");
        ssi8meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        ssi8meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        ssi8meta.setLore(Arrays.asList(
                "§7Activa/desactiva TeamSwap"
        ));
        ssi8.setItemMeta(ssi8meta);
        return ssi8;
    }

    public static ItemStack staffscenitem9(){
        ItemStack ssi9 = new ItemStack(Material.CHEST);
        ItemMeta ssi9meta = ssi9.getItemMeta();
        ssi9meta.setDisplayName("§6TeamInventory");
        ssi9meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        ssi9meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        ssi9meta.setLore(Arrays.asList(
                "§7Activa/desactiva TeamInventory"
        ));
        ssi9.setItemMeta(ssi9meta);
        return ssi9;
    }

    public static ItemStack staffscenitem10(){
        ItemStack ssi10 = new ItemStack(Material.BLAZE_POWDER);
        ItemMeta ssi10meta = ssi10.getItemMeta();
        ssi10meta.setDisplayName("§4ola");
        ssi10meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        ssi10meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        ssi10meta.setLore(Arrays.asList(
                "§7Activa/desactiva ola"
        ));
        ssi10.setItemMeta(ssi10meta);
        return ssi10;
    }

    public static ItemStack staffconfigitem1(){
        ItemStack sci1 = new ItemStack(Material.BOW);
        ItemMeta sci1meta = sci1.getItemMeta();
        sci1meta.setDisplayName("§6Modalidad");
        sci1meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        sci1meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        if (Comandos.Towers) {
            sci1meta.setLore(Arrays.asList(
                    "§7Cambia entre la modalidad",
                    "§7Modalidad actual: §6Towers"
            ));
        }
        if (Comandos.Walls) {
            sci1meta.setLore(Arrays.asList(
                    "§7Cambia entre la modalidad",
                    "§7Modalidad actual: §6Walls"
            ));
        }
        if (Comandos.Bingo) {
            sci1meta.setLore(Arrays.asList(
                    "§7Cambia entre la modalidad",
                    "§7Modalidad actual: §6Bingo"
            ));
        }
        sci1.setItemMeta(sci1meta);
        return sci1;
    }

    public static ItemStack staffconfigitem2() {
        ItemStack sci2 = new ItemStack(Material.COMPARATOR);
        ItemMeta sci2meta = sci2.getItemMeta();
        sci2meta.setDisplayName("§6Config");
        sci2meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        sci2meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        if (Comandos.Towers) {
            sci2meta.setLore(Arrays.asList(
                    "§7Abre la config de la modalidad elegida",
                    "§7Modalidad actual: §6Towers"
            ));
        } else if (Comandos.Walls) {
            sci2meta.setLore(Arrays.asList(
                    "§7Abre la config de la modalidad elegida",
                    "§7Modalidad actual: §6Walls"
            ));
        } else if (Comandos.Bingo) {
            sci2meta.setLore(Arrays.asList(
                    "§7Abre la config de la modalidad elegida",
                    "§7Modalidad actual: §6Bingo"
            ));
        }
        sci2.setItemMeta(sci2meta);
        return sci2;
    }

    public static ItemStack staffconfigitem3(){
        ItemStack sci3 = new ItemStack(Material.TOTEM_OF_UNDYING);
        ItemMeta sci3meta = sci3.getItemMeta();
        sci3meta.setDisplayName("§6Teams");
        sci3meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        sci3meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        if (Comandos.Captains) {
            sci3meta.setLore(Arrays.asList(
                    "§7Cambia entre el tipo de teams",
                    "§7Teams actuales: §6Captains"
            ));
        }
        if (Comandos.SlaveMarket) {
            sci3meta.setLore(Arrays.asList(
                    "§7Cambia entre el tipo de teams",
                    "§7Teams actuales: §6SlaveMarket"
            ));
        }
        if (Comandos.Random) {
            sci3meta.setLore(Arrays.asList(
                    "§7Cambia entre el tipo de teams",
                    "§7Teams actuales: §6Random"
            ));
        }
        if (Comandos.Rigged) {
            sci3meta.setLore(Arrays.asList(
                    "§7Cambia entre el tipo de teams",
                    "§7Teams actuales: §6Rigged"
            ));
        }
        if (Comandos.Chosen) {
            sci3meta.setLore(Arrays.asList(
                    "§7Cambia entre el tipo de teams",
                    "§7Teams actuales: §6Chosen"
            ));
        }
        if (Comandos.InCaptains) {
            sci3meta.setLore(Arrays.asList(
                    "§7Cambia entre el tipo de teams",
                    "§7Teams actuales: §6InCaptains"
            ));
        }
        sci3.setItemMeta(sci3meta);
        return sci3;
    }

    public static ItemStack stafftowersitem1(){
        ItemStack sti1 = new ItemStack(Material.FEATHER);
        ItemMeta sti1meta = sti1.getItemMeta();
        sti1meta.setDisplayName("§fSky");
        sti1meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        sti1meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        if (Comandos.SkyOff){
            sti1meta.setLore(Arrays.asList(
                    "§7Activa/desactiva el Sky",
                    "§7Actual: §4OFF"
            ));
        } else {
            sti1meta.setLore(Arrays.asList(
                    "§7Activa/desactiva el Sky",
                    "§7Actual: §2ON"
            ));
        }
        sti1.setItemMeta(sti1meta);
        return sti1;
    }

    public static ItemStack stafftowersitem2(){
        ItemStack sti2 = new ItemStack(Material.ENCHANTING_TABLE);
        ItemMeta sti2meta = sti2.getItemMeta();
        sti2meta.setDisplayName("§dEnchants");
        sti2meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        sti2meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        if (Comandos.EnchantsON){
            sti2meta.setLore(Arrays.asList(
                    "§7Activa/desactiva/nerfea los Enchants",
                    "§7Actual: §2ON"
            ));
        } else if (Comandos.EnchantsNerf){
            sti2meta.setLore(Arrays.asList(
                    "§7Activa/desactiva/nerfea los Enchants",
                    "§7Actual: §fNerf"
            ));
        } else if (Comandos.EnchantsOFF){
            sti2meta.setLore(Arrays.asList(
                    "§7Activa/desactiva/nerfea los Enchants",
                    "§7Actual: §4OFF"
            ));
        }
        sti2.setItemMeta(sti2meta);
        return sti2;
    }

    public static ItemStack stafftowersitem3(){
        ItemStack sti3 = new ItemStack(Material.NETHER_BRICK);
        ItemMeta sti3meta = sti3.getItemMeta();
        sti3meta.setDisplayName("§cUnder");
        sti3meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        sti3meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        if (Comandos.UnderOff){
            sti3meta.setLore(Arrays.asList(
                    "§7Activa/desactiva el Under",
                    "§7Actual: §4OFF"
            ));
        } else {
            sti3meta.setLore(Arrays.asList(
                    "§7Activa/desactiva el Under",
                    "§7Actual: §2ON"
            ));
        }
        sti3.setItemMeta(sti3meta);
        return sti3;
    }

    public static ItemStack HasteyBoysMenu(){
        ItemStack hb = new ItemStack(Material.IRON_PICKAXE);
        ItemMeta hbmeta = hb.getItemMeta();
        hbmeta.setDisplayName("§bHasteyBoys");
        hbmeta.setLore(Arrays.asList(
                "§7Encanta tus herramientas con §bEficiencia III",
                "§7y §bDurabilidad 1"
        ));
        hb.setItemMeta(hbmeta);
        return hb;
    }

    public static ItemStack CutCleanMenu(){
        ItemStack cc = new ItemStack(Material.IRON_INGOT);
        ItemMeta ccmeta = cc.getItemMeta();
        ccmeta.setDisplayName("§eCutClean");
        ccmeta.setLore(Arrays.asList(
                "§7Los minerales y la comida se §ecocinan",
                "§7automaticamente al dropear"
        ));
        cc.setItemMeta(ccmeta);
        return cc;
    }

    public static ItemStack NoFallMenu(){
        ItemStack nf = new ItemStack(Material.DIAMOND_BOOTS);
        ItemMeta nfmeta = nf.getItemMeta();
        nfmeta.setDisplayName("§fNoFall");
        nfmeta.setLore(Arrays.asList(
                "§7No hay §fdaño de caida"
        ));
        nf.setItemMeta(nfmeta);
        return nf;
    }

    public static ItemStack SwitcherooMenu(){
        ItemStack sw = new ItemStack(Material.ARROW);
        ItemMeta swmeta = sw.getItemMeta();
        swmeta.setDisplayName("§aSwitcheroo");
        swmeta.setLore(Arrays.asList(
                "§aIntercambia posiciones §7con un jugador al",
                "§7dispararle una flecha"
        ));
        sw.setItemMeta(swmeta);
        return sw;
    }

    public static ItemStack SkyOffMenu(){
        ItemStack so = new ItemStack(Material.FEATHER);
        ItemMeta someta = so.getItemMeta();
        someta.setDisplayName("§fSkyOff");
        someta.setLore(Arrays.asList(
                "§7El limite de §faltura para construir es y210"
        ));
        so.setItemMeta(someta);
        return so;
    }

    public static ItemStack SuperHeroesMenu(){
        ItemStack uo = new ItemStack(Material.NETHER_BRICK);
        ItemMeta uometa = uo.getItemMeta();
        uometa.setDisplayName("§cUnderOff");
        uometa.setLore(Arrays.asList(
                "§7No se puede construir por §cdebajo de la base",
                "§7Si te caes es una excepción, pero solo podés subir"
        ));
        uo.setItemMeta(uometa);
        return uo;
    }

    public static ItemStack BowlessMenu(){
        ItemStack uo = new ItemStack(Material.NETHER_BRICK);
        ItemMeta uometa = uo.getItemMeta();
        uometa.setDisplayName("§cUnderOff");
        uometa.setLore(Arrays.asList(
                "§7No se puede construir por §cdebajo de la base",
                "§7Si te caes es una excepción, pero solo podés subir"
        ));
        uo.setItemMeta(uometa);
        return uo;
    }

    public static ItemStack VengefulSpiritsMenu(){
        ItemStack uo = new ItemStack(Material.NETHER_BRICK);
        ItemMeta uometa = uo.getItemMeta();
        uometa.setDisplayName("§cUnderOff");
        uometa.setLore(Arrays.asList(
                "§7No se puede construir por §cdebajo de la base",
                "§7Si te caes es una excepción, pero solo podés subir"
        ));
        uo.setItemMeta(uometa);
        return uo;
    }

    public static ItemStack TeamSwapMenu(){
        ItemStack uo = new ItemStack(Material.NETHER_BRICK);
        ItemMeta uometa = uo.getItemMeta();
        uometa.setDisplayName("§cUnderOff");
        uometa.setLore(Arrays.asList(
                "§7No se puede construir por §cdebajo de la base",
                "§7Si te caes es una excepción, pero solo podés subir"
        ));
        uo.setItemMeta(uometa);
        return uo;
    }

    public static ItemStack TeamInventoryMenu(){
        ItemStack uo = new ItemStack(Material.NETHER_BRICK);
        ItemMeta uometa = uo.getItemMeta();
        uometa.setDisplayName("§cUnderOff");
        uometa.setLore(Arrays.asList(
                "§7No se puede construir por §cdebajo de la base",
                "§7Si te caes es una excepción, pero solo podés subir"
        ));
        uo.setItemMeta(uometa);
        return uo;
    }

    public static ItemStack aaaaMenu(){
        ItemStack uo = new ItemStack(Material.NETHER_BRICK);
        ItemMeta uometa = uo.getItemMeta();
        uometa.setDisplayName("§cUnderOff");
        uometa.setLore(Arrays.asList(
                "§7No se puede construir por §cdebajo de la base",
                "§7Si te caes es una excepción, pero solo podés subir"
        ));
        uo.setItemMeta(uometa);
        return uo;
    }
}

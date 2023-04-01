package be.nadtum.jobs.Builder;

import org.bukkit.Material;

public class StainBuilder {

    private Material material;
    private Integer amount;

    public StainBuilder(Material material, Integer amount) {
        this.material = material;
        this.amount = amount;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}

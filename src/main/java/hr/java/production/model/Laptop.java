package hr.java.production.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public final class Laptop extends Item implements Tehnical, Serializable {
    private Integer garancija;

    public Laptop(String name, Category category, BigDecimal width, BigDecimal height, BigDecimal length,
                  BigDecimal productionCost, BigDecimal sellingPrice, Discount discount, Integer garancija, Long id) {
        super(name, category, width, height, length, productionCost, sellingPrice, discount, id);
        this.garancija = garancija;
    }

    @Override
    public Integer getGarancija() {
        return garancija;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Laptop laptop = (Laptop) o;
        return Objects.equals(garancija, laptop.garancija);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), garancija);
    }

    @Override
    public String toString() {
        return "Laptop{" +
                "garancija=" + garancija +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}

package hr.java.production.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class Hamburger extends Item implements Edible, Serializable {
    private final Integer BROJ_KALORIJA_KG = 4000;
    private BigDecimal weight;

    public Hamburger(String name,Category category, BigDecimal width, BigDecimal height, BigDecimal length,
                BigDecimal productionCost, BigDecimal sellingPrice,BigDecimal weight, Discount discount, Long id){
        super(name,category,width,height,length,productionCost,sellingPrice,discount, id);
        this.weight = weight;
    }

    public Integer getBrojKalorijaKG() {
        return BROJ_KALORIJA_KG;
    }
    public BigDecimal getWeight() {
        return weight;
    }
    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    @Override
    public Integer calculateKilocalories() {
        return BROJ_KALORIJA_KG * weight.intValue();
    }

    @Override
    public BigDecimal calculatePrice() {
        return weight.multiply(super.getSellingPrice());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Hamburger hamburger = (Hamburger) o;
        return Objects.equals(BROJ_KALORIJA_KG, hamburger.BROJ_KALORIJA_KG) && Objects.equals(weight, hamburger.weight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), BROJ_KALORIJA_KG, weight);
    }

    @Override
    public String toString() {
        return "Hamburger{" +
                "weight=" + weight +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}

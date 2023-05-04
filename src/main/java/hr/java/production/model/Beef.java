package hr.java.production.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class Beef extends Item implements Edible, Serializable {
    private final Integer BROJ_KALORIJA_KG = 2505;
    private BigDecimal weight;

    public Beef(String name, Category category, BigDecimal width, BigDecimal height, BigDecimal length,
                     BigDecimal productionCost, BigDecimal sellingPrice,BigDecimal weight,Discount discount, Long id){
        super(name ,category,width,height,length,productionCost,sellingPrice,discount, id);
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
        return weight.intValue() * BROJ_KALORIJA_KG;
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
        Beef beef = (Beef) o;
        return Objects.equals(BROJ_KALORIJA_KG, beef.BROJ_KALORIJA_KG) && Objects.equals(weight, beef.weight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), BROJ_KALORIJA_KG, weight);
    }

    @Override
    public String toString() {
        return "Beef{" +
                "weight=" + weight +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}

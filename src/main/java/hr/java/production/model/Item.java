package hr.java.production.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class Item extends NamedEntity implements Serializable {
    private Category category;
    private BigDecimal width;
    private BigDecimal height;
    private BigDecimal length;
    private BigDecimal productionCost;
    private BigDecimal sellingPrice;
    private Discount discount;

    public Item(String name, Category category, BigDecimal width, BigDecimal height, BigDecimal length,
                BigDecimal productionCost, BigDecimal sellingPrice, Discount discount, Long id)
    {
        super(name, id);
        this.category = category;
        this.width = width;
        this.height = height;
        this.length = length;
        this.productionCost = productionCost;
        BigDecimal discount_ = sellingPrice.multiply(discount.discountAmount().divide(BigDecimal.valueOf(100)));
        this.sellingPrice = sellingPrice.subtract(discount_);
        this.discount = discount;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public BigDecimal getVolume(){
        return width.multiply(height).multiply(length);
    }
    public String getName() {return super.name;}
    public Category getCategory() {return category;}
    public void setCategory(Category category) {this.category = category;}
    public BigDecimal getWidth() {return width;}
    public void setWidth(BigDecimal width) {this.width = width;}
    public BigDecimal getHeight() {return height;}
    public void setHeight(BigDecimal height) {this.height = height;}
    public BigDecimal getLength() {return length;}
    public void setLength(BigDecimal length) {this.length = length;}
    public BigDecimal getProductionCost() {return productionCost;}
    public void setProductionCost(BigDecimal productionCost) {this.productionCost = productionCost;}
    public BigDecimal getSellingPrice() {return sellingPrice;}
    public void setSellingPrice(BigDecimal sellingPrice) {this.sellingPrice = sellingPrice;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hamburger || o instanceof Laptop || o instanceof Beef)) return false;
        Item item = (Item) o;
        return Objects.equals(category, item.category) && Objects.equals(width, item.width) && Objects.equals(height, item.height) && Objects.equals(length, item.length) && Objects.equals(productionCost, item.productionCost) && Objects.equals(sellingPrice, item.sellingPrice) && Objects.equals(discount, item.discount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), category, width, height, length, productionCost, sellingPrice, discount);
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name +
                ", category=" + category +
                ", width=" + width +
                ", height=" + height +
                ", length=" + length +
                ", productionCost=" + productionCost +
                ", sellingPrice=" + sellingPrice +
                ", discount=" + discount +
                '}';
    }
}

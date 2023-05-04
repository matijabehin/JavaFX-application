package hr.java.production.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Store extends NamedEntity implements Serializable {
    private String webAddress;
    private Set<Item> items;

    public Store(String name, String webAddress, Set<Item> items, Long id) {
        super(name, id);
        this.webAddress = webAddress;
        this.items = items;
    }
    public Store(String name, String webAddress, List<Item> items, Long id) {
        super(name, id);
        this.webAddress = webAddress;
        this.items = items.stream().collect(Collectors.toSet());
    }
    public String getWebAddress() {return webAddress;}
    public void setWebAddress(String webAddress) {this.webAddress = webAddress;}
    public Set<Item> getItems() {return items;}
    public void setItems(Set<Item> items) {this.items = items;}
    public Integer getNumOfItems(){return items.size();}
    public Item getItemByIndex(int index){
        List<Item> items_ = new ArrayList<>(items);
        return items_.get(index);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Store store = (Store) o;
        return Objects.equals(webAddress, store.webAddress) && Objects.equals(items, store.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), webAddress, items);
    }

    @Override
    public String toString() {
        return "Store{" +
                "id='" + id + '\'' +
                ", name=" + name +
                ", webAddress='" + webAddress + '\'' +
                ", items=" + items +
                '}';
    }
}

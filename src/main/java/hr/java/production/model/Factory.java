package hr.java.production.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Factory extends NamedEntity implements Serializable {
    private Adress adress;
    private Set<Item> items;

    public Factory(String name, Adress adress, Set<Item> items, Long id) {
        super(name,id);
        this.adress = adress;
        this.items = items;
    }
    public Factory(String name, Adress adress, List<Item> items, Long id) {
        super(name,id);
        this.adress = adress;
        this.items = items.stream().collect(Collectors.toSet());
    }

    public Adress getAdress() {return adress;}
    public void setAdress(Adress adress) {this.adress = adress;}
    public Set<Item> getItems() {return items;}
    public void setItems(Set<Item> items) {this.items = items;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Factory factory = (Factory) o;
        return Objects.equals(adress, factory.adress) && Objects.equals(items, factory.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), adress, items);
    }

    @Override
    public String toString() {
        return "Factory{" +
                "id=" + id +
                ", name=" + name +
                ", items='" + items + '\'' +
                ", adress=" + adress +
                '}';
    }
}

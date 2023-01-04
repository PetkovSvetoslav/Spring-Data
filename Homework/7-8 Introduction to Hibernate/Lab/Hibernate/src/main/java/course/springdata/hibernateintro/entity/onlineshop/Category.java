package course.springdata.hibernateintro.entity.onlineshop;

import java.util.HashSet;
import java.util.Set;

public class Category {
    private long id;
    private String name;
    private String desc;
    private Set<Stock> stocks;

    public Category() {
        this.stocks = new HashSet<>();
    }

    public Category(String name, String desc) {
        this();
        this.name = name;
        this.desc = desc;
    }

    public void addStock(Stock stock) {
        this.stocks.add(stock);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Set<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(Set<Stock> stocks) {
        this.stocks = stocks;
    }
}
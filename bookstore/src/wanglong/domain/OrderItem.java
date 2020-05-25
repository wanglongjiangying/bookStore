package wanglong.domain;

import java.io.Serializable;
public class OrderItem implements Serializable {

    private Order order;
    private Product product;
    private Integer buynum;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getBuynum() {
        return buynum;
    }

    public void setBuynum(Integer buynum) {
        this.buynum = buynum;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "order=" + order +
                ", product=" + product +
                ", buynum=" + buynum +
                '}';
    }
}

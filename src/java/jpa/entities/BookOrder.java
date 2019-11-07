/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author User
 */
@Entity
@Table(name = "book_order")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BookOrder.findAll", query = "SELECT b FROM BookOrder b")
    , @NamedQuery(name = "BookOrder.findByOrdersId", query = "SELECT b FROM BookOrder b WHERE b.bookOrderPK.ordersId = :ordersId")
    , @NamedQuery(name = "BookOrder.findByBookId", query = "SELECT b FROM BookOrder b WHERE b.bookOrderPK.bookId = :bookId")
    , @NamedQuery(name = "BookOrder.findByUserId", query = "SELECT b FROM BookOrder b WHERE b.userId = :userId")})
public class BookOrder implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected BookOrderPK bookOrderPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "user_id")
    private int userId;
    @JoinColumn(name = "orders_id", referencedColumnName = "orders_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Orders orders;

    public BookOrder() {
    }

    public BookOrder(BookOrderPK bookOrderPK) {
        this.bookOrderPK = bookOrderPK;
    }

    public BookOrder(BookOrderPK bookOrderPK, int userId) {
        this.bookOrderPK = bookOrderPK;
        this.userId = userId;
    }

    public BookOrder(int ordersId, int bookId) {
        this.bookOrderPK = new BookOrderPK(ordersId, bookId);
    }

    public BookOrderPK getBookOrderPK() {
        return bookOrderPK;
    }

    public void setBookOrderPK(BookOrderPK bookOrderPK) {
        this.bookOrderPK = bookOrderPK;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bookOrderPK != null ? bookOrderPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BookOrder)) {
            return false;
        }
        BookOrder other = (BookOrder) object;
        if ((this.bookOrderPK == null && other.bookOrderPK != null) || (this.bookOrderPK != null && !this.bookOrderPK.equals(other.bookOrderPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.entities.BookOrder[ bookOrderPK=" + bookOrderPK + " ]";
    }
    
}

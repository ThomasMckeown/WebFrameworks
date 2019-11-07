/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author User
 */
@Embeddable
public class BookOrderPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "orders_id")
    private int ordersId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "book_id")
    private int bookId;

    public BookOrderPK() {
    }

    public BookOrderPK(int ordersId, int bookId) {
        this.ordersId = ordersId;
        this.bookId = bookId;
    }

    public int getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(int ordersId) {
        this.ordersId = ordersId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) ordersId;
        hash += (int) bookId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BookOrderPK)) {
            return false;
        }
        BookOrderPK other = (BookOrderPK) object;
        if (this.ordersId != other.ordersId) {
            return false;
        }
        if (this.bookId != other.bookId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.entities.BookOrderPK[ ordersId=" + ordersId + ", bookId=" + bookId + " ]";
    }
    
}

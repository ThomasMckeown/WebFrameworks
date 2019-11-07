package jsf;

import jpa.entities.BookOrder;
import jsf.util.JsfUtil;
import jsf.util.PaginationHelper;
import jpa.session.BookOrderFacade;

import java.io.Serializable;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

@Named("bookOrderController")
@SessionScoped
public class BookOrderController implements Serializable {

    private BookOrder current;
    private DataModel items = null;
    @EJB
    private jpa.session.BookOrderFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public BookOrderController() {
    }

    public BookOrder getSelected() {
        if (current == null) {
            current = new BookOrder();
            current.setBookOrderPK(new jpa.entities.BookOrderPK());
            selectedItemIndex = -1;
        }
        return current;
    }

    private BookOrderFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (BookOrder) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new BookOrder();
        current.setBookOrderPK(new jpa.entities.BookOrderPK());
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            current.getBookOrderPK().setOrdersId(current.getOrders().getOrdersId());
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/resources/Bundle").getString("BookOrderCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/resources/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (BookOrder) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            current.getBookOrderPK().setOrdersId(current.getOrders().getOrdersId());
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/resources/Bundle").getString("BookOrderUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/resources/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (BookOrder) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/resources/Bundle").getString("BookOrderDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/resources/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public BookOrder getBookOrder(jpa.entities.BookOrderPK id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = BookOrder.class)
    public static class BookOrderControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            BookOrderController controller = (BookOrderController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "bookOrderController");
            return controller.getBookOrder(getKey(value));
        }

        jpa.entities.BookOrderPK getKey(String value) {
            jpa.entities.BookOrderPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new jpa.entities.BookOrderPK();
            key.setOrdersId(Integer.parseInt(values[0]));
            key.setBookId(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(jpa.entities.BookOrderPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getOrdersId());
            sb.append(SEPARATOR);
            sb.append(value.getBookId());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof BookOrder) {
                BookOrder o = (BookOrder) object;
                return getStringKey(o.getBookOrderPK());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + BookOrder.class.getName());
            }
        }

    }

}

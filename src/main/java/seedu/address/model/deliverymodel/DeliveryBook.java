package seedu.address.model.deliverymodel;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_REDO_LIMIT_REACHED;
import static seedu.address.commons.core.Messages.MESSAGE_UNDO_LIMIT_REACHED;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.UndoRedoLimitReachedException;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.UniqueDeliveryList;

public class DeliveryBook implements ReadOnlyDeliveryBook {
    private static List<DeliveryBook> deliveryBookStateList = new ArrayList<>();
    private static int deliveryBookStatePointer = -1;

    private final UniqueDeliveryList deliveries;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        deliveries = new UniqueDeliveryList();
    }

    public DeliveryBook() {}

    /**
     * Creates a DeliveryBook using Deliveries in the {@code toBeCopied}
     */
    public DeliveryBook(ReadOnlyDeliveryBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the delivery list with {@code deliveries}.
     * {@code deliveries} must not contain duplicate deliveries.
     */
    public void setDeliveries(List<Delivery> deliveries) {
        this.deliveries.setDeliveries(deliveries);
    }

    /**
     * Resets the existing data of this {@code DeliveryBook} with {@code newData}.
     */
    public void resetData(ReadOnlyDeliveryBook newData) {
        requireNonNull(newData);
        setDeliveries(newData.getDeliveryList());
    }

    //// delivery-level operations

    /**
     * Returns true if a delivery with the same identity as {@code delivery} exists in the delivery book.
     */
    public boolean hasDelivery(Delivery delivery) {
        requireNonNull(delivery);
        return deliveries.contains(delivery);
    }

    /**
     * Adds a delivery to the delivery book.
     * The delivery must not already exist in the delivery book.
     */
    public void addDelivery(Delivery d) {
        deliveries.add(d);
    }

    /**
     * Replaces the given delivery {@code target} in the list with {@code editedDelivery}.
     * {@code target} must exist in the delivery book.
     * The delivery identity of {@code editedDelivery} must not be the
     * same as another existing delivery in the delivery book.
     */
    public void setDelivery(Delivery target, Delivery editedDelivery) {
        requireNonNull(editedDelivery);

        deliveries.setDelivery(target, editedDelivery);
    }

    /**
     * Removes {@code key} from this {@code DeliveryBook}.
     * {@code key} must exist in the Delivery book.
     */
    public void removeDelivery(Delivery key) {
        deliveries.remove(key);
    }

    //// util methods

    @Override
    public ObservableList<Delivery> getDeliveryList() {
        return deliveries.asUnmodifiableObservableList();
    }

    //// undo-redo operations

    /**
     * Copies the current {@code InventoryBook} in the state list.
     */
    public void commit() {
        assert deliveryBookStatePointer < deliveryBookStateList.size();

        if (!deliveryBookStateList.isEmpty()) {
            deliveryBookStateList = deliveryBookStateList.subList(0, deliveryBookStatePointer + 1);
        }
        deliveryBookStateList.add(new DeliveryBook(this));
        deliveryBookStatePointer++;
    }

    /**
     * Shifts the current {@code InventoryBook} to the previous one in the state list.
     */
    public void undo() throws UndoRedoLimitReachedException {
        if (canUndo()) {
            deliveryBookStatePointer--;
            resetData(deliveryBookStateList.get(deliveryBookStatePointer));
        } else {
            throw new UndoRedoLimitReachedException(MESSAGE_UNDO_LIMIT_REACHED);
        }
    }

    /**
     * Shifts the current {@code InventoryBook} to the next one in the state list.
     */
    public void redo() throws UndoRedoLimitReachedException {
        if (canRedo()) {
            deliveryBookStatePointer++;
            resetData(deliveryBookStateList.get(deliveryBookStatePointer));
        } else {
            throw new UndoRedoLimitReachedException(MESSAGE_REDO_LIMIT_REACHED);
        }
    }

    private boolean canUndo() {
        return deliveryBookStatePointer > 0;
    }

    private boolean canRedo() {
        return deliveryBookStatePointer < deliveryBookStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof DeliveryBook
            && deliveries.equals(((DeliveryBook) other).deliveries));
    }

    @Override
    public int hashCode() {
        return deliveries.hashCode();
    }
}

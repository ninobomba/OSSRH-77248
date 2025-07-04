package io.github.ninobomba.commons.data.markers;

/**
 * This interface extends the IPersistentDeletable interface and represents a persistent object that can be soft deleted.
 *
 * Implementing this interface indicates that the implementing class represents an object that can be temporarily marked
 * as deleted from a persistent storage, such as a database.
 *
 * Since this interface extends IPersistentDeletable, it inherits the marker behavior of being deletable from the
 * persistent storage.
 *
 * Classes that implement this interface can provide their own implementation for the soft deletion logic if required.
 *
 * @see IPersistentDeletable
 */
public interface IPersistentSoftDeletable extends IPersistentDeletable {
}

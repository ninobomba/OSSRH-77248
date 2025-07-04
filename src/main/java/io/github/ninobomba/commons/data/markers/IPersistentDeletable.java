package io.github.ninobomba.commons.data.markers;

/**
 * This interface represents a persistent deletable object.
 *
 * Implementing this interface indicates that the implementing class represents an object that can be deleted
 * from a persistent storage, such as a database.
 *
 * This interface does not define any methods, but serves as a marker interface to identify classes that
 * can be deleted from the persistent storage.
 *
 * Classes that implement this interface can be further extended to represent different types of deletable objects.
 * For example, the interface IPersistentSoftDeletable can be used to represent objects that can be soft deleted,
 * while the interface IPersistentHardDeletable can be used to represent objects that can be hard deleted.
 *
 * @see IPersistentSoftDeletable
 * @see IPersistentHardDeletable
 */
public interface IPersistentDeletable {
}

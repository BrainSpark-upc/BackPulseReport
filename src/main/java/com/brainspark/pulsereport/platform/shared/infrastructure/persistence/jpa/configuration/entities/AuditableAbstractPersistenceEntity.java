package com.brainspark.pulsereport.platform.shared.infrastructure.persistence.jpa.configuration.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

/**
 * Abstract base class for JPA entities with automatic audit tracking capabilities.
 *
 * This abstract entity serves as a mapped superclass for all auditable domain entities
 * that require automatic tracking of creation and modification timestamps.
 *
 * Features:
 * - Automatic generation of primary key (Identity strategy)
 * - Automatic capture of creation timestamp (read-only after creation)
 * - Automatic capture of last modification timestamp (updated on any change)
 * - Uses Spring Data's AuditingEntityListener for automatic timestamp management
 *
 * This class is configured as a {@link MappedSuperclass}, meaning it will not be
 * mapped to a database table itself, but its fields will be inherited and persisted
 * in all concrete entity subclasses.
 *
 * Usage in this project:
 * Concrete entity classes should extend this class to automatically inherit auditing functionality. <b>Example:</b>
 * <pre>
 * @Entity
 * @Table (name = "users")
 * public class User extends AuditableAbstractPersistenceEntity {
 *     private String name;
 *     // ... (other fields)
 * }
 * </pre>
 *
 * @see MappedSuperclass
 * @see AuditingEntityListener
 * @see CreatedDate
 * @see LastModifiedDate
 */
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditableAbstractPersistenceEntity {

    /**
     * Primary key of the entity.
     *
     * This field serves as the unique identifier for each entity record in the database.
     * It is automatically generated using an identity strategy (auto-increment in most databases).
     *
     */
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Timestamp of when this entity was first created/persisted.
     *
     * This field is automatically populated by Spring Data's {@link AuditingEntityListener}
     * during the initial persistence of an entity. The timestamp is captured at the moment
     * of insertion and represents the creation time of the record.
     *
     * Constraints:
     * - Cannot be null (nullable = false)
     * - Cannot be updated after initial creation (updatable = false)
     * - Read-only to prevent manual modification
     */
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Date createdAt;

    /**
     * Timestamp of when this entity was last modified.
     *
     * This field is automatically updated by Spring Data's {@link AuditingEntityListener}
     * whenever the entity is modified and persisted. It tracks the most recent change
     * to any field of the entity.
     *
     * Note:
     * Initially, this timestamp is set to the same value as {@link #createdAt} during
     * the initial creation of the entity. Subsequent updates to the entity will refresh
     * this timestamp to reflect the current modification time.
     *
     * Constraints:
     * - Cannot be null (nullable = false)
     * - Can be updated on every modification (updatable = true by default)
     * - Read-only to prevent manual modification
     */
    @LastModifiedDate
    @Column(nullable = false)
    private Date updateAt;
}

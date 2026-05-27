package com.brainspark.pulsereport.platform.shared.infrastructure.persistence.jpa.strategy;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

import static io.github.encryptorcode.pluralize.Pluralize.pluralize;

/**
 * Hibernate physical naming strategy that converts identifiers to snake case.
 * Table names are also pluralized before snake-case conversion.
 *
 * @since 1.0
 * @see PhysicalNamingStrategy
 */
public class SnakeCasePhysicalNamingStrategy implements PhysicalNamingStrategy {

    @Override
    public Identifier toPhysicalCatalogName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        return this.toSnakeCase(identifier);
    }

    @Override
    public Identifier toPhysicalSchemaName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        return this.toSnakeCase(identifier);
    }

    @Override
    public Identifier toPhysicalTableName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        return this.toSnakeCase(this.toPlural(identifier));
    }

    @Override
    public Identifier toPhysicalSequenceName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        return this.toSnakeCase(identifier);
    }

    @Override
    public Identifier toPhysicalColumnName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        return this.toSnakeCase(identifier);
    }

    private Identifier toSnakeCase(Identifier identifier) {
        if (identifier == null) {
            return null;
        }

        String text = identifier.getText();

        String snakeCaseText = text
                .replaceAll("([a-z])([A-Z])", "$1_$2")
                .replaceAll("([A-Z]+)([A-Z][a-z])", "$1_$2")
                .replace('-', '_')
                .toLowerCase();

        return Identifier.toIdentifier(snakeCaseText, identifier.isQuoted());
    }

    private Identifier toPlural(Identifier identifier) {
        if (identifier == null) {
            return null;
        }

        String pluralText = pluralize(identifier.getText());

        return Identifier.toIdentifier(pluralText, identifier.isQuoted());
    }
}
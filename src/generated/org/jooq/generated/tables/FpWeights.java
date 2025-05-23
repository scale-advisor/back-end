/*
 * This file is generated by jOOQ.
 */
package org.jooq.generated.tables;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.InverseForeignKey;
import org.jooq.Name;
import org.jooq.Path;
import org.jooq.PlainSQL;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.SQL;
import org.jooq.Schema;
import org.jooq.Select;
import org.jooq.Stringly;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.generated.Keys;
import org.jooq.generated.ScaleAdvisor;
import org.jooq.generated.tables.Project.ProjectPath;
import org.jooq.generated.tables.records.FpWeightsRecord;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class FpWeights extends TableImpl<FpWeightsRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>scale_advisor.FP_WEIGHTS</code>
     */
    public static final FpWeights FP_WEIGHTS = new FpWeights();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<FpWeightsRecord> getRecordType() {
        return FpWeightsRecord.class;
    }

    /**
     * The column <code>scale_advisor.FP_WEIGHTS.FP_WEIGHTS_ID</code>.
     */
    public final TableField<FpWeightsRecord, Long> FP_WEIGHTS_ID = createField(DSL.name("FP_WEIGHTS_ID"), SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>scale_advisor.FP_WEIGHTS.PROJECT_ID</code>.
     */
    public final TableField<FpWeightsRecord, Long> PROJECT_ID = createField(DSL.name("PROJECT_ID"), SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>scale_advisor.FP_WEIGHTS.ILF_WEIGHT</code>.
     */
    public final TableField<FpWeightsRecord, BigDecimal> ILF_WEIGHT = createField(DSL.name("ILF_WEIGHT"), SQLDataType.DECIMAL(10, 1).nullable(false), this, "");

    /**
     * The column <code>scale_advisor.FP_WEIGHTS.EIF_WEIGHT</code>.
     */
    public final TableField<FpWeightsRecord, BigDecimal> EIF_WEIGHT = createField(DSL.name("EIF_WEIGHT"), SQLDataType.DECIMAL(10, 1).nullable(false), this, "");

    /**
     * The column <code>scale_advisor.FP_WEIGHTS.EI_WEIGHT</code>.
     */
    public final TableField<FpWeightsRecord, BigDecimal> EI_WEIGHT = createField(DSL.name("EI_WEIGHT"), SQLDataType.DECIMAL(10, 1).nullable(false), this, "");

    /**
     * The column <code>scale_advisor.FP_WEIGHTS.EO_WEIGHT</code>.
     */
    public final TableField<FpWeightsRecord, BigDecimal> EO_WEIGHT = createField(DSL.name("EO_WEIGHT"), SQLDataType.DECIMAL(10, 1).nullable(false), this, "");

    /**
     * The column <code>scale_advisor.FP_WEIGHTS.EQ_WEIGHT</code>.
     */
    public final TableField<FpWeightsRecord, BigDecimal> EQ_WEIGHT = createField(DSL.name("EQ_WEIGHT"), SQLDataType.DECIMAL(10, 1).nullable(false), this, "");

    /**
     * The column <code>scale_advisor.FP_WEIGHTS.CREATED_AT</code>.
     */
    public final TableField<FpWeightsRecord, LocalDateTime> CREATED_AT = createField(DSL.name("CREATED_AT"), SQLDataType.LOCALDATETIME(0).nullable(false), this, "");

    /**
     * The column <code>scale_advisor.FP_WEIGHTS.UPDATED_AT</code>.
     */
    public final TableField<FpWeightsRecord, LocalDateTime> UPDATED_AT = createField(DSL.name("UPDATED_AT"), SQLDataType.LOCALDATETIME(0).nullable(false), this, "");

    private FpWeights(Name alias, Table<FpWeightsRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private FpWeights(Name alias, Table<FpWeightsRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
    }

    /**
     * Create an aliased <code>scale_advisor.FP_WEIGHTS</code> table reference
     */
    public FpWeights(String alias) {
        this(DSL.name(alias), FP_WEIGHTS);
    }

    /**
     * Create an aliased <code>scale_advisor.FP_WEIGHTS</code> table reference
     */
    public FpWeights(Name alias) {
        this(alias, FP_WEIGHTS);
    }

    /**
     * Create a <code>scale_advisor.FP_WEIGHTS</code> table reference
     */
    public FpWeights() {
        this(DSL.name("FP_WEIGHTS"), null);
    }

    public <O extends Record> FpWeights(Table<O> path, ForeignKey<O, FpWeightsRecord> childPath, InverseForeignKey<O, FpWeightsRecord> parentPath) {
        super(path, childPath, parentPath, FP_WEIGHTS);
    }

    /**
     * A subtype implementing {@link Path} for simplified path-based joins.
     */
    public static class FpWeightsPath extends FpWeights implements Path<FpWeightsRecord> {

        private static final long serialVersionUID = 1L;
        public <O extends Record> FpWeightsPath(Table<O> path, ForeignKey<O, FpWeightsRecord> childPath, InverseForeignKey<O, FpWeightsRecord> parentPath) {
            super(path, childPath, parentPath);
        }
        private FpWeightsPath(Name alias, Table<FpWeightsRecord> aliased) {
            super(alias, aliased);
        }

        @Override
        public FpWeightsPath as(String alias) {
            return new FpWeightsPath(DSL.name(alias), this);
        }

        @Override
        public FpWeightsPath as(Name alias) {
            return new FpWeightsPath(alias, this);
        }

        @Override
        public FpWeightsPath as(Table<?> alias) {
            return new FpWeightsPath(alias.getQualifiedName(), this);
        }
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : ScaleAdvisor.SCALE_ADVISOR;
    }

    @Override
    public UniqueKey<FpWeightsRecord> getPrimaryKey() {
        return Keys.KEY_FP_WEIGHTS_PRIMARY;
    }

    @Override
    public List<ForeignKey<FpWeightsRecord, ?>> getReferences() {
        return Arrays.asList(Keys.FK_FP_WEIGHTS_PROJECT_ID);
    }

    private transient ProjectPath _project;

    /**
     * Get the implicit join path to the <code>scale_advisor.PROJECT</code>
     * table.
     */
    public ProjectPath project() {
        if (_project == null)
            _project = new ProjectPath(this, Keys.FK_FP_WEIGHTS_PROJECT_ID, null);

        return _project;
    }

    @Override
    public FpWeights as(String alias) {
        return new FpWeights(DSL.name(alias), this);
    }

    @Override
    public FpWeights as(Name alias) {
        return new FpWeights(alias, this);
    }

    @Override
    public FpWeights as(Table<?> alias) {
        return new FpWeights(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public FpWeights rename(String name) {
        return new FpWeights(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public FpWeights rename(Name name) {
        return new FpWeights(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public FpWeights rename(Table<?> name) {
        return new FpWeights(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public FpWeights where(Condition condition) {
        return new FpWeights(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public FpWeights where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public FpWeights where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public FpWeights where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public FpWeights where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public FpWeights where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public FpWeights where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public FpWeights where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public FpWeights whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public FpWeights whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }
}

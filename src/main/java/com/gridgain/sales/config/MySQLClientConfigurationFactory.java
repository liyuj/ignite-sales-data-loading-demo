package com.gridgain.sales.config;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Properties;
import javax.cache.configuration.Factory;
import javax.sql.DataSource;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheKeyConfiguration;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.QueryEntity;
import org.apache.ignite.cache.QueryIndex;
import org.apache.ignite.cache.QueryIndexType;
import org.apache.ignite.cache.store.jdbc.CacheJdbcPojoStoreFactory;
import org.apache.ignite.cache.store.jdbc.JdbcType;
import org.apache.ignite.cache.store.jdbc.JdbcTypeField;
import org.apache.ignite.cache.store.jdbc.dialect.MySQLDialect;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.DataRegionConfiguration;
import org.apache.ignite.configuration.DataStorageConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;

/** This file was generated by Ignite Web Console (04/23/2020, 16:26) **/
public class MySQLClientConfigurationFactory {
    /** Secret properties loading. **/
    private static final Properties props = new Properties();

    static {
        try (InputStream in = IgniteConfiguration.class.getClassLoader().getResourceAsStream("sales.properties")) {
            props.load(in);
        }
        catch (Exception ignored) {
            // No-op.
        }
    }

    /** Helper class for datasource creation. **/
    public static class DataSources {
        public static final MysqlDataSource INSTANCE_dsMySQL_Classicmodels = createdsMySQL_Classicmodels();

        private static MysqlDataSource createdsMySQL_Classicmodels() {
            MysqlDataSource dsMySQL_Classicmodels = new MysqlDataSource();

            dsMySQL_Classicmodels.setURL(props.getProperty("dsMySQL_Classicmodels.jdbc.url"));
            dsMySQL_Classicmodels.setUser(props.getProperty("dsMySQL_Classicmodels.jdbc.username"));
            dsMySQL_Classicmodels.setPassword(props.getProperty("dsMySQL_Classicmodels.jdbc.password"));

            return dsMySQL_Classicmodels;
        }
    }

    /**
     * Configure grid.
     * 
     * @return Ignite configuration.
     * @throws Exception If failed to construct Ignite configuration instance.
     **/
    public static IgniteConfiguration createConfiguration() throws Exception {
        IgniteConfiguration cfg = new IgniteConfiguration();

        cfg.setClientMode(true);
        cfg.setIgniteInstanceName("sales");

        TcpDiscoverySpi discovery = new TcpDiscoverySpi();

        TcpDiscoveryVmIpFinder ipFinder = new TcpDiscoveryVmIpFinder();

        ipFinder.setAddresses(Arrays.asList("127.0.0.1:47500..47510"));

        discovery.setIpFinder(ipFinder);

        cfg.setDiscoverySpi(discovery);

        cfg.setCacheKeyConfiguration(new CacheKeyConfiguration[] {
            new CacheKeyConfiguration("com.gridgain.sales.model.OrderDetailKey", "ordernumber")
        });

        DataStorageConfiguration dataStorageCfg = new DataStorageConfiguration();

        DataRegionConfiguration dataRegionCfg = new DataRegionConfiguration();

        dataRegionCfg.setPersistenceEnabled(true);

        dataStorageCfg.setDefaultDataRegionConfiguration(dataRegionCfg);

        cfg.setDataStorageConfiguration(dataStorageCfg);

        cfg.setCacheConfiguration(
            cacheCustomerCache(),
            cacheEmployeeCache(),
            cacheOfficeCache(),
            cacheOrderDetailCache(),
            cacheOrderCache(),
            cachePaymentCache(),
            cacheProductLineCache(),
            cacheProductCache()
        );

        return cfg;
    }

    /**
     * Create configuration for cache "CustomerCache".
     * 
     * @return Configured cache.
     * @throws Exception if failed to create cache configuration.
     **/
    public static CacheConfiguration cacheCustomerCache() throws Exception {
        CacheConfiguration ccfg = new CacheConfiguration();

        ccfg.setName("CustomerCache");
        ccfg.setCacheMode(CacheMode.PARTITIONED);
        ccfg.setAtomicityMode(CacheAtomicityMode.ATOMIC);
        ccfg.setBackups(1);
        ccfg.setReadFromBackup(true);
        ccfg.setCopyOnRead(true);
        ccfg.setSqlSchema("SALES");

        CacheJdbcPojoStoreFactory cacheStoreFactory = new CacheJdbcPojoStoreFactory();

        cacheStoreFactory.setDataSourceFactory(new Factory<DataSource>() {
            /** {@inheritDoc} **/
            @Override public DataSource create() {
                return DataSources.INSTANCE_dsMySQL_Classicmodels;
            };
        });

        cacheStoreFactory.setDialect(new MySQLDialect());

        cacheStoreFactory.setTypes(jdbcTypeCustomer(ccfg.getName()));

        ccfg.setCacheStoreFactory(cacheStoreFactory);

        ccfg.setReadThrough(true);
        ccfg.setWriteThrough(true);
        ccfg.setEagerTtl(true);

        ArrayList<QueryEntity> qryEntities = new ArrayList<>();

        QueryEntity qryEntity = new QueryEntity();

        qryEntity.setKeyType("java.lang.Integer");
        qryEntity.setValueType("com.gridgain.sales.model.Customer");
        qryEntity.setKeyFieldName("customernumber");

        HashSet<String> keyFields = new HashSet<>();

        keyFields.add("customernumber");

        qryEntity.setKeyFields(keyFields);

        LinkedHashMap<String, String> fields = new LinkedHashMap<>();

        fields.put("customername", "java.lang.String");
        fields.put("contactlastname", "java.lang.String");
        fields.put("contactfirstname", "java.lang.String");
        fields.put("phone", "java.lang.String");
        fields.put("addressline1", "java.lang.String");
        fields.put("addressline2", "java.lang.String");
        fields.put("city", "java.lang.String");
        fields.put("state", "java.lang.String");
        fields.put("postalcode", "java.lang.String");
        fields.put("country", "java.lang.String");
        fields.put("salesrepemployeenumber", "java.lang.Integer");
        fields.put("creditlimit", "java.math.BigDecimal");
        fields.put("customernumber", "java.lang.Integer");

        qryEntity.setFields(fields);

        ArrayList<QueryIndex> indexes = new ArrayList<>();

        QueryIndex index = new QueryIndex();

        index.setName("salesRepEmployeeNumber");
        index.setIndexType(QueryIndexType.SORTED);

        LinkedHashMap<String, Boolean> indFlds = new LinkedHashMap<>();

        indFlds.put("salesrepemployeenumber", false);

        index.setFields(indFlds);
        indexes.add(index);

        qryEntity.setIndexes(indexes);
        qryEntities.add(qryEntity);

        ccfg.setQueryEntities(qryEntities);

        return ccfg;
    }

    /**
     * Create JDBC type for "jdbcTypeCustomer".
     * 
     * @param cacheName Cache name.
     * @return Configured JDBC type.
     **/
    private static JdbcType jdbcTypeCustomer(String cacheName) {
        JdbcType type = new JdbcType();

        type.setCacheName(cacheName);
        type.setKeyType(Integer.class);
        type.setValueType("com.gridgain.sales.model.Customer");
        type.setDatabaseSchema("classicmodels");
        type.setDatabaseTable("customers");

        type.setKeyFields(new JdbcTypeField(Types.INTEGER, "customerNumber", int.class, "customernumber"));

        type.setValueFields(
            new JdbcTypeField(Types.VARCHAR, "customerName", String.class, "customername"),
            new JdbcTypeField(Types.VARCHAR, "contactLastName", String.class, "contactlastname"),
            new JdbcTypeField(Types.VARCHAR, "contactFirstName", String.class, "contactfirstname"),
            new JdbcTypeField(Types.VARCHAR, "phone", String.class, "phone"),
            new JdbcTypeField(Types.VARCHAR, "addressLine1", String.class, "addressline1"),
            new JdbcTypeField(Types.VARCHAR, "addressLine2", String.class, "addressline2"),
            new JdbcTypeField(Types.VARCHAR, "city", String.class, "city"),
            new JdbcTypeField(Types.VARCHAR, "state", String.class, "state"),
            new JdbcTypeField(Types.VARCHAR, "postalCode", String.class, "postalcode"),
            new JdbcTypeField(Types.VARCHAR, "country", String.class, "country"),
            new JdbcTypeField(Types.INTEGER, "salesRepEmployeeNumber", Integer.class, "salesrepemployeenumber"),
            new JdbcTypeField(Types.DECIMAL, "creditLimit", BigDecimal.class, "creditlimit")
        );

        return type;
    }

    /**
     * Create configuration for cache "EmployeeCache".
     * 
     * @return Configured cache.
     * @throws Exception if failed to create cache configuration.
     **/
    public static CacheConfiguration cacheEmployeeCache() throws Exception {
        CacheConfiguration ccfg = new CacheConfiguration();

        ccfg.setName("EmployeeCache");
        ccfg.setCacheMode(CacheMode.REPLICATED);
        ccfg.setAtomicityMode(CacheAtomicityMode.ATOMIC);
        ccfg.setCopyOnRead(true);
        ccfg.setSqlSchema("SALES");

        CacheJdbcPojoStoreFactory cacheStoreFactory = new CacheJdbcPojoStoreFactory();

        cacheStoreFactory.setDataSourceFactory(new Factory<DataSource>() {
            /** {@inheritDoc} **/
            @Override public DataSource create() {
                return DataSources.INSTANCE_dsMySQL_Classicmodels;
            };
        });

        cacheStoreFactory.setDialect(new MySQLDialect());

        cacheStoreFactory.setTypes(jdbcTypeEmployee(ccfg.getName()));

        ccfg.setCacheStoreFactory(cacheStoreFactory);

        ccfg.setReadThrough(true);
        ccfg.setWriteThrough(true);
        ccfg.setEagerTtl(true);

        ArrayList<QueryEntity> qryEntities = new ArrayList<>();

        QueryEntity qryEntity = new QueryEntity();

        qryEntity.setKeyType("java.lang.Integer");
        qryEntity.setValueType("com.gridgain.sales.model.Employee");
        qryEntity.setKeyFieldName("employeenumber");

        HashSet<String> keyFields = new HashSet<>();

        keyFields.add("employeenumber");

        qryEntity.setKeyFields(keyFields);

        LinkedHashMap<String, String> fields = new LinkedHashMap<>();

        fields.put("lastname", "java.lang.String");
        fields.put("firstname", "java.lang.String");
        fields.put("extension", "java.lang.String");
        fields.put("email", "java.lang.String");
        fields.put("officecode", "java.lang.String");
        fields.put("reportsto", "java.lang.Integer");
        fields.put("jobtitle", "java.lang.String");
        fields.put("employeenumber", "java.lang.Integer");

        qryEntity.setFields(fields);

        ArrayList<QueryIndex> indexes = new ArrayList<>();

        QueryIndex index = new QueryIndex();

        index.setName("officeCode");
        index.setIndexType(QueryIndexType.SORTED);

        LinkedHashMap<String, Boolean> indFlds = new LinkedHashMap<>();

        indFlds.put("officecode", false);

        index.setFields(indFlds);
        indexes.add(index);

        index = new QueryIndex();

        index.setName("reportsTo");
        index.setIndexType(QueryIndexType.SORTED);

        indFlds = new LinkedHashMap<>();

        indFlds.put("reportsto", false);

        index.setFields(indFlds);
        indexes.add(index);

        qryEntity.setIndexes(indexes);
        qryEntities.add(qryEntity);

        ccfg.setQueryEntities(qryEntities);

        return ccfg;
    }

    /**
     * Create JDBC type for "jdbcTypeEmployee".
     * 
     * @param cacheName Cache name.
     * @return Configured JDBC type.
     **/
    private static JdbcType jdbcTypeEmployee(String cacheName) {
        JdbcType type = new JdbcType();

        type.setCacheName(cacheName);
        type.setKeyType(Integer.class);
        type.setValueType("com.gridgain.sales.model.Employee");
        type.setDatabaseSchema("classicmodels");
        type.setDatabaseTable("employees");

        type.setKeyFields(new JdbcTypeField(Types.INTEGER, "employeeNumber", int.class, "employeenumber"));

        type.setValueFields(
            new JdbcTypeField(Types.VARCHAR, "lastName", String.class, "lastname"),
            new JdbcTypeField(Types.VARCHAR, "firstName", String.class, "firstname"),
            new JdbcTypeField(Types.VARCHAR, "extension", String.class, "extension"),
            new JdbcTypeField(Types.VARCHAR, "email", String.class, "email"),
            new JdbcTypeField(Types.VARCHAR, "officeCode", String.class, "officecode"),
            new JdbcTypeField(Types.INTEGER, "reportsTo", Integer.class, "reportsto"),
            new JdbcTypeField(Types.VARCHAR, "jobTitle", String.class, "jobtitle")
        );

        return type;
    }

    /**
     * Create configuration for cache "OfficeCache".
     * 
     * @return Configured cache.
     * @throws Exception if failed to create cache configuration.
     **/
    public static CacheConfiguration cacheOfficeCache() throws Exception {
        CacheConfiguration ccfg = new CacheConfiguration();

        ccfg.setName("OfficeCache");
        ccfg.setCacheMode(CacheMode.REPLICATED);
        ccfg.setAtomicityMode(CacheAtomicityMode.ATOMIC);
        ccfg.setCopyOnRead(true);
        ccfg.setSqlSchema("SALES");

        CacheJdbcPojoStoreFactory cacheStoreFactory = new CacheJdbcPojoStoreFactory();

        cacheStoreFactory.setDataSourceFactory(new Factory<DataSource>() {
            /** {@inheritDoc} **/
            @Override public DataSource create() {
                return DataSources.INSTANCE_dsMySQL_Classicmodels;
            };
        });

        cacheStoreFactory.setDialect(new MySQLDialect());

        cacheStoreFactory.setTypes(jdbcTypeOffice(ccfg.getName()));

        ccfg.setCacheStoreFactory(cacheStoreFactory);

        ccfg.setReadThrough(true);
        ccfg.setWriteThrough(true);
        ccfg.setEagerTtl(true);

        ArrayList<QueryEntity> qryEntities = new ArrayList<>();

        QueryEntity qryEntity = new QueryEntity();

        qryEntity.setKeyType("java.lang.String");
        qryEntity.setValueType("com.gridgain.sales.model.Office");
        qryEntity.setKeyFieldName("officecode");

        HashSet<String> keyFields = new HashSet<>();

        keyFields.add("officecode");

        qryEntity.setKeyFields(keyFields);

        LinkedHashMap<String, String> fields = new LinkedHashMap<>();

        fields.put("city", "java.lang.String");
        fields.put("phone", "java.lang.String");
        fields.put("addressline1", "java.lang.String");
        fields.put("addressline2", "java.lang.String");
        fields.put("state", "java.lang.String");
        fields.put("country", "java.lang.String");
        fields.put("postalcode", "java.lang.String");
        fields.put("territory", "java.lang.String");
        fields.put("officecode", "java.lang.String");

        qryEntity.setFields(fields);
        qryEntities.add(qryEntity);

        ccfg.setQueryEntities(qryEntities);

        return ccfg;
    }

    /**
     * Create JDBC type for "jdbcTypeOffice".
     * 
     * @param cacheName Cache name.
     * @return Configured JDBC type.
     **/
    private static JdbcType jdbcTypeOffice(String cacheName) {
        JdbcType type = new JdbcType();

        type.setCacheName(cacheName);
        type.setKeyType(String.class);
        type.setValueType("com.gridgain.sales.model.Office");
        type.setDatabaseSchema("classicmodels");
        type.setDatabaseTable("offices");

        type.setKeyFields(new JdbcTypeField(Types.VARCHAR, "officeCode", String.class, "officecode"));

        type.setValueFields(
            new JdbcTypeField(Types.VARCHAR, "city", String.class, "city"),
            new JdbcTypeField(Types.VARCHAR, "phone", String.class, "phone"),
            new JdbcTypeField(Types.VARCHAR, "addressLine1", String.class, "addressline1"),
            new JdbcTypeField(Types.VARCHAR, "addressLine2", String.class, "addressline2"),
            new JdbcTypeField(Types.VARCHAR, "state", String.class, "state"),
            new JdbcTypeField(Types.VARCHAR, "country", String.class, "country"),
            new JdbcTypeField(Types.VARCHAR, "postalCode", String.class, "postalcode"),
            new JdbcTypeField(Types.VARCHAR, "territory", String.class, "territory")
        );

        return type;
    }

    /**
     * Create configuration for cache "OrderDetailCache".
     * 
     * @return Configured cache.
     * @throws Exception if failed to create cache configuration.
     **/
    public static CacheConfiguration cacheOrderDetailCache() throws Exception {
        CacheConfiguration ccfg = new CacheConfiguration();

        ccfg.setName("OrderDetailCache");
        ccfg.setCacheMode(CacheMode.PARTITIONED);
        ccfg.setAtomicityMode(CacheAtomicityMode.ATOMIC);
        ccfg.setBackups(1);
        ccfg.setReadFromBackup(true);
        ccfg.setCopyOnRead(true);
        ccfg.setSqlSchema("SALES");

        CacheJdbcPojoStoreFactory cacheStoreFactory = new CacheJdbcPojoStoreFactory();

        cacheStoreFactory.setDataSourceFactory(new Factory<DataSource>() {
            /** {@inheritDoc} **/
            @Override public DataSource create() {
                return DataSources.INSTANCE_dsMySQL_Classicmodels;
            };
        });

        cacheStoreFactory.setDialect(new MySQLDialect());

        cacheStoreFactory.setTypes(jdbcTypeOrderDetail(ccfg.getName()));

        ccfg.setCacheStoreFactory(cacheStoreFactory);

        ccfg.setReadThrough(true);
        ccfg.setWriteThrough(true);
        ccfg.setEagerTtl(true);

        ArrayList<QueryEntity> qryEntities = new ArrayList<>();

        QueryEntity qryEntity = new QueryEntity();

        qryEntity.setKeyType("com.gridgain.sales.model.OrderDetailKey");
        qryEntity.setValueType("com.gridgain.sales.model.OrderDetail");

        HashSet<String> keyFields = new HashSet<>();

        keyFields.add("ordernumber");

        keyFields.add("productcode");

        qryEntity.setKeyFields(keyFields);

        LinkedHashMap<String, String> fields = new LinkedHashMap<>();

        fields.put("ordernumber", "java.lang.Integer");
        fields.put("productcode", "java.lang.String");
        fields.put("quantityordered", "java.lang.Integer");
        fields.put("priceeach", "java.math.BigDecimal");
        fields.put("orderlinenumber", "java.lang.Short");

        qryEntity.setFields(fields);

        ArrayList<QueryIndex> indexes = new ArrayList<>();

        QueryIndex index = new QueryIndex();

        index.setName("productCode");
        index.setIndexType(QueryIndexType.SORTED);

        LinkedHashMap<String, Boolean> indFlds = new LinkedHashMap<>();

        indFlds.put("productcode", false);

        index.setFields(indFlds);
        indexes.add(index);

        qryEntity.setIndexes(indexes);
        qryEntities.add(qryEntity);

        ccfg.setQueryEntities(qryEntities);

        return ccfg;
    }

    /**
     * Create JDBC type for "jdbcTypeOrderDetail".
     * 
     * @param cacheName Cache name.
     * @return Configured JDBC type.
     **/
    private static JdbcType jdbcTypeOrderDetail(String cacheName) {
        JdbcType type = new JdbcType();

        type.setCacheName(cacheName);
        type.setKeyType("com.gridgain.sales.model.OrderDetailKey");
        type.setValueType("com.gridgain.sales.model.OrderDetail");
        type.setDatabaseSchema("classicmodels");
        type.setDatabaseTable("orderdetails");

        type.setKeyFields(
            new JdbcTypeField(Types.INTEGER, "orderNumber", int.class, "ordernumber"),
            new JdbcTypeField(Types.VARCHAR, "productCode", String.class, "productcode")
        );

        type.setValueFields(
            new JdbcTypeField(Types.INTEGER, "quantityOrdered", int.class, "quantityordered"),
            new JdbcTypeField(Types.DECIMAL, "priceEach", BigDecimal.class, "priceeach"),
            new JdbcTypeField(Types.SMALLINT, "orderLineNumber", short.class, "orderlinenumber")
        );

        return type;
    }

    /**
     * Create configuration for cache "OrderCache".
     * 
     * @return Configured cache.
     * @throws Exception if failed to create cache configuration.
     **/
    public static CacheConfiguration cacheOrderCache() throws Exception {
        CacheConfiguration ccfg = new CacheConfiguration();

        ccfg.setName("OrderCache");
        ccfg.setCacheMode(CacheMode.PARTITIONED);
        ccfg.setAtomicityMode(CacheAtomicityMode.ATOMIC);
        ccfg.setBackups(1);
        ccfg.setReadFromBackup(true);
        ccfg.setCopyOnRead(true);
        ccfg.setSqlSchema("SALES");

        CacheJdbcPojoStoreFactory cacheStoreFactory = new CacheJdbcPojoStoreFactory();

        cacheStoreFactory.setDataSourceFactory(new Factory<DataSource>() {
            /** {@inheritDoc} **/
            @Override public DataSource create() {
                return DataSources.INSTANCE_dsMySQL_Classicmodels;
            };
        });

        cacheStoreFactory.setDialect(new MySQLDialect());

        cacheStoreFactory.setTypes(jdbcTypeOrder(ccfg.getName()));

        ccfg.setCacheStoreFactory(cacheStoreFactory);

        ccfg.setReadThrough(true);
        ccfg.setWriteThrough(true);
        ccfg.setEagerTtl(true);

        ArrayList<QueryEntity> qryEntities = new ArrayList<>();

        QueryEntity qryEntity = new QueryEntity();

        qryEntity.setKeyType("java.lang.Integer");
        qryEntity.setValueType("com.gridgain.sales.model.Order");
        qryEntity.setKeyFieldName("ordernumber");

        HashSet<String> keyFields = new HashSet<>();

        keyFields.add("ordernumber");

        qryEntity.setKeyFields(keyFields);

        LinkedHashMap<String, String> fields = new LinkedHashMap<>();

        fields.put("orderdate", "java.sql.Date");
        fields.put("requireddate", "java.sql.Date");
        fields.put("shippeddate", "java.sql.Date");
        fields.put("status", "java.lang.String");
        fields.put("comments", "java.lang.String");
        fields.put("customernumber", "java.lang.Integer");
        fields.put("ordernumber", "java.lang.Integer");

        qryEntity.setFields(fields);

        ArrayList<QueryIndex> indexes = new ArrayList<>();

        QueryIndex index = new QueryIndex();

        index.setName("customerNumber");
        index.setIndexType(QueryIndexType.SORTED);

        LinkedHashMap<String, Boolean> indFlds = new LinkedHashMap<>();

        indFlds.put("customernumber", false);

        index.setFields(indFlds);
        indexes.add(index);

        qryEntity.setIndexes(indexes);
        qryEntities.add(qryEntity);

        ccfg.setQueryEntities(qryEntities);

        return ccfg;
    }

    /**
     * Create JDBC type for "jdbcTypeOrder".
     * 
     * @param cacheName Cache name.
     * @return Configured JDBC type.
     **/
    private static JdbcType jdbcTypeOrder(String cacheName) {
        JdbcType type = new JdbcType();

        type.setCacheName(cacheName);
        type.setKeyType(Integer.class);
        type.setValueType("com.gridgain.sales.model.Order");
        type.setDatabaseSchema("classicmodels");
        type.setDatabaseTable("orders");

        type.setKeyFields(new JdbcTypeField(Types.INTEGER, "orderNumber", int.class, "ordernumber"));

        type.setValueFields(
            new JdbcTypeField(Types.DATE, "orderDate", Date.class, "orderdate"),
            new JdbcTypeField(Types.DATE, "requiredDate", Date.class, "requireddate"),
            new JdbcTypeField(Types.DATE, "shippedDate", Date.class, "shippeddate"),
            new JdbcTypeField(Types.VARCHAR, "status", String.class, "status"),
            new JdbcTypeField(Types.LONGVARCHAR, "comments", String.class, "comments"),
            new JdbcTypeField(Types.INTEGER, "customerNumber", int.class, "customernumber")
        );

        return type;
    }

    /**
     * Create configuration for cache "PaymentCache".
     * 
     * @return Configured cache.
     * @throws Exception if failed to create cache configuration.
     **/
    public static CacheConfiguration cachePaymentCache() throws Exception {
        CacheConfiguration ccfg = new CacheConfiguration();

        ccfg.setName("PaymentCache");
        ccfg.setCacheMode(CacheMode.PARTITIONED);
        ccfg.setAtomicityMode(CacheAtomicityMode.ATOMIC);
        ccfg.setBackups(1);
        ccfg.setReadFromBackup(true);
        ccfg.setCopyOnRead(true);
        ccfg.setSqlSchema("SALES");

        CacheJdbcPojoStoreFactory cacheStoreFactory = new CacheJdbcPojoStoreFactory();

        cacheStoreFactory.setDataSourceFactory(new Factory<DataSource>() {
            /** {@inheritDoc} **/
            @Override public DataSource create() {
                return DataSources.INSTANCE_dsMySQL_Classicmodels;
            };
        });

        cacheStoreFactory.setDialect(new MySQLDialect());

        cacheStoreFactory.setTypes(jdbcTypePayment(ccfg.getName()));

        ccfg.setCacheStoreFactory(cacheStoreFactory);

        ccfg.setReadThrough(true);
        ccfg.setWriteThrough(true);
        ccfg.setEagerTtl(true);

        ArrayList<QueryEntity> qryEntities = new ArrayList<>();

        QueryEntity qryEntity = new QueryEntity();

        qryEntity.setKeyType("com.gridgain.sales.model.PaymentKey");
        qryEntity.setValueType("com.gridgain.sales.model.Payment");

        HashSet<String> keyFields = new HashSet<>();

        keyFields.add("customernumber");

        keyFields.add("checknumber");

        qryEntity.setKeyFields(keyFields);

        LinkedHashMap<String, String> fields = new LinkedHashMap<>();

        fields.put("customernumber", "java.lang.Integer");
        fields.put("checknumber", "java.lang.String");
        fields.put("paymentdate", "java.sql.Date");
        fields.put("amount", "java.math.BigDecimal");

        qryEntity.setFields(fields);

        ArrayList<QueryIndex> indexes = new ArrayList<>();

        QueryIndex index = new QueryIndex();

        index.setName("Payment_CheckNumber_Amount");
        index.setIndexType(QueryIndexType.FULLTEXT);

        LinkedHashMap<String, Boolean> indFlds = new LinkedHashMap<>();

        indFlds.put("checknumber", true);
        indFlds.put("amount", true);

        index.setFields(indFlds);
        indexes.add(index);

        qryEntity.setIndexes(indexes);
        qryEntities.add(qryEntity);

        ccfg.setQueryEntities(qryEntities);

        return ccfg;
    }

    /**
     * Create JDBC type for "jdbcTypePayment".
     * 
     * @param cacheName Cache name.
     * @return Configured JDBC type.
     **/
    private static JdbcType jdbcTypePayment(String cacheName) {
        JdbcType type = new JdbcType();

        type.setCacheName(cacheName);
        type.setKeyType("com.gridgain.sales.model.PaymentKey");
        type.setValueType("com.gridgain.sales.model.Payment");
        type.setDatabaseSchema("classicmodels");
        type.setDatabaseTable("payments");

        type.setKeyFields(
            new JdbcTypeField(Types.INTEGER, "customerNumber", int.class, "customernumber"),
            new JdbcTypeField(Types.VARCHAR, "checkNumber", String.class, "checknumber")
        );

        type.setValueFields(
            new JdbcTypeField(Types.DATE, "paymentDate", Date.class, "paymentdate"),
            new JdbcTypeField(Types.DECIMAL, "amount", BigDecimal.class, "amount")
        );

        return type;
    }

    /**
     * Create configuration for cache "ProductLineCache".
     * 
     * @return Configured cache.
     * @throws Exception if failed to create cache configuration.
     **/
    public static CacheConfiguration cacheProductLineCache() throws Exception {
        CacheConfiguration ccfg = new CacheConfiguration();

        ccfg.setName("ProductLineCache");
        ccfg.setCacheMode(CacheMode.REPLICATED);
        ccfg.setAtomicityMode(CacheAtomicityMode.ATOMIC);
        ccfg.setCopyOnRead(true);
        ccfg.setSqlSchema("SALES");

        CacheJdbcPojoStoreFactory cacheStoreFactory = new CacheJdbcPojoStoreFactory();

        cacheStoreFactory.setDataSourceFactory(new Factory<DataSource>() {
            /** {@inheritDoc} **/
            @Override public DataSource create() {
                return DataSources.INSTANCE_dsMySQL_Classicmodels;
            };
        });

        cacheStoreFactory.setDialect(new MySQLDialect());

        cacheStoreFactory.setTypes(jdbcTypeProductLine(ccfg.getName()));

        ccfg.setCacheStoreFactory(cacheStoreFactory);

        ccfg.setReadThrough(true);
        ccfg.setWriteThrough(true);
        ccfg.setEagerTtl(true);

        ArrayList<QueryEntity> qryEntities = new ArrayList<>();

        QueryEntity qryEntity = new QueryEntity();

        qryEntity.setKeyType("java.lang.String");
        qryEntity.setValueType("com.gridgain.sales.model.ProductLine");
        qryEntity.setKeyFieldName("productline");

        HashSet<String> keyFields = new HashSet<>();

        keyFields.add("productline");

        qryEntity.setKeyFields(keyFields);

        LinkedHashMap<String, String> fields = new LinkedHashMap<>();

        fields.put("textdescription", "java.lang.String");
        fields.put("htmldescription", "java.lang.String");
        fields.put("image", "[B");
        fields.put("productline", "java.lang.String");

        qryEntity.setFields(fields);
        qryEntities.add(qryEntity);

        ccfg.setQueryEntities(qryEntities);

        return ccfg;
    }

    /**
     * Create JDBC type for "jdbcTypeProductLine".
     * 
     * @param cacheName Cache name.
     * @return Configured JDBC type.
     **/
    private static JdbcType jdbcTypeProductLine(String cacheName) {
        JdbcType type = new JdbcType();

        type.setCacheName(cacheName);
        type.setKeyType(String.class);
        type.setValueType("com.gridgain.sales.model.ProductLine");
        type.setDatabaseSchema("classicmodels");
        type.setDatabaseTable("productlines");

        type.setKeyFields(new JdbcTypeField(Types.VARCHAR, "productLine", String.class, "productline"));

        type.setValueFields(
            new JdbcTypeField(Types.VARCHAR, "textDescription", String.class, "textdescription"),
            new JdbcTypeField(Types.LONGVARCHAR, "htmlDescription", String.class, "htmldescription"),
            new JdbcTypeField(Types.LONGVARBINARY, "image", byte[].class, "image")
        );

        return type;
    }

    /**
     * Create configuration for cache "ProductCache".
     * 
     * @return Configured cache.
     * @throws Exception if failed to create cache configuration.
     **/
    public static CacheConfiguration cacheProductCache() throws Exception {
        CacheConfiguration ccfg = new CacheConfiguration();

        ccfg.setName("ProductCache");
        ccfg.setCacheMode(CacheMode.REPLICATED);
        ccfg.setAtomicityMode(CacheAtomicityMode.ATOMIC);
        ccfg.setCopyOnRead(true);
        ccfg.setSqlSchema("SALES");

        CacheJdbcPojoStoreFactory cacheStoreFactory = new CacheJdbcPojoStoreFactory();

        cacheStoreFactory.setDataSourceFactory(new Factory<DataSource>() {
            /** {@inheritDoc} **/
            @Override public DataSource create() {
                return DataSources.INSTANCE_dsMySQL_Classicmodels;
            };
        });

        cacheStoreFactory.setDialect(new MySQLDialect());

        cacheStoreFactory.setTypes(jdbcTypeProduct(ccfg.getName()));

        ccfg.setCacheStoreFactory(cacheStoreFactory);

        ccfg.setReadThrough(true);
        ccfg.setWriteThrough(true);
        ccfg.setEagerTtl(true);

        ArrayList<QueryEntity> qryEntities = new ArrayList<>();

        QueryEntity qryEntity = new QueryEntity();

        qryEntity.setKeyType("java.lang.String");
        qryEntity.setValueType("com.gridgain.sales.model.Product");
        qryEntity.setKeyFieldName("productcode");

        HashSet<String> keyFields = new HashSet<>();

        keyFields.add("productcode");

        qryEntity.setKeyFields(keyFields);

        LinkedHashMap<String, String> fields = new LinkedHashMap<>();

        fields.put("productname", "java.lang.String");
        fields.put("productline", "java.lang.String");
        fields.put("productscale", "java.lang.String");
        fields.put("productvendor", "java.lang.String");
        fields.put("productdescription", "java.lang.String");
        fields.put("quantityinstock", "java.lang.Short");
        fields.put("buyprice", "java.math.BigDecimal");
        fields.put("msrp", "java.math.BigDecimal");
        fields.put("productcode", "java.lang.String");

        qryEntity.setFields(fields);

        ArrayList<QueryIndex> indexes = new ArrayList<>();

        QueryIndex index = new QueryIndex();

        index.setName("productLine");
        index.setIndexType(QueryIndexType.SORTED);

        LinkedHashMap<String, Boolean> indFlds = new LinkedHashMap<>();

        indFlds.put("productline", false);

        index.setFields(indFlds);
        indexes.add(index);

        index = new QueryIndex();

        index.setName("Product_Name_Description");
        index.setIndexType(QueryIndexType.FULLTEXT);

        indFlds = new LinkedHashMap<>();

        indFlds.put("productcode", true);
        indFlds.put("productname", true);
        indFlds.put("productdescription", true);
        indFlds.put("productvendor", true);

        index.setFields(indFlds);
        indexes.add(index);

        qryEntity.setIndexes(indexes);
        qryEntities.add(qryEntity);

        ccfg.setQueryEntities(qryEntities);

        return ccfg;
    }

    /**
     * Create JDBC type for "jdbcTypeProduct".
     * 
     * @param cacheName Cache name.
     * @return Configured JDBC type.
     **/
    private static JdbcType jdbcTypeProduct(String cacheName) {
        JdbcType type = new JdbcType();

        type.setCacheName(cacheName);
        type.setKeyType(String.class);
        type.setValueType("com.gridgain.sales.model.Product");
        type.setDatabaseSchema("classicmodels");
        type.setDatabaseTable("products");

        type.setKeyFields(new JdbcTypeField(Types.VARCHAR, "productCode", String.class, "productcode"));

        type.setValueFields(
            new JdbcTypeField(Types.VARCHAR, "productName", String.class, "productname"),
            new JdbcTypeField(Types.VARCHAR, "productLine", String.class, "productline"),
            new JdbcTypeField(Types.VARCHAR, "productScale", String.class, "productscale"),
            new JdbcTypeField(Types.VARCHAR, "productVendor", String.class, "productvendor"),
            new JdbcTypeField(Types.LONGVARCHAR, "productDescription", String.class, "productdescription"),
            new JdbcTypeField(Types.SMALLINT, "quantityInStock", short.class, "quantityinstock"),
            new JdbcTypeField(Types.DECIMAL, "buyPrice", BigDecimal.class, "buyprice"),
            new JdbcTypeField(Types.DECIMAL, "MSRP", BigDecimal.class, "msrp")
        );

        return type;
    }
}
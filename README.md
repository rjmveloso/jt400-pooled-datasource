# Pooled Data Source for IBM AS400

The AS400ConnectionPoolDataSource wrapper encapsulates an AS400JDBCConnectionPool and AS400JDBCConnectionPoolDataSource exposing the `javax.sql.DataSource` interface.

Since native AS400JDBCConnectionPool is not a data source, it's not possible to simply change the usage of a DBCP DataSource to the native pool, breaking the dependencies of `javax.sql.DataSource` across systems. This project tries to achieve this.

AS400ConnectionPoolDataSource exposes the same properties as AS400JDBCConnectionPool, and a new one `min-connections` that initializes the pool with a minimum number of connections.

Properties of AS400JDBCConnectionPoolDataSource are set by a `java.util.Properties`.
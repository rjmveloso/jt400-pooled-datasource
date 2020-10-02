package io.github.jt400.access;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.sql.DataSource;

import com.ibm.as400.access.AS400JDBCConnectionPool;
import com.ibm.as400.access.AS400JDBCConnectionPoolDataSource;
import com.ibm.as400.access.AS400JDBCDataSource;
import com.ibm.as400.access.ConnectionPoolException;

public class AS400ConnectionPoolDataSource extends AS400ConnectionPoolAdapter implements DataSource {

	private AS400JDBCConnectionPoolDataSource dsource;

	private AS400ConnectionPoolDataSource(AS400JDBCConnectionPoolDataSource dsource) {
		super(new AS400JDBCConnectionPool(dsource));
		this.dsource = dsource;
	}

	public AS400ConnectionPoolDataSource(String system) {
		this(new AS400JDBCConnectionPoolDataSource(system));
	}

	public AS400ConnectionPoolDataSource(String system, String username, String password) {
		this(new AS400JDBCConnectionPoolDataSource(system, username, password));
	}

	// should this be public allowing DataSource parameterization or use the setter
	// below based on java.util.Properties??
	AS400JDBCDataSource getWrappedDataSource() {
		return dsource;
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		return dsource.getLogWriter();
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		dsource.setLogWriter(out);
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		return dsource.getLoginTimeout();
	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		dsource.setLoginTimeout(seconds);
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return dsource.getParentLogger();
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		return dsource.unwrap(iface);
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return dsource.isWrapperFor(iface);
	}

	@Override
	public Connection getConnection() throws SQLException {
		try {
			populate(); // lazy fill of pool
			return cpool.getConnection();
		} catch (ConnectionPoolException e) {
			throw new SQLException(e);
		}
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	public void setDataSourceProperties(Properties properties) {
		dsource.setProperties(properties);
	}

}

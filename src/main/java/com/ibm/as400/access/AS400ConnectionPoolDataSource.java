package com.ibm.as400.access;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.sql.DataSource;

public class AS400ConnectionPoolDataSource extends AS400ConnectionPoolAdapter implements DataSource {

	private static final int DEFAULT_POOL_SIZE = 10;

	private AS400JDBCConnectionPoolDataSource dsource;

	// FOR TEST ONLY
	AS400ConnectionPoolDataSource() {
		setMaxConnections(DEFAULT_POOL_SIZE);
	}

	public AS400ConnectionPoolDataSource(String system) {
		dsource = new AS400JDBCConnectionPoolDataSource(system);
		initialize();
	}

	public AS400ConnectionPoolDataSource(String system, String username, String password) {
		dsource = new AS400JDBCConnectionPoolDataSource(system, username, password);
		initialize();
	}

	public AS400ConnectionPoolDataSource(String system, String username, String password, String keyRingName,
			String keyRingPassword) {
		dsource = new AS400JDBCConnectionPoolDataSource(system, username, password, keyRingName, keyRingPassword);
		initialize();
	}

	private void initialize() {
		cpool = new AS400JDBCConnectionPool(dsource);
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
		// return dsource.getParentLogger();
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		// return dsource.unwrap(iface);
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// return dsource.isWrapperFor(iface);
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public Connection getConnection() throws SQLException {
		try {
			populate();
			return cpool.getConnection();
		} catch (ConnectionPoolException e) {
			throw new SQLException(e);
		}
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	private void populate() throws ConnectionPoolException {
		if (!cpool.isInUse()) {
			synchronized (cpool) {
				int max = getMaxConnections();
				int min = getMinConnections();
				int size = Math.min(min > 0 ? min : DEFAULT_POOL_SIZE, max > 0 ? max : 0);
				cpool.fill(size);
			}
		}
	}

	public void setDataSourceProperties(Properties properties) {
		dsource.setProperties(properties);
	}

}

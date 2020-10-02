package io.github.jt400.access;

import java.beans.PropertyChangeListener;
import java.io.Closeable;
import java.io.IOException;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.ibm.as400.access.AS400JDBCConnectionPool;
import com.ibm.as400.access.ConnectionPoolException;
import com.ibm.as400.access.ConnectionPoolListener;

public abstract class AS400ConnectionPoolAdapter implements ConnectionPoolDataSource, Closeable {

	private static final int DEFAULT_POOL_SIZE = 10;

	private volatile boolean inUse;

	private int minConnections = -1;

	/**
	 * Encapsulation must be used because getConnection does not throws the same exception.<br/>
	 * We need to throw {@link SQLException} to comply with {@link DataSource}.
	 */
	protected final AS400JDBCConnectionPool cpool;

	protected AS400ConnectionPoolAdapter(AS400JDBCConnectionPool cpool) {
		this.cpool = cpool;
	}

	@Override
	public int getMaxConnections() {
		return cpool.getMaxConnections();
	}

	@Override
	public void setMaxConnections(int value) {
		cpool.setMaxConnections(value);
	}

	@Override
	public int getMinConnections() {
		return minConnections;
	}

	@Override
	public void setMinConnections(int value) {
		minConnections = value;
	}

	@Override
	public long getMaxInactivity() {
		return cpool.getMaxInactivity();
	}

	@Override
	public void setMaxInactivity(long value) {
		cpool.setMaxInactivity(value);
	}

	@Override
	public long getMaxLifetime() {
		return cpool.getMaxLifetime();
	}

	@Override
	public void setMaxLifetime(long value) {
		cpool.setMaxLifetime(value);
	}

	@Override
	public int getMaxUseCount() {
		return cpool.getMaxUseCount();
	}

	@Override
	public void setMaxUseCount(int value) {
		cpool.setMaxUseCount(value);
	}

	@Override
	public long getMaxUseTime() {
		return cpool.getMaxUseTime();
	}

	@Override
	public void setMaxUseTime(long value) {
		cpool.setMaxUseTime(value);
	}

	@Override
	public void close() throws IOException {
		cpool.close();
	}

	/**
	 * Deprecated on JDK 9+
	 */
	@Override
	protected void finalize() throws Throwable {
		close();
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		cpool.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		cpool.removePropertyChangeListener(listener);
	}

	public void addConnectionPoolListener(ConnectionPoolListener listener) {
		cpool.addConnectionPoolListener(listener);
	}

	public void removeConnectionPoolListener(ConnectionPoolListener listener) {
		cpool.removeConnectionPoolListener(listener);
	}

	protected void populate() throws ConnectionPoolException {
		if (!inUse) {
			synchronized (cpool) {
				if (!inUse) {
					int max = getMaxConnections();
					int min = getMinConnections();
					int size = Math.min(min > 0 ? min : DEFAULT_POOL_SIZE, max > 0 ? max : 0);
					cpool.fill(size);
					inUse = true;
				}
			}
		}
	}

}

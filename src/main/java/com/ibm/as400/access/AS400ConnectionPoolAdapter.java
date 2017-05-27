package com.ibm.as400.access;

public abstract class AS400ConnectionPoolAdapter implements ConnectionPoolDataSource {

	private int minConnections = -1;

	protected AS400JDBCConnectionPool cpool;

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

}

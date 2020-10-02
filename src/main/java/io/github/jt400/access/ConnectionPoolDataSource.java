package io.github.jt400.access;

interface ConnectionPoolDataSource {

	static final String MAX_CONNECTIONS = "max-connections";
	static final String MAX_INACTIVITY = "max_inactivity";
	static final String MAX_LIFETIME = "max_lifetime";
	static final String MAX_USE_COUNT = "max_use_count";
	static final String MAX_USE_TIME = "max_use_time";

	static final String CLEANUP_INTERVAL = "cleanup-interval";
	static final String PRETEST_CONNECTIONS = "pretest_connections";

	public int getMaxConnections();

	public void setMaxConnections(int value);

	public int getMinConnections();

	public void setMinConnections(int value);

	public long getMaxInactivity();

	public void setMaxInactivity(long value);

	public long getMaxLifetime();

	public void setMaxLifetime(long value);

	public int getMaxUseCount();

	public void setMaxUseCount(int value);

	public long getMaxUseTime();

	public void setMaxUseTime(long value);

}

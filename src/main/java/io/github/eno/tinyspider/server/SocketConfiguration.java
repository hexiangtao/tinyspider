package io.github.eno.tinyspider.server;

public interface SocketConfiguration {

	static SocketConfiguration DEFAULT = builder().build();

	public static Builder builder() {

		return new Builder();

	}

	class Builder implements io.github.eno.tinyspider.server.Builder<SocketConfiguration> {

		@Override
		public SocketConfiguration build() {
			return null;
		}

	}
}

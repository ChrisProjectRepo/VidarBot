package cs.sii.domain;

import java.util.Objects;

import org.springframework.stereotype.Component;

@Component
public class OnionAddress implements Cloneable {

	private String onion;

	public OnionAddress() {
	}

	public OnionAddress(String onion) {
		this.onion = onion;
	}

	public String getOnion() {
		return onion;
	}

	public void setOnion(String onion) {
		this.onion = onion;
	}

	@Override
	protected Object clone() {
		return new OnionAddress(this.toString());
	}

	@Override
	public String toString() {
		return onion;
	}

	public String toJsonString() {
		return "{\"user_onion\":\"" + onion + "\"}";
	}

	@Override
	public boolean equals(Object o) {
		return onion.equals(((OnionAddress) o).getOnion());
	}

	/*@Override
	public int hashCode() {
		int hash = 3;
		hash = 13 * hash + Objects.hashCode(this.urlBot);
		return hash;
	}*/
}
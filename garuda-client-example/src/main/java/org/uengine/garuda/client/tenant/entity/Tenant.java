package org.uengine.garuda.client.tenant.entity;

public class Tenant {
	private String id;
	
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Tenant [id=" + id + ", name=" + name + "]";
	}
}

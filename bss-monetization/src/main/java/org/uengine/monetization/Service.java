package org.uengine.monetization;

import org.metaworks.annotation.Order;

public class Service{

	public String id;
	@Order(1)
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}

	public String name;
	@Order(2)
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}

	public String description;
	@Order(3)
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}

}

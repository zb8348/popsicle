package com.pfw.popsicle.tools.crud.entity;

public enum HtmlType {
	hidden {
		public String getName() {
			return "hidden";
		}
	},
	checkbox {
		public String getName() {
			return "checkbox";
		}
	},
	select {
		public String getName() {
			return "select";
		}
	},
	textarea {
		public String getName() {
			return "textarea";
		}
	},
	radio {
		public String getName() {
			return "radio";
		}
	},
	dateTime {
		public String getName() {
			return "dateTime";
		}
	},
	text {
		public String getName() {
			return "text";
		}
	},
	none {
		public String getName() {
			return "none";
		}
	};
	public abstract String getName();
}

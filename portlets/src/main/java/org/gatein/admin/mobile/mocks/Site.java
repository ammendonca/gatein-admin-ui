package org.gatein.admin.mobile.mocks;

import java.io.Serializable;
import java.util.Locale;

public class Site implements Comparable<Site>, Serializable {

	private static final long serialVersionUID = 1L;

	private final String id;

	private String title;
	private String description;

	private Locale locale;

	private String skin;

	public Site(String id) {
		if (id == null)
			throw new IllegalArgumentException("id cannot be null");

		this.id = id;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public String getSkin() {
		return skin;
	}

	public void setSkin(String skin) {
		this.skin = skin;
	}

	@Override
	public int compareTo(Site other) {
		return getName().compareTo(other.getName());
	}

	@Override
	public String toString() {
		return "Site[id=" + id + "; name=" + id + "; title=" + title + "; locale=" + locale + "; skin=" + skin + "]";
	}

}

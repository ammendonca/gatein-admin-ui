/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2012, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.gatein.admin.mobile.beans.configuration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.exoplatform.container.ExoContainerContext;
import org.exoplatform.portal.config.DataStorage;
import org.exoplatform.portal.config.model.DevicePropertyCondition;
import org.exoplatform.portal.config.model.PortalConfig;
import org.exoplatform.portal.config.model.PortalRedirect;
import org.exoplatform.portal.config.model.RedirectCondition;
import org.exoplatform.portal.config.model.RedirectMappings;
import org.exoplatform.portal.config.model.UserAgentConditions;

@ManagedBean(name = "rdrEdit")
@ViewScoped
public class EditRedirectBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean isEdit = false;

	public boolean getIsEdit() {
		return isEdit;
	}

	public void setIsEdit(boolean isEdit) {
		this.isEdit = isEdit;
	}

	DataStorage ds = null;
	PortalConfig cfg = null;
	
	protected PortalRedirect pr;

	protected String name;
	protected boolean enabled;
	protected String redirectSite;

	protected RedirectMappings mappings;

	/**
	 * Sets the name of the Redirect to be edited.
	 * It's _NOT_ used to change the current redirect name!
	 */
	public void configRedirect() {
		// System.out.println("[EditRedirectBean] configRedirect()");
		Map<String,String> params = 
				FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String rname = params.get("rname");
		// System.out.println("[EditRedirectBean] configRedirect() @ got parameter rname = '" + rname + "'");
		name = rname;
	}

	/**
	 * Returns the name of the redirect being edited.
	 * 
	 * @return
	 */
	public String getName() {
		// System.out.println("[EditRedirectBean] getName() = " + name);
		return name;
	}

	/**
	 * Sets the Redirect Name.
	 * @param name
	 */
	public void setName(String name) {
		// System.out.println("[EditRedirectBean] setName(" + name + ")");
		this.name = name;
		this.isEdit = true;
	}

	/**
	 * Toggles redirect enabled/disabled state. Persisted immediately, as it's used for the redirects listing.
	 * 
	 * @param site the site the affected redirect belongs to
	 * @param name the name of the redirect to be enabled/disabled
	 */
	public void toggleEnabled(String site, String name) {
		System.out.println("[EditRedirectBean] '" + getName() + "' toggleEnabled(" + site + ", " + name +")");
		try {
			// FIXME: Use webui Util.getUIPortal();
			if (ds == null) {
				ds = (DataStorage) ExoContainerContext.getCurrentContainer().getComponentInstanceOfType(DataStorage.class);
			}

			// System.out.println("Portal Names: " + ds.getAllPortalNames());
			cfg = ds.getPortalConfig(site);
			for (PortalRedirect pr : cfg.getPortalRedirects()) {
				if (pr.getName().equals(name)) {
					System.out.println("[EditRedirectBean] '" + getName() + "' toggleEnabled(" + site + ", " + name +") >> Enabled was '" + pr.isEnabled() + "'");
					pr.setEnabled(!pr.isEnabled());
					ds.save(cfg);
					return;
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("[EditRedirectBean] '" + getName() + "' toggleEnabled(" + site + ", " + name +") >> Not Found!");
	}

	public void toggleEnabled() {
		System.out.println("[EditRedirectBean] '" + getName() + "' toggleEnabled()");
		toggleEnabled(site, name);
	}

	/**
	 * After editing a redirect, save/persist it.
	 * 
	 * @return
	 */
	public String saveRedirect() {
		System.out.println("[EditRedirectBean] '" + getName() + "' saveRedirect()");

		try {
			cfg = ds.getPortalConfig(site);
			for (PortalRedirect pr : cfg.getPortalRedirects()) {
				if (pr.getName().equals(name)) {
					ds.save(cfg);
					isEdit = false;
					return null;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * After editing a redirect, cancel it's changes.
	 * Method restores the redirect state, name, conditions, mappings, etc to it's backup state.
	 * TODO: Try to clone the entire redirect for backup.
	 */
	public void rollbackRedirect() {
		isEdit = false;
		System.out.println("[EditRedirectBean] '" + getName() + "' rollbackRedirect()");
	}

	// ----- REDIRECT ENABLED, NAME & SITE -----
	
	/**
	 * Sets the enabled/disabled state of a redirect, in edit mode. As it is in edit mode, it is not persisted as in 
	 * {{@link #toggleEnabled(String, String)}, since it can be canceled.
	 * 
	 * @param enabled the value to set the enabled property
	 */
	public void setEnabled(boolean enabled) {
		System.out.println("[EditRedirectBean] '" + getName() + "' setEnabled(" + enabled + ")");
		pr.setEnabled(enabled);
	}

	/**
	 * Getter for the redirect enabled property, indicating if the redirect is active or not.
	 * 
	 * @return a boolean indicating the redirect enabled property
	 */
	public boolean getEnabled() {
		System.out.println("[EditRedirectBean] '" + getName() + "' isEnabled() = " + enabled);
		return pr != null ? pr.isEnabled() : false;
	}

	public String getRedirectSite() {
		return redirectSite;
	}

	public void setRedirectSite(String redirectSite) {
		this.redirectSite = redirectSite;
	}

	// ----- CONDITIONS -----

	public ArrayList<RedirectCondition> getConditions() {
		return pr != null ? pr.getConditions() : new ArrayList<RedirectCondition>();
	}

	public void setConditions(ArrayList<RedirectCondition> conditions) {
		pr.setConditions(conditions);
	}

	// current condition being edited
	private int currentConditionIndex;
	private RedirectCondition editedCondition;

	private ArrayList<String> backupContains;
	private ArrayList<String> backupDoesNotContain;
	private ArrayList<DevicePropertyCondition> backupDeviceProperties;
	
	private boolean conditionsChanged = false;
	private boolean isNewCondition = false;

	private String site;

	public int getCurrentConditionIndex() {
		System.out.println("[EditRedirectBean] getCurrentConditionIndex() = " + currentConditionIndex);
		return currentConditionIndex;
	}

	public void setCurrentConditionIndex(int currentConditionIndex) {
		System.out.println("[EditRedirectBean] setCurrentConditionIndex(" + currentConditionIndex + ")");
		this.currentConditionIndex = currentConditionIndex;
	}

	public RedirectCondition getEditedCondition() {
		// System.out.println("[EditRedirectBean] getEditedCondition() = " + editedCondition);
		return editedCondition;
	}

	public void setEditedCondition(RedirectCondition editedCondition) {
		// System.out.println("[EditRedirectBean] setEditedCondition(" + editedCondition + ")");
		this.editedCondition = editedCondition;
		this.backupContains = new ArrayList<String>(editedCondition.getUserAgentConditions().getContains());
		this.backupDoesNotContain = new ArrayList<String>(editedCondition.getUserAgentConditions().getDoesNotContain());
		this.backupDeviceProperties = editedCondition.getDeviceProperties() == null ? new ArrayList<DevicePropertyCondition>() : 
			new ArrayList<DevicePropertyCondition>(editedCondition.getDeviceProperties());
		this.conditionsChanged = false;
	}

	public ArrayList<String> getContains(String condition) {
		System.out.println("[EditRedirectBean] '" + editedCondition.getName() + "' getContains(" + condition + ")");
		return editedCondition.getUserAgentConditions().getContains();
	}

	public void addCondition() {
		System.out.println("[EditRedirectBean] addCondition()");
		this.editedCondition = createNewCondition();
		isNewCondition = true;
	}
	
	/**
	 * Creates a new redirect condition, sanitizing the initial values, as they are set to null instead of empty ArrayLists, etc.
	 * 
	 * @return
	 */
	private RedirectCondition createNewCondition() {
		RedirectCondition newRC = new RedirectCondition();
		newRC.setName("");
		newRC.setDeviceProperties(new ArrayList<DevicePropertyCondition>());
		UserAgentConditions newUAC = new UserAgentConditions();
		newUAC.setContains(new ArrayList<String>());
		newUAC.setDoesNotContain(new ArrayList<String>());
		newRC.setUserAgentConditions(newUAC);

		return newRC;
	}

	/**
	 * Adds a new "CONTAINS" entry to the edited condition.
	 */
	public void addContains() {
		System.out.println("[EditRedirectBean] '" + editedCondition.getName() + "' addContains()");
		editedCondition.getUserAgentConditions().getContains().add("");
		conditionsChanged = true;
	}

	/**
	 * Removes a "CONTAINS" entry from the edited condition.
	 * @param index the index of the entry to remove
	 */
	public void removeContains(Integer index) {
		String rc = editedCondition.getUserAgentConditions().getContains().remove((int)index);
		System.out.println("[EditRedirectBean] '" + editedCondition.getName() + "' removeContains(" + index + ") = '" + rc + "'");
		conditionsChanged = true;
	}

	/**
	 * Adds a new "DOES NOT CONTAIN" entry to the edited condition.
	 */
	public void addDoesNotContain() {
		System.out.println("[EditRedirectBean] '" + editedCondition.getName() + "' addContains()");
		editedCondition.getUserAgentConditions().getDoesNotContain().add("");
		conditionsChanged = true;
	}

	/**
	 * Removes a "DOES NOT CONTAIN" entry from the edited condition.
	 * @param index the index of the entry to remove
	 */
	public void removeDoesNotContain(Integer index) {
		String rc = editedCondition.getUserAgentConditions().getDoesNotContain().remove((int)index);
		System.out.println("[EditRedirectBean] '" + editedCondition.getName() + "' removeContains(" + index + ") = '" + rc + "'");
		conditionsChanged = true;
	}

	public boolean getConditionsChanged() {
		System.out.println("[EditRedirectBean] '" + getName() + "' getConditionsChanged()");
		return conditionsChanged;
	}

	/**
	 * After editing a condition, save it.
	 * Method does nothing, as all changes should be already present in the object.
	 * 
	 * @return
	 */
	public String saveCondition() {
		System.out.println("[EditRedirectBean] '" + getName() + "' saveCondition()");
		if(isNewCondition) {
			System.out.println("[EditRedirectBean] '" + getName() + "' saveCondition() : Adding New Condition '" + editedCondition.getName() + "'");
			this.pr.getConditions().add(editedCondition);
			isNewCondition = false;
			conditionsChanged = false;
		}
		return null;
	}
	
	/**
	 * After editing a condition, cancel it's changes.
	 * Method restores the conditions, properties, etc to it's backup state.
	 * TODO: Try to clone the entire condition for backup.
	 */
	public void rollbackCondition() {
		System.out.println("[EditRedirectBean] '" + getName() + "' rollbackCondition()");
		if(!isNewCondition) {
			this.editedCondition.getUserAgentConditions().setContains(backupContains);
			this.editedCondition.getUserAgentConditions().setDoesNotContain(backupDoesNotContain);
			this.editedCondition.setDeviceProperties(backupDeviceProperties);
		}
	}
	
	// ----- PROPERTIES -----

	/**
	 * Adds a new Device Property to the edited condition.
	 */
	public void addProperty() {
		System.out.println("[EditRedirectBean] addProperty()");
		if (editedCondition.getDeviceProperties() == null) {
			editedCondition.setDeviceProperties(new ArrayList<DevicePropertyCondition>());
		}

		editedCondition.getDeviceProperties().add(new DevicePropertyCondition());
	}

	/**
	 * Removes a Device Property entry from the edited condition.
	 * @param index the index of the entry to remove
	 */
	public void removeProperty(Integer index) {
		DevicePropertyCondition rc = editedCondition.getDeviceProperties().remove((int)index);
		System.out.println("[EditRedirectBean] '" + editedCondition.getName() + "' removeProperty(" + index + ") = '" + rc + "'");
		conditionsChanged = true;
	}

	/**
	 * Gets the proper operator to be shown at property editor, mapping it to the &lt;select&gt; element.
	 * 
	 * @param index the index of the entry to get the operator from
	 * @return a string value representing the operator (mt for matches, bt for between, gt for greater-than,
	 *         lt for less-than and eq for equals)
	 */
	public String getPropertyOperator(int index) {
		System.out.println("[EditRedirectBean] getPropertyOperator(" + index + ")");
		DevicePropertyCondition dp = editedCondition.getDeviceProperties().get(index);
		if (dp.getMatches() != null && !dp.getMatches().trim().isEmpty()) {
			return "mt";
		}
		else if (dp.getGreaterThan() != null && dp.getLessThan() != null && dp.getGreaterThan() != 0.0  && dp.getLessThan() != 0.0) {
			return "bt";
		}
		else if (dp.getGreaterThan() != null || dp.getGreaterThan() != 0.0) {
			return "gt";
		}
		else if (dp.getLessThan() != null || dp.getLessThan() != 0.0) {
			return "lt";
		}
		else {
			return "eq";
		}
		//return dp.getMatches() != null ? "mt" : dp.getGreaterThan() != null ? (dp.getLessThan() != null ? "bt" : "gt") : dp.getLessThan() != null ? "lt" : "eq";
	}

	// ----- MAPPINGS -----

	public RedirectMappings getMappings() {
		return mappings;
	}

	public void setMappings(RedirectMappings mappings) {
		this.mappings = mappings;
	}

	// --- Utilities ----------------------------------------------------------

	public void load(String site, String redirect) {
		System.out.println("[EditRedirectBean] load(" + site + ", " + redirect + ")");

		this.site = site;

		// FIXME: Use webui Util.getUIPortal();
		if (ds == null) {
			ds = (DataStorage) ExoContainerContext.getCurrentContainer().getComponentInstanceOfType(DataStorage.class);
		}

		try {
			// System.out.println("Portal Names: " + ds.getAllPortalNames());
			cfg = ds.getPortalConfig(site);
			for (PortalRedirect pr : cfg.getPortalRedirects()) {
				if (pr.getName().equals(redirect)) {
					this.pr = pr;
					this.name = pr.getName();
					this.enabled = pr.isEnabled();
					this.redirectSite = pr.getRedirectSite();
					this.mappings = pr.getMappings();
					System.out.println("[EditRedirectBean] loaded successfully");
					isEdit = true;
					return;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("[EditRedirectBean] did not load");
	}
}

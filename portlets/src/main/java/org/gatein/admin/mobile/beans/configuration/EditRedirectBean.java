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
import org.exoplatform.portal.config.model.PortalConfig;
import org.exoplatform.portal.config.model.PortalRedirect;
import org.exoplatform.portal.config.model.RedirectCondition;
import org.exoplatform.portal.config.model.RedirectMappings;

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
	
	protected String name;
	protected boolean enabled;
	protected String redirectSite;

	protected ArrayList<RedirectCondition> conditions;
	protected RedirectMappings mappings;

	public String getName() {
		// System.out.println("[EditRedirectBean] getName() = " + name);
		return name;
	}

	public void configRedirect() {
		// System.out.println("[EditRedirectBean] configRedirect()");
		Map<String,String> params = 
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String rname = params.get("rname");
		// System.out.println("[EditRedirectBean] configRedirect() @ got parameter rname = '" + rname + "'");
		name = rname;
	}

	public void setName(String name) {
		// System.out.println("[EditRedirectBean] setName(" + name + ")");
		this.name = name;
		this.isEdit = true;
	}

	public boolean getEnabled() {
		// System.out.println("[EditRedirectBean] isEnabled() = " + enabled);
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		// System.out.println("[EditRedirectBean] setEnabled(" + enabled + ")");
		this.enabled = enabled;
	}

	public String getRedirectSite() {
		return redirectSite;
	}

	public void setRedirectSite(String redirectSite) {
		this.redirectSite = redirectSite;
	}

	// ----- CONDITIONS -----
	
	public ArrayList<RedirectCondition> getConditions() {
		return conditions;
	}

	public void setConditions(ArrayList<RedirectCondition> conditions) {
		this.conditions = conditions;
	}

	// current condition being edited
	private int currentConditionIndex;
	private RedirectCondition editedCondition;
	
	public int getCurrentConditionIndex() {
		// System.out.println("[EditRedirectBean] getCurrentConditionIndex() = " + currentConditionIndex);
		return currentConditionIndex;
	}
	
	public void setCurrentConditionIndex(int currentConditionIndex) {
		// System.out.println("[EditRedirectBean] setCurrentConditionIndex(" + currentConditionIndex + ")");
		this.currentConditionIndex = currentConditionIndex;
	}

	public RedirectCondition getEditedCondition() {
		// System.out.println("[EditRedirectBean] getEditedCondition() = " + editedCondition);
        return editedCondition;
    }
 
    public void setEditedCondition(RedirectCondition editedCondition) {
		// System.out.println("[EditRedirectBean] setEditedCondition(" + editedCondition + ")");
        this.editedCondition = editedCondition;
    }
    
    public RedirectMappings getMappings() {
		return mappings;
	}

	public void setMappings(RedirectMappings mappings) {
		this.mappings = mappings;
	}

	// --- Utilities ----------------------------------------------------------

	public void load(String site, String redirect) {
		System.out.println("[EditRedirectBean] load(" + site + ", " + redirect + ")");
		// FIXME: Use webui Util.getUIPortal();
		DataStorage ds = (DataStorage) ExoContainerContext.getCurrentContainer().getComponentInstanceOfType(DataStorage.class);

		try {
			// System.out.println("Portal Names: " + ds.getAllPortalNames());
			PortalConfig cfg = ds.getPortalConfig(site);
			for (PortalRedirect pr : cfg.getPortalRedirects()) {
				if (pr.getName().equals(redirect)) {
					this.name = pr.getName();
					this.enabled = pr.isEnabled();
					this.redirectSite = pr.getRedirectSite();
					this.conditions = pr.getConditions();
					this.mappings = pr.getMappings();
					System.out.println("[EditRedirectBean] loaded successfully");
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

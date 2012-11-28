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
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.exoplatform.container.ExoContainerContext;
import org.exoplatform.portal.config.DataStorage;
import org.exoplatform.portal.config.model.PortalConfig;
import org.exoplatform.portal.config.model.PortalRedirect;
import org.gatein.api.PortalRequest;
import org.gatein.api.portal.site.Site;
import org.gatein.api.portal.site.SiteQuery;
import org.gatein.api.portal.site.SiteType;

@ManagedBean(name = "mr")
@ViewScoped
public class MobileRedirectBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String siteName = null;

	public String getSiteName() {
		// System.out.println("Getting Site Name: '" + siteName + "'");
		return siteName != null ? siteName : "Olympicz Site";
	}

	public void setSiteName(String siteName) {
		// System.out.println("Setting Site Name: '" + siteName + "'");
		this.siteName = siteName;
	}

	public ArrayList<PortalRedirect> getRedirects() {
		// System.out.println("Getting Redirects for Portal '" + siteName + "'");

		if(siteName == null)
			return new ArrayList<PortalRedirect>();

		// FIXME: Use webui Util.getUIPortal();
		DataStorage ds = (DataStorage) ExoContainerContext.getCurrentContainer().getComponentInstanceOfType(DataStorage.class);

		ArrayList<PortalRedirect> r = new ArrayList<PortalRedirect>();
		try {
			// System.out.println("Portal Names: " + ds.getAllPortalNames());
			PortalConfig cfg = ds.getPortalConfig(siteName);
			r = cfg.getPortalRedirects();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// System.out.println("Returning " + (r != null ? r.size() : "N/A") + " Redirects.");
		return r;
	}

	// TODO: Move this to an API bean
	public List<Site> getSites() {
		// System.out.println("Getting Sites");
		SiteQuery sq = new SiteQuery.Builder().withSiteTypes(SiteType.SITE).build();
		List<Site> s = PortalRequest.getInstance().getPortal().findSites(sq);
		// System.out.println("Returning " + s.size() + " Sites.");
		return s;
	}

	// TODO: move this to a different bean
	public List<String> getNodes() {
		// TODO: get from portal
		ArrayList<String> nodes = new ArrayList<String>();
		nodes.add("customers");
		nodes.add("organization");
		// nodes.add("/organization/communication");
		// nodes.add("/organization/communication/marketing");
		// nodes.add("/organization/communication/press-and-media");
		// nodes.add("/organization/management");
		// nodes.add("/organization/management/executive-board");
		// nodes.add("/organization/management/human-resources");
		// nodes.add("/organization/operations/operations");
		// nodes.add("/organization/operations/finances");
		// nodes.add("/organization/operations/sales");
		// nodes.add("/organization/operations/partners");
		// nodes.add("/organization/operations/administrators");
		// nodes.add("/organization/operations/guests");
		// nodes.add("/organization/operations/users");
		// nodes.add("/organization/operations/employees");
		// nodes.add("/organization/operations/managers");
		// nodes.add("/organization/operations/design");
		return nodes;
	}
}

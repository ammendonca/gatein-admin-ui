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
import java.util.Arrays;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.exoplatform.container.ExoContainerContext;
import org.exoplatform.portal.config.DataStorage;
import org.exoplatform.portal.config.model.PortalConfig;
import org.exoplatform.portal.config.model.PortalRedirect;
import org.gatein.api.PortalRequest;
import org.gatein.api.site.Site;
import org.gatein.api.site.SiteQuery;
import org.gatein.api.site.SiteType;

@ManagedBean(name = "mr")
@ViewScoped
public class MobileRedirectBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String siteName = null;

	public String getSiteName() {
		// System.out.println("Getting Site Name: '" + siteName + "'");
		return siteName;
	}

	public void setSiteName(String siteName) {
		// System.out.println("Setting Site Name: '" + siteName + "'");
		this.siteName = siteName;
	}

	private String redirectName = null;

	public String getRedirectName() {
		System.out.println("Getting Redirect Name: '" + redirectName + "'");
		return siteName;
	}

	public void setRedirectName(String redirectName) {
		System.out.println("Setting Redirect Name: '" + redirectName + "'");
		this.redirectName = redirectName;
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

			// FIXME: getPortalRedirects() should return empty list
			if (r == null) {
				r = new ArrayList<PortalRedirect>();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// System.out.println("Returning " + (r != null ? r.size() : "N/A") + " Redirects.");
		System.out.println("[MobileRedirectBean] getRedirects() returning " + r.size() + " redirect(s) for '" + siteName + "'.");
		return r;
	}

	public PortalRedirect getRedirect(String site, String redirect) {
		// System.out.println("Getting Specific Redirect.. Site[" + site + "] Redirect[" + redirect + "]");
		setSiteName(site);
		setRedirectName(redirect);
		return getRedirect();
	}

	public PortalRedirect getRedirect() {
		// System.out.println("Getting Redirects for Portal '" + siteName + "'");

		if(redirectName == null)
			return null;

		// FIXME: Use webui Util.getUIPortal();
		DataStorage ds = (DataStorage) ExoContainerContext.getCurrentContainer().getComponentInstanceOfType(DataStorage.class);

		try {
			// System.out.println("Portal Names: " + ds.getAllPortalNames());
			PortalConfig cfg = ds.getPortalConfig(siteName);
			for (PortalRedirect pr : cfg.getPortalRedirects()) {
				if (pr.getName().equals(redirectName)) {
					return pr;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// System.out.println("Returning " + (r != null ? r.size() : "N/A") + " Redirects.");
		return null;
	}

//	// TODO: Move this to an API bean
	public List<Site> getSites() {
		System.out.println("[api] Getting Sites");
		SiteQuery sq = new SiteQuery.Builder().withSiteTypes(SiteType.SITE).build();
		List<Site> s = PortalRequest.getInstance().getPortal().findSites(sq);
		System.out.println("[api] Returning " + s.size() + " Sites.");
		return s;
	}

}

/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.template;

import java.util.List;

import com.google.common.base.CaseFormat;
import com.tactfactory.harmony.plateforme.BaseAdapter;
import com.tactfactory.harmony.template.androidxml.ManifestUpdater;
import com.tactfactory.harmony.utils.PackageUtils;

/**
 * Generate the application.
 *
 */
public class ApplicationGenerator extends BaseGenerator {
	/** The local name space. */
	private String localNameSpace;
	/** The application name. */
	private String applicationName;

	/**
	 * Constructor.
	 * @param adapter the adapter to use
	 * @throws Exception if adapter is null
	 */
	public ApplicationGenerator(final BaseAdapter adapter) throws Exception {
		super(adapter);

		this.localNameSpace =
				this.getAppMetas().getProjectNameSpace().replace('/', '.');
		this.applicationName =
				CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL,
						this.getAppMetas().getName());

		this.setDatamodel(this.getAppMetas().toMap(this.getAdapter()));
		this.getDatamodel().put(
				TagConstant.LOCAL_NAMESPACE, this.localNameSpace);

		List<String> services = new ManifestUpdater(adapter).getServices();
		this.getDatamodel().put(TagConstant.SERVICES, services);
	}

	/**
	 * Generate the application.
	 */
	public final void generateApplication() {
		this.makeSource(
				"TemplateApplication.java",
				this.applicationName + "Application.java",
				false);

		this.makeSource(
				"TemplateApplicationBase.java",
				this.applicationName + "ApplicationBase.java",
				true);
	}

	@Override
	protected final void makeSource(final String templateName,
			final String fileName,
			final boolean override) {
		final String fullFilePath = String.format("%s%s/%s",
				this.getAdapter().getSourcePath(),
				PackageUtils.extractPath(this.localNameSpace).toLowerCase(),
				fileName);

		final String fullTemplatePath = String.format("%s%s",
				this.getAdapter().getTemplateSourcePath() ,
				templateName);

		super.makeSource(fullTemplatePath, fullFilePath, override);
	}
}

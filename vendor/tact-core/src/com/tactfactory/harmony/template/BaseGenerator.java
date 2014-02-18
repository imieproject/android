/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.template;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;

import com.tactfactory.harmony.Harmony;
import com.tactfactory.harmony.meta.ApplicationMetadata;
import com.tactfactory.harmony.plateforme.BaseAdapter;
import com.tactfactory.harmony.utils.ConsoleUtils;
import com.tactfactory.harmony.utils.TactFileUtils;

import freemarker.cache.FileTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Base Generator.
 */
public abstract class BaseGenerator {

	
	// Meta-models
	/** The application metadata. */
	private ApplicationMetadata appMetas;

	// Platform adapter
	/** The used adapter. */
	private BaseAdapter adapter;
	/** The datamodel. */
	private Map<String, Object> datamodel;

	// Config
	/** The freemarker configuration. */
	private Configuration cfg = new Configuration();

	/**
	 * @return the appMetas
	 */
	public final ApplicationMetadata getAppMetas() {
		return appMetas;
	}

	/**
	 * @param appMetas the appMetas to set
	 */
	public final void setAppMetas(final ApplicationMetadata appMetas) {
		this.appMetas = appMetas;
	}

	/**
	 * @return the adapter
	 */
	public final BaseAdapter getAdapter() {
		return adapter;
	}

	/**
	 * @param adapter the adapter to set
	 */
	public final void setAdapter(final BaseAdapter adapter) {
		this.adapter = adapter;
	}

	/**
	 * @return the datamodel
	 */
	public final Map<String, Object> getDatamodel() {
		return datamodel;
	}

	/**
	 * @param datamodel the datamodel to set
	 */
	public final void setDatamodel(final Map<String, Object> datamodel) {
		this.datamodel = datamodel;
	}

	/**
	 * @return the cfg
	 */
	public final Configuration getCfg() {
		return cfg;
	}

	/**
	 * @param cfg the cfg to set
	 */
	public final void setCfg(final Configuration cfg) {
		this.cfg = cfg;
	}

	/**
	 * Constructor.
	 * @param adapt The adapter to use
	 * @throws Exception if adapter is null
	 */
	public BaseGenerator(final BaseAdapter adapt) {
		if (adapt == null) {
			throw new RuntimeException("No adapter define.");
		}

		try {
			// FIXME Clone object tree
			this.appMetas	= ApplicationMetadata.INSTANCE;
			this.adapter	= adapt;
	
			//this.cfg.setDirectoryForTemplateLoading(
			//		new File(Harmony.getRootPath() + "/vendor/tact-core"));
	
			final  Object[] files = 
					Harmony.getTemplateFolders().values().toArray();
			final  TemplateLoader[] loaders = 
					new TemplateLoader[files.length + 1];
			for (int i = 0; i < files.length; i++) {
				final FileTemplateLoader ftl =
						new FileTemplateLoader((File) files[i]);
				loaders[i] = ftl;
			}
				loaders[files.length] = new FileTemplateLoader(
						new File(Harmony.getRootPath() + "/vendor/tact-core"));
			final MultiTemplateLoader mtl = new MultiTemplateLoader(loaders);
	
			this.cfg.setTemplateLoader(mtl);

		} catch (IOException e) {
			throw new RuntimeException("Error with template loading : " 
						+ e.getMessage());
		}
	}

	/**
	 * Make Java Source Code.
	 *
	 * @param templatePath Template path file.
	 * 		For list activity is "TemplateListActivity.java"
	 * @param generatePath The destination file path
	 * @param override True for recreating the file.
	 * 			False for not writing anything if the file already exists.
	 */
	protected void makeSource(final String templatePath,
			final String generatePath,
			final boolean override) {
		
		if (!TactFileUtils.exists(generatePath) || override) {
			final File generateFile = TactFileUtils.makeFile(generatePath);
			
			try {
				String oldFile = TactFileUtils.fileToString(generateFile);
				// Debug Log
				ConsoleUtils.displayDebug("Generate Source : " +
						generateFile.getCanonicalPath());
				
				// Create
				final Template tpl =
						this.cfg.getTemplate(templatePath + ".ftl");
				
				// Write and close
				final OutputStreamWriter output =
						new OutputStreamWriter(
								new FileOutputStream(generateFile),
								TactFileUtils.DEFAULT_ENCODING);
				final String fileName = generatePath.split("/")
						[generatePath.split("/").length - 1];
				this.datamodel.put("fileName", fileName);
				tpl.process(this.datamodel, output);
				output.flush();
				output.close();
				
				if (oldFile != null && !oldFile.isEmpty()) {
					this.backupOrRollbackIfNeeded(generateFile, oldFile);
				}
			} catch (final IOException e) {
				ConsoleUtils.displayError(e);
			} catch (final TemplateException e) {
				ConsoleUtils.displayError(e);
			}
		}
	}

	/**
	 * Append Source Code to existing file.
	 *
	 * @param templatePath Template path file.
	 * 			For list activity is "TemplateListActivity.java"
	 * @param generatePath Destination file.
	 */
	protected final void appendSource(final String templatePath,
			final String generatePath) {
		if (TactFileUtils.exists(generatePath)) {
			final File generateFile = new File(generatePath);

			try {
				// Debug Log
				ConsoleUtils.displayDebug("Append Source : ",
						generateFile.getPath());

				// Create
				final Template tpl =
						this.cfg.getTemplate(templatePath + ".ftl");

				// Write and close
				final OutputStreamWriter output =
						new OutputStreamWriter(
								new FileOutputStream(generateFile, true),
								TactFileUtils.DEFAULT_ENCODING);
				tpl.process(this.datamodel, output);
				output.flush();
				output.close();

			} catch (final IOException e) {
				ConsoleUtils.displayError(e);
				ConsoleUtils.displayError(e);
			} catch (final TemplateException e) {
				ConsoleUtils.displayError(e);
				ConsoleUtils.displayError(e);
			}
		}
	}


	/**
	 * Update Libs.
	 * @param libName The library name
	 */
	protected void updateLibrary(final String libName) {
		final File dest = new File(
				String.format("%s/%s", this.adapter.getLibsPath(), libName));

		if (!dest.exists()) {
			File src = Harmony.getLibrary(libName.replace("/", File.separator));
			if (src.isDirectory()) {
				try {
					TactFileUtils.copyDirectory(src, dest);
				} catch (IOException e) {
					ConsoleUtils.displayError(e);
				}
			} else {
				TactFileUtils.copyfile(
					src,
					dest);
			}
		}
	}

	/**
	 * Generate Utils.
	 * @param utilName The utility class name
	 */
	protected void updateUtil(final String utilName) {
		this.makeSource(
				String.format("%s%s",
						this.adapter.getTemplateUtilPath(),
						utilName),
				String.format("%s%s",
						this.adapter.getUtilPath(),
						utilName),
				false);
	}

	
	/** 
	 * Backup the given file if its old content is not the same.
	 * @param file The file to backup
	 * @param oldContent Its old content
	 */
	private void backupOrRollbackIfNeeded(
			final File file, final String oldContent) {
		String newContent = TactFileUtils.fileToString(file);
		
		if (!this.adapter.filesEqual(
				oldContent, newContent, file.getName(), true)) {
			String backupFileName = "." + file.getName() + ".back"; 
			TactFileUtils.stringBufferToFile(
					new StringBuffer(oldContent), 
					new File(file.getParent() + "/" + backupFileName));
		} else {
			TactFileUtils.stringBufferToFile(
					new StringBuffer(oldContent), 
					file);
		}
	}
}

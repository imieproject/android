/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.template.androidxml;

import java.util.ArrayList;
import java.util.List;

import org.jdom2.Element;
import org.jdom2.Namespace;

import com.tactfactory.harmony.plateforme.BaseAdapter;

/**
 * Represents an android dimens.xml file.
 */
public class DimensFile extends XmlManager {
	/** Resources element. */
	private final static String ELEMENT_ROOT = "resources";
	/** Dimen element. */
	private final static String ELEMENT_DIMEN = "dimen";
	/** Name Attribute. */
	private final static String ATTRIBUTE_NAME = "name";
	
	/**
	 * List of defined dimens.
	 */
	protected ArrayList<Dimen> dimens = new ArrayList<Dimen>();
	
	/**
	 * Constructor.
	 * 
	 * @param adapter The adapter
	 * @param dimenFilePath The file path
	 */
	public DimensFile(BaseAdapter adapter, String dimenFilePath) {
		super(adapter, dimenFilePath);
		Element root = this.getDocument().getRootElement();
		List<Element> dimens = root.getChildren(ELEMENT_DIMEN);
		for (Element dimen : dimens) {
			this.dimens.add(new Dimen(dimen));
		}
		
	}
	
	/**
	 * Add a dimen if it doesn't exist yet.
	 * 
	 * @param dimen The dimen to add.
	 */
	public void addDimen(Dimen dimen) {
		if (getDimen(dimen.getName()) == null) {
			this.dimens.add(dimen);
			this.getDocument().getRootElement().addContent(dimen.getElement());
		}
	}
	
	/** 
	 * Get the dimen named name.
	 * 
	 * @param name The name of the dimen
	 * @return The dimen
	 */
	public Dimen getDimen(String name) {
		Dimen result = null;
		for (Dimen dimen : this.dimens) {
			if (dimen.getName().equals(name)) {
				result = dimen;
			}
		}
		return result;
	}
	
	/**
	 * Merge a DimensFile into this one.
	 * 
	 * @param dimenManager The dimensfile to merge into this one
	 */
	public void mergeFrom(DimensFile dimenManager) {
		ArrayList<Dimen> dimens = dimenManager.dimens;
		for (Dimen dimen : dimens) {
			this.addDimen(dimen.clone());
		}
	}
	
	/**
	 * Class representing an android dimen.
	 */
	public static class Dimen {
		/** The associated xml element. */
		protected Element element;
		/** Dimen's name. */
		protected String name;
		/** Dimen's value. */
		protected String value;
		
		/**
		 * Empty constructor.
		 */
		public Dimen() {
			this.element = new Element(ELEMENT_DIMEN);
		}
		
		/**
		 * Clone the dimen.
		 * 
		 * @return The dimen
		 */
		public Dimen clone() {
			Dimen result = new Dimen();
			result.setName(this.name);
			result.setValue(this.value);
			
			return result;
		}
		
		/**
		 * Constructor.
		 * 
		 * @param element The element to extract
		 */
		public Dimen(Element element) {
			this.element = element;
			this.parseFromElement();
		}
		
		/**
		 * Parse the element to fill this dimen.
		 */
		private void parseFromElement() {
			
			this.name = this.element.getAttributeValue(ATTRIBUTE_NAME);
			this.value = this.element.getValue();
		}
		
		/**
		 * @return the element
		 */
		public final Element getElement() {
			return element;
		}

		/**
		 * @return the name
		 */
		public final String getName() {
			return name;
		}

		/**
		 * @param name the name to set
		 */
		public final void setName(String name) {
			this.name = name;
			this.element.setAttribute(ATTRIBUTE_NAME, this.name);
		}
		
		/**
		 * @return the name
		 */
		public final String getValue() {
			return this.value;
		}

		/**
		 * @param name the name to set
		 */
		public final void setValue(String value) {
			this.value = value;
			this.element.setText(value);
		}
	}
	
	/**
	 * Merge a dimens.xml files into another one. 
	 * @param adapter The adapter
	 * @param from The source dimens.xml 
	 * @param to The dimens.xml to overwrite
	 */
	public static void mergeFromTo(BaseAdapter adapter, String from, String to) {
		DimensFile fromDimens = new DimensFile(adapter, from);
		DimensFile toDimens = new DimensFile(adapter, to);
		toDimens.mergeFrom(fromDimens);
		toDimens.save();
	}

	@Override
	protected Element getDefaultRoot() {
		Element rootElement = new Element(ELEMENT_ROOT);
		rootElement.addNamespaceDeclaration(
				Namespace.getNamespace(
						"android", 
						"http://schemas.android.com/apk/res/android"));
		return rootElement;
	}
}

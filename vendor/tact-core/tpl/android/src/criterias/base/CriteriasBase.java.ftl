<@header?interpret />
package ${project_namespace}.criterias.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ${project_namespace}.criterias.base.Criteria.Type;
import ${project_namespace}.criterias.base.value.StringValue;

/**
 * CriteriasBase.
 * An array of Criteria and CriteriasBase. Used for db requests.
 * A criterias represents the WHERE clause of a request.
 * @param T The entity type
 */
public abstract class CriteriasBase<T> implements Serializable, ICriteria {
	/** Criteria GroupType. */
	private GroupType type;
	/** Array of ICriteria. */
	private List<ICriteria> criterias = new ArrayList<ICriteria>();

	/**
	 * Constructor.
	 * @param type The Criteria's GroupType
	 */
	public CriteriasBase(final GroupType type) {
		this.type = type;
	}

	@Override
	public String toSQLiteString() {
		StringBuilder ret = new StringBuilder("(");

		for (int i = 0; i < this.criterias.size(); i++) {
			final ICriteria crit = this.criterias.get(i);
			ret.append(crit.toSQLiteString());
			if (i != this.criterias.size() - 1) {
				ret.append(' ');
				ret.append(this.type.getSqlType());
				ret.append(' ');
			}
		}
		ret.append(')');
		return ret.toString();
	}

	@Override
	public String toSQLiteSelection() {
		if (this.criterias.isEmpty()) {
			return null;
		} else {
			StringBuilder ret = new StringBuilder("(");

			for (int i = 0; i < this.criterias.size(); i++) {
				final ICriteria crit = this.criterias.get(i);
				ret.append(crit.toSQLiteSelection());
				if (i != this.criterias.size() - 1) {
					ret.append(' ');
					ret.append(this.type.getSqlType());
					ret.append(' ');
				}
			}
			ret.append(')');
			return ret.toString();
		}
	}

	@Override
	public void toSQLiteSelectionArgs(final ArrayList<String> array) {

		for (int i = 0; i < this.criterias.size(); i++) {
			final ICriteria crit = this.criterias.get(i);
			crit.toSQLiteSelectionArgs(array);
		}

	}

	/**
	 * Converts the criterias to a Selection args String array.
	 * @return The String[] of selection args
	 */
	public String[] toSQLiteSelectionArgs() {
		if (this.criterias.isEmpty()) {
			return null;
		} else {
			ArrayList<String> tmpArray = new ArrayList<String>();
			this.toSQLiteSelectionArgs(tmpArray);
			return tmpArray.toArray(new String[tmpArray.size()]);
		}
	}

	/**
	 * Adds a criteria of form : (key TYPE value).
	 * @param crit The criteria to add
	 * @return True if the criterias is valid and doesn't exists yet
	 */
	public boolean add(final Criteria crit) {
		boolean result;

		if (!this.criterias.contains(crit)) {
			this.criterias.add(crit);
			result = true;
		} else {
			result = false;
		}

		return result;
	}

	/**
	 * Adds a criteria of form : (key TYPE value).
	 * @param crit The criteria to add
	 * @return True if the criterias is valid and doesn't exists yet
	 */
	public boolean add(final ICriteria crit) {
		boolean result;

		if (!this.criterias.contains(crit)) {
			this.criterias.add(crit);
			result = true;
		} else {
			result = false;
		}

		return result;
	}

	/**
	 * Add a criteria of form : (key TYPE value).
	 * @param key The db column
	 * @param value The value
	 * @param type The type of criteria (can be Equals, Superior, etc.)
	 * @return True if the criterias is valid and doesn't exists yet
	 */
	public boolean add(final String key, final String value, final Type type) {
		final Criteria criteria = new Criteria();
		criteria.setKey(key);
		criteria.addValue(new StringValue(value));
		criteria.setType(type);

		return this.add(criteria);
	}

	/**
	 * Add a criteria of form : (key EQUALS value).
	 * @param key The db column
	 * @param value The value
	 * @return True if the criterias is valid and doesn't exists yet
	 */
	public boolean add(final String key, final String value) {
		return this.add(key, value, Type.EQUALS);
	}

	/**
	 * Enum GroupType.
	 */
	public enum GroupType {
		/** AND type.*/
		AND("AND"),
		/** OR type.*/
		OR("OR");

		/** SQLite representation of this type. */
		private String sql;

		/**
		 * Constructor.
		 * @param sql The SQL version of the Enum
		 */
		private GroupType(final String sql) {
			this.sql = sql;
		}

		/**
		 * Get the SQL String transcryption.
		 * @return The SQL version of the Enum
		 */
		public String getSqlType() { return this.sql; }
	}

	/**
	 * Checks if the List is empty.
	 * @return true if the List is empty, false otherwise
	 */
	public boolean isEmpty() {
		return this.criterias.isEmpty();
	}
}

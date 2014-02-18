package com.tactfactory.harmony.test.jobeet.entity;

import java.util.Date;

import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.annotation.Entity;
import com.tactfactory.harmony.annotation.GeneratedValue;
import com.tactfactory.harmony.annotation.Id;
import com.tactfactory.harmony.annotation.JoinColumn;
import com.tactfactory.harmony.annotation.ManyToOne;
import com.tactfactory.harmony.annotation.Table;

@Table
@Entity
public class Job {
	private static final int STRING_LENGTH = 4000;

    @Id
    @Column()					// typ ="integer",
    @GeneratedValue(strategy = "IDENTITY")
    private int id;

    @ManyToOne() 				// targetEntit ="Category"
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @Column()					// typ ="string", lengt =255
    private String type;

    @Column(nullable = true)		// typ ="string", lengt =255
    private String company;

    @Column()					// typ ="string", lengt =255
    private String logo;

    @Column(nullable = true)		// typ ="string", lengt =255
    private String url;

    @Column(nullable = true)		// typ ="string", lengt =255
    private String position;

    @Column()					// typ ="string", lengt =255
    private String location;

    @Column(length = STRING_LENGTH)		// typ ="string",
    private String description;

    @Column(length = STRING_LENGTH, name = "how_to_apply")	// typ ="string",
    private String howToApply;

    @Column(unique = true)		// typ ="string", lengt ="255",
    private String token;

    @Column(name = "is_public")	// typ ="boolean",
    private boolean isPublic;

    @Column(name = "is_activated")// typ ="boolean",
    private boolean isActivated;

    @Column()					// typ ="string", lengt ="255"
    private String email;

    @Column(name = "created_at")	// typ ="datetime",
    private Date createdAt;

    @Column(name = "updated_at")	// typ ="datetime",
    private Date updatedAt;

    @Column(name = "expires_at")	// typ ="datetime",
    private Date expiresAt;

    /**
	 * @return the id
	 */
	public final int getId() {
		return this.id;
	}

	/**
	 * @param id the id to set
	 */
	public final void setId(final int id) {
		this.id = id;
	}

	/**
	 * @return the category
	 */
	public final Category getCategory() {
		return this.category;
	}

	/**
	 * @param category the category to set
	 */
	public final void setCategory(final Category category) {
		this.category = category;
	}

	/**
	 * @return the type
	 */
	public final String getType() {
		return this.type;
	}

	/**
	 * @param type the type to set
	 */
	public final void setType(final String type) {
		this.type = type;
	}

	/**
	 * @return the company
	 */
	public final String getCompany() {
		return this.company;
	}

	/**
	 * @param company the company to set
	 */
	public final void setCompany(final String company) {
		this.company = company;
	}

	/**
	 * @return the logo
	 */
	public final String getLogo() {
		return this.logo;
	}

	/**
	 * @param logo the logo to set
	 */
	public final void setLogo(final String logo) {
		this.logo = logo;
	}

	/**
	 * @return the url
	 */
	public final String getUrl() {
		return this.url;
	}

	/**
	 * @param url the url to set
	 */
	public final void setUrl(final String url) {
		this.url = url;
	}

	/**
	 * @return the position
	 */
	public final String getPosition() {
		return this.position;
	}

	/**
	 * @param position the position to set
	 */
	public final void setPosition(final String position) {
		this.position = position;
	}

	/**
	 * @return the location
	 */
	public final String getLocation() {
		return this.location;
	}

	/**
	 * @param location the location to set
	 */
	public final void setLocation(final String location) {
		this.location = location;
	}

	/**
	 * @return the description
	 */
	public final String getDescription() {
		return this.description;
	}

	/**
	 * @param description the description to set
	 */
	public final void setDescription(final String description) {
		this.description = description;
	}

	/**
	 * @return the howToApply
	 */
	public final String getHowToApply() {
		return this.howToApply;
	}

	/**
	 * @param howToApply the howToApply to set
	 */
	public final void setHowToApply(final String howToApply) {
		this.howToApply = howToApply;
	}

	/**
	 * @return the token
	 */
	public final String getToken() {
		return this.token;
	}

	/**
	 * @param token the token to set
	 */
	public final void setToken(final String token) {
		this.token = token;
	}

	/**
	 * @return the isPublic
	 */
	public final boolean isPublic() {
		return this.isPublic;
	}

	/**
	 * @param isPublic the isPublic to set
	 */
	public final void setPublic(final boolean isPublic) {
		this.isPublic = isPublic;
	}

	/**
	 * @return the isActivated
	 */
	public final boolean isActivated() {
		return this.isActivated;
	}

	/**
	 * @param isActivated the isActivated to set
	 */
	public final void setActivated(final boolean isActivated) {
		this.isActivated = isActivated;
	}

	/**
	 * @return the email
	 */
	public final String getEmail() {
		return this.email;
	}

	/**
	 * @param email the email to set
	 */
	public final void setEmail(final String email) {
		this.email = email;
	}

	/**
	 * @return the createdAt
	 */
	public final Date getCreatedAt() {
		return new Date(this.createdAt.getTime());
	}

	/**
	 * @param createdAt the createdAt to set
	 */
	public final void setCreatedAt(final Date createdAt) {
		this.createdAt = new Date(createdAt.getTime());
	}

	/**
	 * @return the updatedAt
	 */
	public final Date getUpdatedAt() {
		return new Date(this.updatedAt.getTime());
	}

	/**
	 * @param updatedAt the updatedAt to set
	 */
	public final void setUpdatedAt(final Date updatedAt) {
		this.updatedAt = new Date(updatedAt.getTime());
	}

	/**
	 * @return the expiresAt
	 */
	public final Date getExpiresAt() {
		return new Date(this.expiresAt.getTime());
	}

	/**
	 * @param expiresAt the expiresAt to set
	 */
	public final void setExpiresAt(final Date expiresAt) {
		this.expiresAt = new Date(expiresAt.getTime());
	}

	public Job() {
    	this.createdAt = new Date();
    	this.updatedAt = new Date();
    }
}

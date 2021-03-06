package org.orphane.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "orp_orphanage_post")
public class OrphanagePost {

	@Id
	@GeneratedValue(generator = "orp_post_id_gen", strategy = GenerationType.TABLE)
	@TableGenerator(name = "orp_post_id_gen", allocationSize = 1, initialValue = 100, pkColumnName = "orp_post_id", pkColumnValue = "curr_id", table = "orp_post_id_generator", valueColumnName = "next_id")
	@Column(name = "post_id")
	private Integer postId;

	@Column(name = "orp_id", nullable = false)
	private Long orpId;

	@Column(name = "post_content", nullable = false)
	private String post;

	@Column(name = "post_time", nullable = false)
	@Temporal(TemporalType.TIME)
	private Date postTime;

	@Column(name = "post_date", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date postDate;

	public Date getPostDate() {
		return postDate;
	}

	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}

	public Integer getPostId() {
		return postId;
	}

	public void setPostId(Integer postId) {
		this.postId = postId;
	}

	public Long getOrpId() {
		return orpId;
	}

	public void setOrpId(Long orpId) {
		this.orpId = orpId;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	@Override
	public String toString() {
		return "OrphanagePost [postId=" + postId + ", orpId=" + orpId + ", post=" + post + ", postDate=" + postDate
				+ "]";
	}

	public Date getPostTime() {
		return postTime;
	}

	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}

}
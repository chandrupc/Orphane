package org.orphane.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "orp_orphanage_post")
public class OrphanagePost {

	@Id
	@GeneratedValue(generator = "orp_post_id_gen", strategy = GenerationType.TABLE)
	@TableGenerator(name = "orp_post_id_gen", allocationSize = 1, initialValue = 100, pkColumnName = "orp_post_id", pkColumnValue = "curr_id", table = "orp_post_id_generator", valueColumnName = "next_id")
	@Column(name = "post_id")
	private Integer postId;

	@Column(name = "orp_id", nullable = false)
	private Integer orpId;

	@Column(name = "post_content", nullable = false)
	private String post;

	public Integer getPostId() {
		return postId;
	}

	public void setPostId(Integer postId) {
		this.postId = postId;
	}

	public Integer getOrpId() {
		return orpId;
	}

	public void setOrpId(Integer orpId) {
		this.orpId = orpId;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}
}
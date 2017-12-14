package org.orphane.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.orphane.modelenum.UserFile;


@Entity
@Table(name = "orp_file_details")
public class FileDetails {

	@Id
	@GeneratedValue(generator = "file_id_generator", strategy = GenerationType.TABLE)
	@TableGenerator(name = "file_id_generator", table = "orp_file_id_gen", allocationSize = 1, initialValue = 100, catalog = "orphane", pkColumnName = "file_id", pkColumnValue = "value", valueColumnName = "cur_id")
	@Column(name = "file_id")
	public Long id;

	@Column(name = "file_name", nullable = false, length = 100)
	public String fileName;

	@Column(name = "file_location", nullable = false, length = 100)
	public String fileLocation;

	@Enumerated(EnumType.STRING)
	@Column(name = "user_type", length = 10, nullable = false)
	public UserFile userType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileLocation() {
		return fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

	public UserFile getUserType() {
		return userType;
	}

	public void setUserType(UserFile userType) {
		this.userType = userType;
	}

}

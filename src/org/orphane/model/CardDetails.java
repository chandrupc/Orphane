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
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "orp_card_details", catalog = "orphane", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "name_on_card", "expiry_date", "card_no" }, name = "orp_card_details_unq") })
public class CardDetails {

	@Id
	@GeneratedValue(generator = "card_id_generator", strategy = GenerationType.TABLE)
	@TableGenerator(name = "card_id_generator", table = "orp_card_id_gen", allocationSize = 1, initialValue = 100, catalog = "orphane", pkColumnName = "card_id", pkColumnValue = "value", valueColumnName = "cur_id")
	@Column(name = "card_id")
	public Long cardId;

	@Column(name = "name_on_card", nullable = false, length = 30)
	public String nameOnCard;

	@Temporal(TemporalType.DATE)
	@Column(name = "expiry_date", nullable = false)
	public Date expiryDate;

	@Column(name = "card_no", nullable = false)
	public Long cardNumber;

	public Long getCardId() {
		return cardId;
	}

	public void setCardId(Long cardId) {
		this.cardId = cardId;
	}

	public String getNameOnCard() {
		return nameOnCard;
	}

	public void setNameOnCard(String nameOnCard) {
		this.nameOnCard = nameOnCard;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Long getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(Long cardNumber) {
		this.cardNumber = cardNumber;
	}

}

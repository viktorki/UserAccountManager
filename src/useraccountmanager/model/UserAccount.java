package useraccountmanager.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import useraccountmanager.util.Constants;

@Entity
@Table(name = Constants.USER_ACCOUNT_TABLE)
public class UserAccount implements Serializable {

    private static final long serialVersionUID = 6452083108776457939L;

    @Id
    @GeneratedValue
    @Column(name = Constants.USER_ACCOUNT_ID_COLUMN)
    private Long id;

    @Column(name = Constants.USER_ACCOUNT_FIRST_NAME_COLUMN)
    @NotBlank
    @Length(max = Constants.INPUT_MAX_SIZE)
    private String firstName;

    @Column(name = Constants.USER_ACCOUNT_LAST_NAME_COLUMN)
    @NotBlank
    @Length(max = Constants.INPUT_MAX_SIZE)
    private String lastName;

    @Column(name = Constants.USER_ACCOUNT_EMAIL_COLUMN)
    @NotEmpty
    @Length(max = Constants.INPUT_MAX_SIZE)
    @Email
    private String email;

    @Column(name = Constants.USER_ACCOUNT_DATE_OF_BIRTH_COLUMN)
    @Temporal(TemporalType.DATE)
    @NotEmpty
    private Date dateOfBirth;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getFirstName() {
	return firstName;
    }

    public void setFirstName(String firstName) {
	this.firstName = firstName;
    }

    public String getLastName() {
	return lastName;
    }

    public void setLastName(String lastName) {
	this.lastName = lastName;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public Date getDateOfBirth() {
	return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
	this.dateOfBirth = dateOfBirth;
    }
}

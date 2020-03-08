package javaproject.java.csv.supercsv;

import java.util.Date;

public class CustomerBean {
	private String customerNo;
	private String firstName;
	private String lastName;
	private Date birthDate;
	private String mailingAddress;
	private Boolean married;
	private Integer numberOfKids;
	private String favouriteQuote;
	private String email;
	private long loyaltyPoints;
	
	public CustomerBean() {
	}
	
	public CustomerBean(String customerNo, String firstName, String lastName, Date birthDate, String mailingAddress,
			Boolean married, Integer numberOfKids, String favouriteQuote, String email, long loyaltyPoints) {
		super();
		this.customerNo = customerNo;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.mailingAddress = mailingAddress;
		this.married = married;
		this.numberOfKids = numberOfKids;
		this.favouriteQuote = favouriteQuote;
		this.email = email;
		this.loyaltyPoints = loyaltyPoints;
	}
	
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
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
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public String getMailingAddress() {
		return mailingAddress;
	}
	public void setMailingAddress(String mailingAddress) {
		this.mailingAddress = mailingAddress;
	}
	public Boolean getMarried() {
		return married;
	}
	public void setMarried(Boolean married) {
		this.married = married;
	}
	public Integer getNumberOfKids() {
		return numberOfKids;
	}
	public void setNumberOfKids(Integer numberOfKids) {
		this.numberOfKids = numberOfKids;
	}
	public String getFavouriteQuote() {
		return favouriteQuote;
	}
	public void setFavouriteQuote(String favouriteQuote) {
		this.favouriteQuote = favouriteQuote;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public long getLoyaltyPoints() {
		return loyaltyPoints;
	}
	public void setLoyaltyPoints(long loyaltyPoints) {
		this.loyaltyPoints = loyaltyPoints;
	}

	@Override
	public String toString() {
		return "CustomerBean [customerNo=" + customerNo + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", birthDate=" + birthDate + ", mailingAddress=" + mailingAddress + ", married=" + married
				+ ", numberOfKids=" + numberOfKids + ", favouriteQuote=" + favouriteQuote + ", email=" + email
				+ ", loyaltyPoints=" + loyaltyPoints + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((birthDate == null) ? 0 : birthDate.hashCode());
		result = prime * result + ((customerNo == null) ? 0 : customerNo.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((favouriteQuote == null) ? 0 : favouriteQuote.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + (int) (loyaltyPoints ^ (loyaltyPoints >>> 32));
		result = prime * result + ((mailingAddress == null) ? 0 : mailingAddress.hashCode());
		result = prime * result + ((married == null) ? 0 : married.hashCode());
		result = prime * result + ((numberOfKids == null) ? 0 : numberOfKids.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomerBean other = (CustomerBean) obj;
		if (birthDate == null) {
			if (other.birthDate != null)
				return false;
		} else if (!birthDate.equals(other.birthDate))
			return false;
		if (customerNo == null) {
			if (other.customerNo != null)
				return false;
		} else if (!customerNo.equals(other.customerNo))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (favouriteQuote == null) {
			if (other.favouriteQuote != null)
				return false;
		} else if (!favouriteQuote.equals(other.favouriteQuote))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (loyaltyPoints != other.loyaltyPoints)
			return false;
		if (mailingAddress == null) {
			if (other.mailingAddress != null)
				return false;
		} else if (!mailingAddress.equals(other.mailingAddress))
			return false;
		if (married == null) {
			if (other.married != null)
				return false;
		} else if (!married.equals(other.married))
			return false;
		if (numberOfKids == null) {
			if (other.numberOfKids != null)
				return false;
		} else if (!numberOfKids.equals(other.numberOfKids))
			return false;
		return true;
	}

	
	
	
	
}

package pojoResponses;

public class SingleUser {
private data dataobject=null;
private ad adobject=null;

public data getDataobject() {
	return dataobject;
}
public void setDataobject(data dataobject) {
	this.dataobject = dataobject;
}
public ad getAdobject() {
	return adobject;
}
public void setAdobject(ad adobject) {
	this.adobject = adobject;
}
class data{
	private String id=null;
	private String email=null;
	private String first_name=null;
	private String last_name=null;
	private String avatar=null;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
}
class ad{
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	private String company=null;
	private String url=null;
	private String text=null;
}
}

